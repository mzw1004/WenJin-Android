package com.twt.service.wenjin.ui.home;

import com.twt.service.wenjin.bean.HomeResponse;

/**
 * Created by M on 2015/3/24.
 */
public interface OnGetItemsCallback {

    void onSuccess(HomeResponse homeResponse);

    void onFailure(String errorString);

}
