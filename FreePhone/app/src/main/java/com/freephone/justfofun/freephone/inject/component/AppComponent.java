package com.freephone.justfofun.freephone.inject.component;

import android.content.Context;

import com.freephone.justfofun.freephone.DoctorApplication;
<<<<<<< HEAD
import com.freephone.justfofun.freephone.MyAccountManager;
=======
import com.freephone.justfofun.freephone.account.MyAccountManager;
>>>>>>> dev
import com.freephone.justfofun.freephone.inject.module.AppModule;
import com.freephone.justfofun.freephone.restful.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by imorn on 16/3/24.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Context context();

    ApiService apiService();

    MyAccountManager myAccountManager();

    void inject(DoctorApplication application);
}
