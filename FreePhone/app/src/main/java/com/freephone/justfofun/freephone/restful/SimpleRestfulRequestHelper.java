package com.freephone.justfofun.freephone.restful;

import com.freephone.justfofun.freephone.exception.DoctorException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observable;

/**
 * 简单请求
 * <p>
 * Created by imorn on 16/1/13.
 */
public class SimpleRestfulRequestHelper {


    public static <T> RestfulResponse<T> post(String url, Map<String, Object> param) throws DoctorException {
        try {
            Gson gson = GsonCreator.getGson();
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), gson.toJson(param)))
                    .build();
            String str = executeRequest(request);
            return gson.fromJson(str, new TypeToken<RestfulResponse<T>>() {
            }.getType());
        } catch (Exception e) {
            throw new DoctorException(e, ResultCode.NetError);
        }
    }


    public static <T> Observable<RestfulResponse<T>> postObservable(String url, Map<String, Object> param) {
        return Observable.create(subscriber -> {
            try {
                RestfulResponse<T> response = post(url, param);
                subscriber.onNext(response);
                subscriber.onCompleted();
            } catch (DoctorException e) {
                subscriber.onError(e);
            }
        });
    }

    public static <T> Observable<RestfulResponse<T>> getObservable(String url) {
        return Observable.create(subscriber -> {
            try {
                RestfulResponse<T> response = get(url);
                subscriber.onNext(response);
                subscriber.onCompleted();
            } catch (DoctorException e) {
                subscriber.onError(e);
            }
        });
    }

    public static <T> RestfulResponse<T> get(String url) throws DoctorException {
        try {
            Gson gson = GsonCreator.getGson();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            return gson.fromJson(executeRequest(request), new TypeToken<RestfulResponse<T>>() {
            }.getType());
        } catch (Exception e) {
            throw new DoctorException(e, ResultCode.NetError);
        }
    }


    private static String executeRequest(Request request) throws IOException {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("response code = " + response.code());
        }
    }


}
