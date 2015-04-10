package com.twt.service.wenjin.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.twt.service.wenjin.WenJinApp;

/**
 * Created by M on 2015/3/24.
 */
public class NetworkHelper {

    public static boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                WenJinApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
