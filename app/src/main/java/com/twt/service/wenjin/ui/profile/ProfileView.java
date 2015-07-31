package com.twt.service.wenjin.ui.profile;

import com.twt.service.wenjin.bean.UserInfo;

/**
 * Created by M on 2015/4/5.
 */
public interface ProfileView {

    void bindUserInfo(UserInfo userInfo);

    void toastMessage(String msg);

    void addFocus();

    void removeFocus();

    void showProgressBar();

    void hideProgressBar();
}
