package com.freephone.justfofun.freephone.mvp.core;


/**
 * Created by Shenh on 15/11/12.
 */
public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();

    void onCreate();

    void onDestroy();

    void onStart();

    void onStop();
}
