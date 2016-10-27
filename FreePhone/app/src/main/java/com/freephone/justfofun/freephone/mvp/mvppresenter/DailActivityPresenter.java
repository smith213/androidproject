package com.freephone.justfofun.freephone.mvp.mvppresenter;

import android.content.Context;

import com.freephone.justfofun.freephone.account.MyAccountManager;
import com.freephone.justfofun.freephone.mvp.core.MvpBasePresenter;
import com.freephone.justfofun.freephone.mvp.mvpview.DailActivityView;
import com.freephone.justfofun.freephone.restful.ApiService;
import com.freephone.justfofun.freephone.restful.RestfulResponseUtils;
import com.freephone.justfofun.freephone.restful.model.CallPhoneParam;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/13.
 */

public class DailActivityPresenter extends MvpBasePresenter<DailActivityView>{
    private Context mContext;
    private Subscription callPhoneWhenNotOpenPhoneConsultSubscription;
    @Inject
    ApiService apiService;

    @Inject
    MyAccountManager myAccountManager;

    @Inject
    public DailActivityPresenter(Context context){
        this.mContext = context.getApplicationContext();
    }

    public void tryLogout(){
        myAccountManager.deleteAccount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(()->{
                    if (getView()!=null) getView().getOut();
                })
                .subscribe(result->{
                    if(result){
                        if(getView()!=null) getView().getOutSuccess();
                    }else {
                        if (getView()!=null) getView().getOutFailed();
                    }
                });
    }

    public void callPhoneWhenNotOpenPhoneConsult(String callerPhoneNumber, String receiverPhoneNumber) {
        if (callPhoneWhenNotOpenPhoneConsultSubscription == null) {
            CallPhoneParam param = new CallPhoneParam();
            param.setBusinessId(1);
            param.setProductId(1);
            param.setPhoneCaller(callerPhoneNumber);
            param.setPhoneReceiver(receiverPhoneNumber);
            param.setCallerShowPhoneNumber(receiverPhoneNumber);
            param.setReceiverShowPhoneNumber(callerPhoneNumber);

            callPhoneWhenNotOpenPhoneConsultSubscription = apiService.callPhoneWhenNotOpenPhoneConsult(param)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .flatMap(RestfulResponseUtils::processorResponse)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(voidRestfulResponse -> {
                                if (getView() != null) {
                                    getView().dailSuccess();
                                }
                                callPhoneWhenNotOpenPhoneConsultSubscription = null;
                            }
                            , throwable -> {
                                if (getView() != null) {
                                    getView().dialFailed(RestfulResponseUtils.getErrorMessageFromThrowable(mContext, throwable));
                                }
                                callPhoneWhenNotOpenPhoneConsultSubscription = null;
                            }
                    );

        }
    }
}
