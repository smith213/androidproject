package com.freephone.justfofun.freephone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.freephone.justfofun.freephone.R;
import com.freephone.justfofun.freephone.inject.component.ActivityComponent;
import com.freephone.justfofun.freephone.mvp.MvpBaseActivity;
import com.freephone.justfofun.freephone.mvp.mvppresenter.DailActivityPresenter;
import com.freephone.justfofun.freephone.mvp.mvpview.DailActivityView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailActivity extends MvpBaseActivity<DailActivityView,DailActivityPresenter> implements DailActivityView{
    public static final String NAME = "name";
    public static final String PASSWORD = "password";

    private String name;
    private String password;

    @BindView(R.id.dail_layout)
    LinearLayout dailLayout;

    @BindView(R.id.callee_text)
    TextView calleeText;

    @BindView(R.id.caller_text)
    TextView callerText;

    @BindView(R.id.patient_header_simpledraweeview)
    SimpleDraweeView callerPic;

    @BindView(R.id.call_phone_layout)
    LinearLayout callButton;

    @BindView(R.id.click_text)
    TextView clickText;

    private boolean isConnectSuccess = false;

    private  TelephonyManager tManager; //监听通话状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_dail);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString(NAME);
        password = bundle.getString(PASSWORD);

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
    }

    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
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
}
