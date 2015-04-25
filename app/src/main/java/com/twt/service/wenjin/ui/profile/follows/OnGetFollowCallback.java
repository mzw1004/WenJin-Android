package com.twt.service.wenjin.ui.profile.follows;

import com.twt.service.wenjin.bean.FollowsResponse;

/**
 * Created by Administrator on 2015/4/25.
 */
public interface OnGetFollowCallback {

    void onGetFollowersSuccess(FollowsResponse response);

    void onGetFollowersFailed(String errorMsg);
}
