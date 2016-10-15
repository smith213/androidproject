package com.freephone.justfofun.freephone.exception;


import com.freephone.justfofun.freephone.restful.ResultCode;

/**
 * Created by imorn on 15/12/3.
 */
public class DoctorRuntimeException extends RuntimeException {

    private ResultCode resultCode;

    public DoctorRuntimeException() {
        resultCode = ResultCode.DefaultError;
    }

    public DoctorRuntimeException(String detailMessage) {
        super(detailMessage);
    }

    public DoctorRuntimeException(String detailMessage, ResultCode resultCode) {
        this(detailMessage);
        this.resultCode = resultCode;
    }

    public DoctorRuntimeException(String detailMessage, Throwable throwable, ResultCode resultCode) {
        this(detailMessage, throwable);
        this.resultCode = resultCode;
    }

    public DoctorRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DoctorRuntimeException(Throwable throwable, ResultCode resultCode) {
        this(throwable);
        this.resultCode = resultCode;
    }

    public DoctorRuntimeException(Throwable throwable) {
        super(throwable);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
