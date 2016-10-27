package com.freephone.justfofun.freephone.inject.component;

import android.app.Service;

import com.freephone.justfofun.freephone.account.AuthenticationService;
import com.freephone.justfofun.freephone.inject.module.ServiceModule;
import com.freephone.justfofun.freephone.inject.module.ServiceScope;

import dagger.Component;

/**
 * Created by imorn on 16/3/24.
 */
@ServiceScope
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    Service service();

    void inject(AuthenticationService authenticationService);
}
