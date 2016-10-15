package com.freephone.justfofun.freephone.mvp;

import android.content.Context;
import android.support.v4.app.DialogFragment;

import com.freephone.justfofun.freephone.mvp.core.MvpPresenter;
import com.freephone.justfofun.freephone.mvp.core.MvpView;

import javax.inject.Inject;


/**
 * Created by imorn on 16/5/19.
 */
public abstract class MvpBaseDialogFragment <V extends MvpView, P extends MvpPresenter<V>> extends DialogFragment {

    @Inject
    P presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter.attachView(onCreateMvpView());
        presenter.onCreate();
    }


    protected abstract V onCreateMvpView();

    protected P getPresenter() {
        return presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        if (presenter != null) {
            presenter.onStop();
        }
        super.onStop();

    }

    @Override
    public void onDetach() {
        if (presenter != null) {
            presenter.detachView();
            presenter.onDestroy();
        }
        super.onDetach();
    }

}