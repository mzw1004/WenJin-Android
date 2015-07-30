package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.ui.profile.edit.OnGetUserInfoCallback;
import com.twt.service.wenjin.ui.profile.edit.OnPostUserInfoCallBack;
import com.twt.service.wenjin.ui.profile.edit.OnUploadAvatarCallback;

import java.io.FileNotFoundException;

/**
 * Created by Rex on 2015/7/25.
 */
public interface ProfileEditInteractor {
    void OnGetUserInfo(int uid, OnGetUserInfoCallback callback);
    void OnPostUserInfo(int uid, String nick_name, String signature, OnPostUserInfoCallBack callBack);
    void onUploadAvatar(int uid, String user_avatar, OnUploadAvatarCallback callback) throws FileNotFoundException;
}
