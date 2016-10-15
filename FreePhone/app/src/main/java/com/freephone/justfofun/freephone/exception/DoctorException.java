package com.freephone.justfofun.freephone.exception;


import com.freephone.justfofun.freephone.restful.ResultCode;

/**
 * Created by imorn on 15/11/13.
 */
public class DoctorException extends Exception {

    private ResultCode resultCode;

    public DoctorException() {
        resultCode = ResultCode.DefaultError;
    }

    public DoctorException(String detailMessage) {
        super(detailMessage);
    }

    public DoctorException(String detailMessage, ResultCode resultCode) {
        this(detailMessage);
        this.resultCode = resultCode;
    }

    public DoctorException(String detailMessage, Throwable throwable, ResultCode resultCode) {
        this(detailMessage, throwable);
        this.resultCode = resultCode;
    }

    public DoctorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DoctorException(Throwable throwable, ResultCode resultCode) {
        this(throwable);
        this.resultCode = resultCode;
    }

    public DoctorException(Throwable throwable) {
        super(throwable);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
