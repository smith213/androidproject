package com.freephone.justfofun.freephone.inject.module;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.location.LocationManager;


import com.freephone.justfofun.freephone.restful.ApiService;
import com.freephone.justfofun.freephone.restful.ApiServiceFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by imorn on 15/11/17.
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context context() {
        return application;
    }


    @Singleton
    @Provides
    static public AccountManager providerAccountManager(Context context) {
        return AccountManager.get(context);
    }

    @Singleton
    @Provides
    static ApiService apiService(ApiServiceFactory apiServiceFactory) {
        return apiServiceFactory.createApiService();
    }

    @Singleton
    @Provides
    static LocationManager locationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

}
