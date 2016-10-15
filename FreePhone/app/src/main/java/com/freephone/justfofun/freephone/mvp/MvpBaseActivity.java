package com.freephone.justfofun.freephone.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.freephone.justfofun.freephone.inject.InjectActivity;
import com.freephone.justfofun.freephone.mvp.core.MvpPresenter;
import com.freephone.justfofun.freephone.mvp.core.MvpView;

import javax.inject.Inject;


/**
 * Created by imorn on 15/12/3.
 */
public abstract class MvpBaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends InjectActivity {

    @Inject
    P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate();
        presenter.attachView(onCreateMvpView());
    }

    protected abstract V onCreateMvpView();

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    protected P getPresenter() {
        return presenter;
    }

}
