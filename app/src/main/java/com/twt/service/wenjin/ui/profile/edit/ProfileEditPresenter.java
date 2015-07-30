package com.twt.service.wenjin.ui.profile.edit;

import java.io.FileNotFoundException;

/**
 * Created by Rex on 2015/7/25.
 */
public interface ProfileEditPresenter {
    void getUserInfo(int uid);
    void postUserInfo(int uid, String nickname, String signature);
    void uploadAvatar(int uid, String user_avatar) throws FileNotFoundException;

}
