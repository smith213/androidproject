package com.freephone.justfofun.freephone.mvp.mvpview;

import com.freephone.justfofun.freephone.mvp.core.MvpView;

/**
 * Created by Administrator on 2016/10/13.
 */

public interface DailActivityView extends MvpView {
    void dailSuccess();

    void dialFailed(String errorMessage);

    void getOut();

    void getOutSuccess();

    void getOutFailed();
}
