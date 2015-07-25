package com.twt.service.wenjin.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.twt.service.wenjin.BuildConfig;
import com.twt.service.wenjin.WenJinApp;

import java.io.File;

/**
 * Created by M on 2015/4/18.
 */
public class DeviceUtils {

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
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

    public static String getSource() {
        return getBrand() + " " + getModel();
    }

    public static String getNetworkType() {
        NetworkInfo info = ((ConnectivityManager) WenJinApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (info != null) {
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return "WIFI";
                case ConnectivityManager.TYPE_MOBILE:
                    return "GPRS";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    public static boolean isRooted() {
        boolean isRooted;

        try {
            isRooted = !((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists()));
        } catch (Exception e) {
            return false;
        }

        return isRooted;
    }

}
