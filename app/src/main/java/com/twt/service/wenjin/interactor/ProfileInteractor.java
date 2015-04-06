package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.profile.OnGetUserInfoCallback;

/**
 * Created by M on 2015/4/5.
 */
public interface ProfileInteractor {

    void onGetUserInfo(int uid, OnGetUserInfoCallback callback);

}
