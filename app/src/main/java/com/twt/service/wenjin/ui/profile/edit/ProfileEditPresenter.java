package com.twt.service.wenjin.ui.profile.edit;

/**
 * Created by Rex on 2015/7/25.
 */
public interface ProfileEditPresenter {
    void getUserInfo(int uid);
    void postUserInfo(int uid,String nickname, String signature);
}
