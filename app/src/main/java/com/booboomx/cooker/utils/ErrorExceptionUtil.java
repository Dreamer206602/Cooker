package com.booboomx.cooker.utils;

/**
 * Created by booboomx on 17/4/21.
 */

public class ErrorExceptionUtil {

    public static String getErrorMsg(Throwable e){

        if(null == e || null == e.getMessage())
            return "网络出问题了";

        String msg;
        String err = e.getMessage();

        if(err.equals("No address associated with hostname")){
            msg = "网络没有连接";
        }
        else{
            msg = "网络出问题了";
        }

        return msg;
    }
}
