package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.login.OnLoginCallback;

/**
 * Created by M on 2015/3/23.
 */
public interface LoginInteractor {

    void login(String username, String password, OnLoginCallback onLoginCallback);

}
