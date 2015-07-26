package com.twt.service.wenjin.ui.profile.edit;

import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.interactor.ProfileEditInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rex on 2015/7/25.
 */
public class ProfileEditPresenterImpl implements ProfileEditPresenter, OnGetUserInfoCallback, OnPostUserInfoCallBack {
    private ProfileEditView view;
    private ProfileEditInteractor interactor;
    private List<String> filePath = new ArrayList<>();


    public ProfileEditPresenterImpl(ProfileEditView view, ProfileEditInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onGetSuccess(UserInfo userInfo) {
        view.bindUserInfo(userInfo);
    }

    @Override
    public void onGetFailure(String errorMsg) {
        view.toastMessage(errorMsg);

    }

    @Override
    public void getUserInfo(int uid) {
        interactor.OnGetUserInfo(uid, this);
    }

    @Override
    public void postUserInfo(int uid, String nickname, String signature) {
        view.showProgressBar();
        interactor.OnPostUserInfo(uid, nickname, signature,this);
        view.hideProgressBar();
    }

    @Override
    public void updateAvatar(int uid, String user_avatar) {

    }

    @Override
    public void onPostSuccess() {
        view.toastMessage("修改成功！");
        view.finishActivity();
    }

    @Override
    public void onPostFailure(String errorMsg) {
        view.toastMessage(errorMsg);
    }
}
