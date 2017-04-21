package com.booboomx.cooker.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by booboomx on 17/4/21.
 */

public class ToastUtils {

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msgID){
        Toast.makeText(context, msgID, Toast.LENGTH_SHORT).show();
    }
}
