package com.twt.service.wenjin.ui.profile.edit;

import android.graphics.Bitmap;

import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.interactor.ProfileEditInteractor;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.profile.ProfileActivity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rex on 2015/7/25.
 */
public class ProfileEditPresenterImpl implements ProfileEditPresenter, OnGetUserInfoCallback, OnPostUserInfoCallBack, OnUploadAvatarCallback {
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
    public void postUserInfo(int uid, String nickname, String signature){
        interactor.OnPostUserInfo(uid, nickname, signature, this);
        view.finishActivity();
    }

    @Override
    public void uploadAvatar(int uid, String user_avatar) throws FileNotFoundException {
        view.showProgressDialog();
        interactor.onUploadAvatar(uid, user_avatar, this);
    }

    @Override
    public void onPostSuccess() {
        view.toastMessage("修改成功！");
    }

    @Override
    public void onPostFailure(String errorMsg) {
        view.toastMessage(errorMsg);
    }

    @Override
    public void onUploadSuccess() {
        view.hideProgressDialog();
        view.toastMessage("头像上传成功！");
    }

    @Override
    public void onUploadFailure(String errorMsg) {
        view.hideProgressDialog();
        view.showFailureDialog(errorMsg);
    }
}
