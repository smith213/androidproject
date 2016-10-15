package com.freephone.justfofun.freephone.inject;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.freephone.justfofun.freephone.inject.component.AppComponent;
import com.freephone.justfofun.freephone.inject.component.DaggerAppComponent;
import com.freephone.justfofun.freephone.inject.module.AppModule;


/**
 * Created by imorn on 16/3/24.
 */
public abstract class InjectApplication extends MultiDexApplication {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = createAppComponent();
        inject(mAppComponent);
    }

    protected abstract void inject(AppComponent appComponent);

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent(Context context) {
        return ((InjectApplication) context.getApplicationContext()).mAppComponent;
    }
}
