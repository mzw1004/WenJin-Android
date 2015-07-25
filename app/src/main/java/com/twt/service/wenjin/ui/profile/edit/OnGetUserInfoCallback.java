package com.twt.service.wenjin.ui.profile.edit;

import com.twt.service.wenjin.bean.UserInfo;

/**
 * Created by Rex on 2015/7/25.
 */
public interface OnGetUserInfoCallback {
    void onGetSuccess(UserInfo userInfo);
    void onGetFailure(String errorMsg);
}
