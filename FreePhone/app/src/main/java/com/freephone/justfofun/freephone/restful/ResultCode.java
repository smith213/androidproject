package com.freephone.justfofun.freephone.restful;


import com.freephone.justfofun.freephone.restful.serializer.GsonEnum;

/**
 * Created by imorn on 15/12/2.
 */
public enum ResultCode implements GsonEnum<ResultCode> {


    Success(0),
    DefaultError(Integer.MAX_VALUE),
    SessionInvalidateError(-1),
    DeletePateintError(-2),
    ValidateError(-3),

    NotFoundSDCard(-3),
    //TODO  待定
    NetError(-4),
    UnLoginError(-2),
    VerifyError(1403);

    private int code;

    ResultCode(int code) {
        this.code = code;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public ResultCode getDefault(){
        return DefaultError;
    }
}
