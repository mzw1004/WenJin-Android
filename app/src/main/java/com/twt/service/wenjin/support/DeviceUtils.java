package com.twt.service.wenjin.support;

import android.os.Build;

import com.twt.service.wenjin.BuildConfig;

/**
 * Created by M on 2015/4/18.
 */
public class DeviceUtils {

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

}
