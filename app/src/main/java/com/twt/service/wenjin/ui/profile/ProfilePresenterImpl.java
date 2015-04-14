package com.twt.service.wenjin.ui.profile;

import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.interactor.ProfileInteractor;
import com.twt.service.wenjin.support.LogHelper;

/**
 * Created by M on 2015/4/5.
 */
public class ProfilePresenterImpl implements ProfilePresenter, OnGetUserInfoCallback {

    private static final String LOG_TAG = ProfilePresenterImpl.class.getSimpleName();

    private ProfileView mView;
    private ProfileInteractor mInteractor;

    public ProfilePresenterImpl(ProfileView view, ProfileInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void getUserInfo(int uid) {
        mInteractor.onGetUserInfo(uid, this);
    }

    @Override
    public void onGetSuccess(UserInfo userInfo) {
        LogHelper.i(LOG_TAG, "signature: " + userInfo.signature);
    }

    @Override
    public void onGetFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }
}
