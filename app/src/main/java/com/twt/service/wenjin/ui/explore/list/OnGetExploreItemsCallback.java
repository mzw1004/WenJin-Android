package com.twt.service.wenjin.ui.explore.list;

import com.twt.service.wenjin.bean.ExploreResponse;

/**
 * Created by WGL on 2015/3/28.
 */
public interface OnGetExploreItemsCallback {

    void onSuccess(ExploreResponse exploreResponse);

    void onFailed(String errorString);
}
