package com.freephone.justfofun.freephone.inject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.freephone.justfofun.freephone.DoctorApplication;
import com.freephone.justfofun.freephone.inject.component.ActivityComponent;
import com.freephone.justfofun.freephone.inject.component.DaggerActivityComponent;
import com.freephone.justfofun.freephone.inject.module.ActivityModule;


/**
 * Created by imorn on 15/11/17.
 */
public abstract class InjectActivity extends AppCompatActivity {


    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("inject", "onCreate   " + this.getClass() + "  ----> " + this);
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(DoctorApplication.getAppComponent(this))
                .activityModule(new ActivityModule(this))
                .build();
        initInject(activityComponent);

    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected abstract void initInject(ActivityComponent component);
}
