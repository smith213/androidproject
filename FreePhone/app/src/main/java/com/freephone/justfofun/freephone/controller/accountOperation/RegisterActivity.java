package com.freephone.justfofun.freephone.controller.accountOperation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.freephone.justfofun.freephone.account.MyAccountManager;
import com.freephone.justfofun.freephone.R;
import com.freephone.justfofun.freephone.inject.InjectActivity;
import com.freephone.justfofun.freephone.inject.component.ActivityComponent;
import com.freephone.justfofun.freephone.utils.SharedPreferencesUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends InjectActivity {

    /**
     * 用于设置当前日期的输入格式为20161217之类，在密码中检验（密码为今日的日期）
     */
    private static final Format format = new SimpleDateFormat("yyyyMMdd");
    @Inject
    MyAccountManager myAccountManager;

    @BindView(R.id.tips_text)
    TextView tipsText;

    @BindView(R.id.vow_text)
    TextView vowText;

    @BindView(R.id.name_text)
    AutoCompleteTextView nameText;

    @BindView(R.id.password_text)
    EditText passwordText;

    @BindView(R.id.register_button)
    Button registerButton;

    private SharedPreferencesUtils mSharedPreferences;
    private Set<String> names = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSharedPreferences = new SharedPreferencesUtils(this,"account");
        initView();
    }

    @Override
    protected void initInject(ActivityComponent component) {
        component.inject(this);
    }

    private void initView(){
        ButterKnife.bind(this);
        initTips();
        passwordText.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attempRegister();
                return true;
            }
            return false;
        });

        registerButton.setOnClickListener(v->attempRegister());
    }

    private void initTips() {
        //初始化协议
        tipsText.setText(getString(R.string.tips));
        SpannableString spannableString = new SpannableString(getString(R.string.tipsvow));
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#30a9e5"));
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View widget) {
                tipsText.setVisibility(View.GONE);
                vowText.setVisibility(View.VISIBLE);
            }
        }, 0, getString(R.string.tipsvow).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tipsText.append(spannableString);
        tipsText.setHighlightColor(Color.TRANSPARENT);
        tipsText.setMovementMethod(LinkMovementMethod.getInstance());

        vowText.setClickable(true);
        vowText.setOnClickListener(v->{
            tipsText.setVisibility(View.VISIBLE);
            vowText.setVisibility(View.GONE);
        });
    }

    private void attempRegister(){
        if (!passwordText.getText().toString().equals(format.format(new Date()))) {
            Toast.makeText(this,"悟性不够不要强求，何不充值一波灵性再来呢？",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            names.addAll(mSharedPreferences.readStringSet("names"));
            if(names.contains(nameText.getText().toString())){
                names.clear();
                Toast.makeText(this,"你已经是革命队伍的一员了，同志",Toast.LENGTH_LONG).show();
                return;
            }
            names.add(nameText.getText().toString());
            mSharedPreferences.saveStringSet("names",names);
//            myAccountManager.addAccount(nameText.getText().toString(),passwordText.getText().toString());
            Toast.makeText(this,"欢迎加入无产阶级大家庭！",Toast.LENGTH_LONG);
            finish();
        }
    }

    public static void launch(Context context){
        Intent intent = new Intent(context,RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
