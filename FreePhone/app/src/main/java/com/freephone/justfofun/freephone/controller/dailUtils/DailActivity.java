package com.freephone.justfofun.freephone.controller.dailUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.freephone.justfofun.freephone.account.MyAccountManager;
import com.freephone.justfofun.freephone.R;
import com.freephone.justfofun.freephone.controller.accountOperation.LoginActivity;
import com.freephone.justfofun.freephone.inject.component.ActivityComponent;
import com.freephone.justfofun.freephone.mvp.MvpBaseActivity;
import com.freephone.justfofun.freephone.mvp.mvppresenter.DailActivityPresenter;
import com.freephone.justfofun.freephone.mvp.mvpview.DailActivityView;
import com.freephone.justfofun.freephone.utils.PhoneNumUtils;
import com.freephone.justfofun.freephone.utils.SharedPreferencesUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.freephone.justfofun.freephone.utils.PhoneNumUtils.isMobileNO;

public class DailActivity extends MvpBaseActivity<DailActivityView,DailActivityPresenter> implements DailActivityView{
    public static final String NAME = "name";
    public static final String PASSWORD = "password";

    private String name;
    private String password;

    @BindView(R.id.dail_layout)
    LinearLayout dailLayout;

    @BindView(R.id.callee_text)
    TextView calleeText;

    @BindView(R.id.contact_layout)
    LinearLayout contactLayout;

    @BindView(R.id.caller_text)
    TextView callerText;

    @BindView(R.id.logout_layout)
    RelativeLayout logoutLayout;

    @BindView(R.id.patient_header_simpledraweeview)
    SimpleDraweeView callerPic;

    @BindView(R.id.call_phone_layout)
    LinearLayout callButton;

    @BindView(R.id.click_text)
    TextView clickText;

    @Inject
    MyAccountManager myAccountManager;

    private SharedPreferencesUtils mSharedPreferences;

    private String userName;

    private boolean isConnectSuccess = false;

    private  TelephonyManager tManager; //监听通话状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_dail);

        mSharedPreferences = new SharedPreferencesUtils(this,"loginInfo");
        userName = mSharedPreferences.readString("loginName");

        setTitle(userName+"的专属电话");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            name = bundle.getString(NAME);
            password = bundle.getString(PASSWORD);
        }



        tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener pListener=new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String number) {
                // TODO Auto-generated method stub
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE://无任何状态
                    {
                        clickText.setText("拨打电话");
                        isConnectSuccess = false;
                        break;
                    }
                    case TelephonyManager.CALL_STATE_OFFHOOK://接听来电
                        isConnectSuccess = true;
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://来电
                        break;
                    default:
                        break;
                }
                super.onCallStateChanged(state, number);
            }
        };
        //为tManager添加监听器
        tManager.listen(pListener, PhoneStateListener.LISTEN_CALL_STATE);

       initView();
    }

    private void initView(){
        ButterKnife.bind(this);

        callerPic.setImageURI(Uri.parse("res://drawble/"+R.drawable.ic_sb));

        dailLayout.setOnClickListener((view)->{
            if(isMobileNO(callerText.getText().toString().trim())){
                clickText.setText("正在联系万恶的资本家");
                getPresenter().callPhoneWhenNotOpenPhoneConsult(callerText.getText().toString().trim(),"13810106801");
            }
            else Toast.makeText(this,"做好事不怕留名，\n这位英雄请把呼叫者的号码输对",Toast.LENGTH_LONG).show();
        });
        callButton.setOnClickListener((v)->{
            if(isMobileNO(callerText.getText().toString().trim())&&isMobileNO(calleeText.getText().toString().trim())){
                clickText.setText("呼叫中，等待来电");
                getPresenter().callPhoneWhenNotOpenPhoneConsult(callerText.getText().toString().trim(),calleeText.getText().toString().trim());
            }else {
                Toast.makeText(this,"电话输入有误",Toast.LENGTH_LONG).show();
            }
        });

        logoutLayout.setOnClickListener(v->{
            getPresenter().tryLogout();
        });
    }

    @OnClick(R.id.contact_layout)
    void gotoContactList(){
        ShowContactListActivity.launch(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KEYCODE_BACK)){
            if(myAccountManager.isLogin()) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initInject(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected DailActivityView onCreateMvpView() {
        return this;
    }

    public static Void launch(Context context,@Nullable Bundle bundle){
        Intent intent = new Intent(context,DailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
        return null;
    }

    @Override
    public void dailSuccess() {
        clickText.setText("拨打电话");
        Toast.makeText(this,"成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void dialFailed(String errorMessage) {
        clickText.setText("拨打电话");
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void getOut() {

    }

    @Override
    public void getOutSuccess() {
        mSharedPreferences.saveBoolean("login",false);
        mSharedPreferences.saveString("loginName","");
        mSharedPreferences.commit();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void getOutFailed() {
        Toast.makeText(this,"退出失败",Toast.LENGTH_LONG).show();
    }
}
