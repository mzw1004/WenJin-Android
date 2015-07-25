package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.ui.profile.edit.OnGetUserInfoCallback;
import com.twt.service.wenjin.ui.profile.edit.OnPostUserInfoCallBack;

/**
 * Created by Rex on 2015/7/25.
 */
public interface ProfileEditInteractor {
    void OnGetUserInfo(int uid, OnGetUserInfoCallback callback);
    void OnPostUserInfo(int uid, String nick_name, String signature, OnPostUserInfoCallBack callBack);
}
