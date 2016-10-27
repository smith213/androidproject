package com.freephone.justfofun.freephone.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.freephone.justfofun.freephone.BuildConfig;
import com.freephone.justfofun.freephone.R;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Shenh on 2016/10/25.
 * 账户管理
 */
@Singleton
public class MyAccountManager {
    private Context mContext;

    @Inject
    AccountManager mAccountManager;

    private String mAccountType;

    private String userName;

    private String tokenType = "person";

    @Inject
    MyAccountManager(Context context) {
        mContext = context.getApplicationContext();
        mAccountType = context.getString(R.string.account_type);
    }

    public void removeExpiredAccount() {
        Account account = getAccount();
        if (account != null) {
            String appVersion = mAccountManager.getUserData(account, "AppVersion");
            if (TextUtils.isEmpty(appVersion)) {
                try {
                    AccountManagerFuture<Boolean> future = mAccountManager.removeAccount(account, future1 -> {
                    }, null);
                    future.getResult();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Observable<Boolean> deleteAccount() {
        return Observable.create(subscriber -> {
            Account account = getAccount();
            try {
                AccountManagerFuture<Boolean> future = mAccountManager.removeAccount(account, future1 -> {
                }, null);
                boolean isSuccess = future.getResult();
                subscriber.onNext(isSuccess);
                subscriber.onCompleted();
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }

        });
    }

    public Account getAccount() {//对getAccountsByType方法来说用23以上的版本编译会提示call requires permission which may be rejected by user
        Account[] accounts = mAccountManager.getAccountsByType(mAccountType);
        if (accounts != null && accounts.length > 0) {
            return accounts[0];
        }
        return null;
    }

    public boolean isLogin() {
        return getAccount() != null;
    }

    public boolean addAccount(String username, String password/*,String session*/) {
        Account account = getAccount();
        if (account == null) {
            account = new Account(username, mAccountType);
            Bundle bundle = new Bundle();
            bundle.putString("AppVersion", String.valueOf(BuildConfig.VERSION_CODE));
            return mAccountManager.addAccountExplicitly(account, password, bundle);
//            mAccountManager.setAuthToken(account, tokenType, session);
        }
        return true;
    }
}
