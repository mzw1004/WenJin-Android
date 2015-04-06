package com.twt.service.wenjin.ui.profile;

import com.twt.service.wenjin.bean.UserInfo;

/**
 * Created by M on 2015/4/6.
 */
public interface OnGetUserInfoCallback {

    void onGetSuccess(UserInfo userInfo);

    void onGetFailure(String errorMsg);
}
