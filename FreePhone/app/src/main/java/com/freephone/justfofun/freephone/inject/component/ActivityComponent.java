package com.freephone.justfofun.freephone.inject.component;

/**
 * Created by imorn on 16/3/24.
 */

import android.app.Activity;
import android.content.Context;


import com.freephone.justfofun.freephone.DailActivity;
import com.freephone.justfofun.freephone.LoginActivity;
import com.freephone.justfofun.freephone.MyAccountManager;
import com.freephone.justfofun.freephone.RegisterActivity;
import com.freephone.justfofun.freephone.inject.module.ActivityModule;
import com.freephone.justfofun.freephone.inject.module.ActivityScope;
import com.freephone.justfofun.freephone.restful.ApiService;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();

    Context context();

    ApiService apiService();

    MyAccountManager myAccountManager();

    void inject(LoginActivity loginActivity);

    void inject(DailActivity dailActivity);

    void inject(RegisterActivity registerActivity);
}
