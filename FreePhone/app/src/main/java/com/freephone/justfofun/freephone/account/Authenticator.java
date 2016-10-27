package com.freephone.justfofun.freephone.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

<<<<<<< HEAD
import com.freephone.justfofun.freephone.LoginActivity;
import com.freephone.justfofun.freephone.MyAccountManager;
=======
import com.freephone.justfofun.freephone.controller.accountOperation.LoginActivity;
>>>>>>> dev
import com.freephone.justfofun.freephone.R;

import javax.inject.Inject;

/**
 * Created by imorn on 15/12/7.
 */
public class Authenticator extends AbstractAccountAuthenticator {

    public static final String KEY_ERROR_CODE = "ErrorCode";
    public static final String KEY_ERROR_MESSAGE = "ErrorMessage";

    @Inject
    MyAccountManager mDoctorAccountManger;

    private Context mContext;

    private String mAccountType;

    @Inject
    public Authenticator(Context context) {
        super(context);
        mContext = context;
        mAccountType = context.getString(R.string.account_type);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {

        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Account account = mDoctorAccountManger.getAccount();
        if (account == null) {
            final Intent intent = new Intent(mContext, LoginActivity.class);
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
            final Bundle bundle = new Bundle();
            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
            return bundle;
        } else {
            Toast.makeText(mContext, "已经登陆了!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse response,
                                           Account account) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, true);
        return result;
    }
}