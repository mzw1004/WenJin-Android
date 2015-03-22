package com.twt.service.wenjin.support;

import com.twt.service.wenjin.WenJinApp;

/**
 * Created by M on 2015/3/22.
 */
public class ResourcesUtil {

    public static int getColor(int colorId) {
        return WenJinApp.getContext().getResources().getColor(colorId);
    }
}
