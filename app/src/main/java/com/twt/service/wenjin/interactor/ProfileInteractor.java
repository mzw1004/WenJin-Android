package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.profile.OnGetUserInfoCallback;
import com.twt.service.wenjin.ui.profile.OnUserFocusCallback;

/**
 * Created by M on 2015/4/5.
 */
public interface ProfileInteractor {

    void onGetUserInfo(int uid, OnGetUserInfoCallback callback);

    void actionFocus(int uid, OnUserFocusCallback callback);

}
