package com.freephone.justfofun.freephone.inject.module;

/**
 * Created by imorn on 16/3/24.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {
}
