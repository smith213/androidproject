package com.freephone.justfofun.freephone.mvp.core;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by Shenh on 15/11/12.
 */
public abstract class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }



}
