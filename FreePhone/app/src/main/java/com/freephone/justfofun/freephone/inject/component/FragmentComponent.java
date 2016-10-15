package com.freephone.justfofun.freephone.inject.component;


import com.freephone.justfofun.freephone.inject.module.FragmentModule;
import com.freephone.justfofun.freephone.inject.module.FragmentScope;

import dagger.Component;


/**
 * Created by imorn on 16/3/24.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
}
