package com.freephone.justfofun.freephone.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatDialog;


import com.freephone.justfofun.freephone.R;

import javax.inject.Inject;

/**
 * 加载对话框
 * <p>
 * Created by imorn on 15/11/11.
 */
public class LoadingDialog {

    private AppCompatDialog mDialog;

    @Inject
    public LoadingDialog(Activity activity) {
        mDialog = new AppCompatDialog(activity, R.style.LoadingDialog);
        mDialog.setContentView(R.layout.dialog_loading);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void show() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
