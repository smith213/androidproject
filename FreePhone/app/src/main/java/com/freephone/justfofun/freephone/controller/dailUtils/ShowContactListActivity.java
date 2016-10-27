package com.freephone.justfofun.freephone.controller.dailUtils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.freephone.justfofun.freephone.R;
import com.freephone.justfofun.freephone.account.MyAccountManager;
import com.freephone.justfofun.freephone.databinding.ActivityShowContactListBinding;
import com.freephone.justfofun.freephone.databinding.ItemContactLayoutBinding;
import com.freephone.justfofun.freephone.inject.InjectActivity;
import com.freephone.justfofun.freephone.inject.component.ActivityComponent;
import com.freephone.justfofun.freephone.model.ContactInfo;
import com.freephone.justfofun.freephone.restful.ApiService;
import com.freephone.justfofun.freephone.restful.RestfulResponseUtils;
import com.freephone.justfofun.freephone.restful.model.CallPhoneParam;
import com.freephone.justfofun.freephone.ui.LoadingDialog;
import com.freephone.justfofun.freephone.ui.SimpleListDividerDecorator;
import com.freephone.justfofun.freephone.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.freephone.justfofun.freephone.utils.PhoneNumUtils.clearRedundancy;
import static com.freephone.justfofun.freephone.utils.PhoneNumUtils.isMobileNO;

public class ShowContactListActivity extends InjectActivity {
    /**获取库Phon表字段**/
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };

    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**头像ID**/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**联系人的ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    private ActivityShowContactListBinding binding;
    private ContactAdapter mAdapter;

    private List<ContactInfo> myContacts = new ArrayList<>();

    @Inject
    MyAccountManager myAccountManager;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    ApiService mApiService;

    private SharedPreferencesUtils mSharedPreferences;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_show_contact_list);

        mSharedPreferences = new SharedPreferencesUtils(this,"loginInfo");
        userName = mSharedPreferences.readString("loginName");

        if(myAccountManager.isLogin()){
            setTitle(userName+"的联系人");
        }

        initView();

        initData();
    }

    @Override
    protected void initInject(ActivityComponent component) {
        component.inject(this);
    }

    void initView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(this, R.drawable.shape_divider), true));
        binding.recyclerView.setHasFixedSize(true);
        mAdapter = new ContactAdapter(this);
        binding.recyclerView.setAdapter(mAdapter);
    }

    void initData(){
        getPhoneContacts();
        mAdapter.refreshDataList(myContacts);
    }

    private void getPhoneContacts() {
        ContentResolver resolver = this.getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);


        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                //得到联系人ID
                long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

                //得到联系人头像ID
                long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setContactId(contactid);
                contactInfo.setContactName(contactName);
                contactInfo.setContactNum(phoneNumber);
                contactInfo.setPhotoId(photoid);

                myContacts.add(contactInfo);
            }

            phoneCursor.close();
        }
    }

    public static void launch(Context context){
        context.startActivity(new Intent(context,ShowContactListActivity.class));
    }

    class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>{
        @NonNull
        private List<ContactInfo> contactInfos;
        private Context context;

        public ContactAdapter(Context context){
            this.context = context;
            this.contactInfos = new ArrayList<>();
        }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_layout,parent,false);
            return new ContactHolder(itemView);
        }

        public void refreshDataList(List<ContactInfo> contactInfos){
            this.contactInfos = contactInfos;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            holder.bind(contactInfos.get(position));
            holder.itemView.setOnClickListener(v->{
                if(!isMobileNO(binding.callerText.getText().toString())||!isMobileNO(clearRedundancy(contactInfos.get(position).getContactNum()))){
                    Toast.makeText(ShowContactListActivity.this,"号码有误",Toast.LENGTH_LONG).show();
                    return;
                }
                String callee = clearRedundancy(contactInfos.get(position).getContactNum());
                CallPhoneParam param = new CallPhoneParam();
                param.setBusinessId(1);
                param.setProductId(1);
                param.setPhoneCaller(binding.callerText.getText().toString());
                param.setPhoneReceiver(callee);
                param.setCallerShowPhoneNumber(binding.callerText.getText().toString());
                param.setReceiverShowPhoneNumber(callee);
                mApiService.callPhoneWhenNotOpenPhoneConsult(param)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(() -> loadingDialog.show())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .flatMap(RestfulResponseUtils::processorResponse)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(voidRestfulResponse -> {
                                    loadingDialog.dismiss();
                                    Toast.makeText(ShowContactListActivity.this,"呼叫成功，请等待",Toast.LENGTH_LONG).show();
                                }
                                , throwable -> {
                                    loadingDialog.dismiss();
                                    Toast.makeText(ShowContactListActivity.this,"呼叫失败，网出问题了",Toast.LENGTH_LONG).show();
                                }
                        );
            });
        }

        @Override
        public int getItemCount() {
            return contactInfos.size();
        }

        class ContactHolder extends RecyclerView.ViewHolder{
            private ItemContactLayoutBinding mBinding;

            public ContactHolder(View itemView) {
                super(itemView);
                mBinding = DataBindingUtil.bind(itemView);
            }

            public void bind(@NonNull ContactInfo contactInfo){
                mBinding.setUserInfo(contactInfo);
                if(contactInfo.getPhotoId()>0){
                    mBinding.patientContact.setImageURI(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactInfo.getContactId()));
                }
            }
        }
    }
}
