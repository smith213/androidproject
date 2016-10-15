package com.freephone.justfofun.freephone.restful;

import com.freephone.justfofun.freephone.restful.model.CallPhoneParam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by imorn on 15/12/1.
 */
public interface ApiService {
    /**
     * 拨打电话(没有开启电话咨询业务时调用)
     *
     * @param param
     * @return
     */
    @POST("/sms/call/twoway")
    Observable<RestfulResponse<Void>> callPhoneWhenNotOpenPhoneConsult(@Body CallPhoneParam param);
}
