package com.twt.service.wenjin.ui.profile;

import com.twt.service.wenjin.interactor.ProfileInteractor;

/**
 * Created by M on 2015/4/5.
 */
public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileView mView;
    private ProfileInteractor mInteractor;

    public ProfilePresenterImpl(ProfileView view, ProfileInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }
}
