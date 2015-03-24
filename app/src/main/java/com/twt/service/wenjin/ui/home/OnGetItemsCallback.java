package com.twt.service.wenjin.ui.home;

import com.twt.service.wenjin.bean.HomeResponseMessage;

/**
 * Created by M on 2015/3/24.
 */
public interface OnGetItemsCallback {

    void onSuccess(HomeResponseMessage homeResponseMessage);

    void onFailure(String errorString);

}
