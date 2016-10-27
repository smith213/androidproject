package com.freephone.justfofun.freephone.inject.module;

import android.app.Service;

import dagger.Module;
import dagger.Provides;

/**
 * Created by imorn on 15/12/7.
 */
@Module
public class ServiceModule {

    private final Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    @ServiceScope
    Service providerService() {
        return service;
    }
}