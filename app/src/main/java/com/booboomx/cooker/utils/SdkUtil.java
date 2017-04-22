package com.booboomx.cooker.utils;

import android.os.Build;

/**
 * Created by booboomx on 17/4/22.
 */

public class SdkUtil {
    public static boolean sdkVersionGe(int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    public static boolean sdkVersionEq(int version) {
        return Build.VERSION.SDK_INT == version;
    }

    public static boolean sdkVersionLt(int version) {
        return Build.VERSION.SDK_INT < version;
    }

    public static boolean sdkVersionGe19() {
        return sdkVersionGe(19);
    }

    public static boolean sdkVersionGe21() {
        return sdkVersionGe(21);
    }
}
