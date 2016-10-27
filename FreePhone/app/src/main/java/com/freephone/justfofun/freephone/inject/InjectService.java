package com.freephone.justfofun.freephone.inject;

import android.app.Service;

import com.freephone.justfofun.freephone.DoctorApplication;
import com.freephone.justfofun.freephone.inject.component.DaggerServiceComponent;
import com.freephone.justfofun.freephone.inject.component.ServiceComponent;
import com.freephone.justfofun.freephone.inject.module.ServiceModule;


/**
 * Created by imorn on 15/12/7.
 */
public abstract class   InjectService extends Service {

    private ServiceComponent serviceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceComponent = DaggerServiceComponent.builder()
                .appComponent(DoctorApplication.getAppComponent(this))
                .serviceModule(new ServiceModule(this))
                .build();
        initInject(serviceComponent);
    }

    protected abstract void initInject(ServiceComponent serviceComponent);
}
