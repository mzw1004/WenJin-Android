package com.twt.service.wenjin.ui.profile.edit;

import com.twt.service.wenjin.bean.UserInfo;

/**
 * Created by Rex on 2015/7/25.
 */
public interface ProfileEditView {
    void bindUserInfo(UserInfo userInfo);
    void showProgressBar();
    void hideProgressBar();
    void toastMessage(String msg);
    void finishActivity();
    void showProgressDialog();
    void hideProgressDialog();
    void showFailureDialog(String errorMsg);
}
