package com.freephone.justfofun.freephone.restful;

import android.content.Context;


import com.freephone.justfofun.freephone.R;
import com.freephone.justfofun.freephone.common.Logger;
import com.freephone.justfofun.freephone.exception.DoctorException;
import com.freephone.justfofun.freephone.exception.DoctorRuntimeException;


import rx.Observable;

/**
 * Created by imorn on 15/12/11.
 */
public class RestfulResponseUtils {


    /**
     * 处理返回结果
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> Observable<T> processorResult(RestfulResponse<T> response) {
        return Observable.create(subscriber -> {
            ResultCode resultCode = response.getCode();
            if (ResultCode.Success == resultCode) {
                subscriber.onNext(response.getResult());
                subscriber.onCompleted();
            } else {
                subscriber.onError(new DoctorRuntimeException(response.getMessage(), resultCode));
            }
        });
    }

    /**
     * 处理返回结果
     *
     * @param response
     * @param <T>
     * @return
     */
    public static <T> Observable<RestfulResponse<T>> processorResponse(RestfulResponse<T> response) {
        return Observable.create(subscriber -> {
            ResultCode resultCode = response.getCode();
            if (ResultCode.Success == resultCode) {
                subscriber.onNext(response);
            } else {
                subscriber.onError(new DoctorRuntimeException(response.getMessage(), resultCode));
            }
        });
    }


    /**
     * 从异常获取错误状态
     *
     * @param context
     * @param throwable
     * @return
     */
    public static String getErrorMessageFromThrowable(Context context, Throwable throwable) {
        Logger.getDefaultLog().e("error", throwable.getMessage(), throwable);
        if (throwable instanceof DoctorRuntimeException
                || throwable instanceof DoctorException) {
            return throwable.getMessage();
        } else {
            return context.getString(R.string.error_message_net_work_error);
        }
    }

}
