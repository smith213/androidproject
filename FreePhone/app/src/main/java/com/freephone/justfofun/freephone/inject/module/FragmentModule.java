package com.freephone.justfofun.freephone.inject.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by imorn on 16/3/24.
 */
@Module
public class FragmentModule {

    private final Fragment fragment;


    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Fragment providerFragment() {
        return fragment;
    }

    @Provides
    @FragmentScope
    Activity providerActivity() {
        return fragment.getActivity();
    }
}
