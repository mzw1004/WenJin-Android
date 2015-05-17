package com.twt.service.wenjin.support;

import android.app.Activity;
import android.os.Build;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.twt.service.wenjin.BuildConfig;
import com.twt.service.wenjin.R;

/**
 * Created by Jinsen on 15/5/12.
 */
public class StatusBarHelper {
    /**
     * Call this method after setContentView<br>
     * Make status bar immensable!<br>
     * @param activity
     */
    public static void setStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(activity.getResources().getColor(R.color.color_primary));
        }
    }
}
