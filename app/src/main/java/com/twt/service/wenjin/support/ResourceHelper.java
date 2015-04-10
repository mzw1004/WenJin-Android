package com.twt.service.wenjin.support;

import com.twt.service.wenjin.WenJinApp;

/**
 * Created by M on 2015/3/22.
 */
public class ResourceHelper {

    public static int getColor(int colorId) {
        return WenJinApp.getContext().getResources().getColor(colorId);
    }

    public static String getString(int stringId) {
        return WenJinApp.getContext().getResources().getString(stringId);
    }

    public static String[] getStringArrays(int arrayId) {
        return WenJinApp.getContext().getResources().getStringArray(arrayId);
    }
}
