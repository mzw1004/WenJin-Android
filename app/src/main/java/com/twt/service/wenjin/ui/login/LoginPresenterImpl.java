package com.twt.service.wenjin.ui.login;

import android.text.TextUtils;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.interactor.LoginInteractor;
import com.twt.service.wenjin.support.ResourcesUtil;

/**
 * Created by M on 2015/3/23.
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginCallback {

    private LoginView mLoginView;
    private LoginInteractor mLoginInteractor;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor) {
        this.mLoginView = loginView;
        this.mLoginInteractor = loginInteractor;
    }

    @Override
    public void validateLogin(String username, String password) {
        mLoginView.showProgressBar();
        if (TextUtils.isEmpty(username)) {
            mLoginView.usernameError(ResourcesUtil.getString(R.string.login_error_empty));
            mLoginView.hideProgressBar();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mLoginView.passwordError(ResourcesUtil.getString(R.string.login_error_empty));
            mLoginView.hideProgressBar();
            return;
        }
        mLoginInteractor.login(username, password, this);
    }

    @Override
    public void onSuccess() {
        mLoginView.hideProgressBar();
    }

    @Override
    public void onFailure() {
        mLoginView.hideProgressBar();
    }
}
