package com.twt.service.wenjin.ui.login;

/**
 * Created by M on 2015/3/23.
 */
public interface LoginView {

    void usernameError(String errorString);

    void passwordError(String errorString);

    void showProgressBar();

    void hideProgressBar();

    void toastMessage(String msg);

    void startMainActivity();

}
