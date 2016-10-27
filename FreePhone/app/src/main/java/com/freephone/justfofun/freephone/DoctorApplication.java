package com.freephone.justfofun.freephone;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.freephone.justfofun.freephone.common.fresco.OkHttpImagePipelineConfigFactory;
import com.freephone.justfofun.freephone.inject.InjectApplication;
import com.freephone.justfofun.freephone.inject.component.AppComponent;

import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;


/**
 * Created by imorn on 15/11/4.
 */
public class DoctorApplication extends InjectApplication {


    private static final String XIAO_MI_PUSH_TAG = "xiaoMiPush";

    @Inject
    MyAccountManager myAccountManager;

    @Override
    public void onCreate() {
        super.onCreate();

        myAccountManager.removeExpiredAccount();
        initFresco();
    }

    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }


    public static DoctorApplication get(Context context) {
        return (DoctorApplication) context.getApplicationContext();
    }


    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 初始化图片加载
     */
    private void initFresco() {
        OkHttpClient okHttpClient = new OkHttpClient();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, okHttpClient)
                .build();
        Fresco.initialize(this, config);
    }
}
