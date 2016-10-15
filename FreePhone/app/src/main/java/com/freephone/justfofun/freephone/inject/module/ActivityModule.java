package com.freephone.justfofun.freephone.inject.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by imorn on 15/11/17.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return activity;
    }


}
