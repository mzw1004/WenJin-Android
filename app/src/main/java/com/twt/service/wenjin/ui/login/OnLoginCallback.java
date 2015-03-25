package com.twt.service.wenjin.ui.login;

import com.twt.service.wenjin.bean.UserInfo;

/**
 * Created by M on 2015/3/23.
 */
public interface OnLoginCallback {

    void onSuccess(UserInfo userInfo);

    void onFailure(String errorString);

}
