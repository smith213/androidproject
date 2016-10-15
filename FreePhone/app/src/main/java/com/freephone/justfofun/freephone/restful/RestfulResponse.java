package com.freephone.justfofun.freephone.restful;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by imorn on 15/12/1.
 */
public class RestfulResponse<Result> {

    @Expose
    @SerializedName("respCode")
    private ResultCode code;

    @Expose
    @SerializedName("respMsg")
    private String message;

    @Expose
    private Result result;

    public RestfulResponse(ResultCode code, String message) {
        this(code, message, null);
    }

    public RestfulResponse(ResultCode code, String message, Result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RestfulResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}