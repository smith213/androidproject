package com.freephone.justfofun.freephone.account;

import android.content.Intent;
import android.os.IBinder;

import com.freephone.justfofun.freephone.inject.InjectService;
import com.freephone.justfofun.freephone.inject.component.ServiceComponent;

import javax.inject.Inject;

/**
 * Created by imorn on 15/12/7.
 */
public class AuthenticationService extends InjectService {

    @Inject
    Authenticator mAuthenticator;



    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

    @Override
    protected void initInject(ServiceComponent serviceComponent) {
        serviceComponent.inject(this);
    }
}
