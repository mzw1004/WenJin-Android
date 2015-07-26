package com.twt.service.wenjin.interactor;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.ui.profile.edit.OnGetUserInfoCallback;
import com.twt.service.wenjin.ui.profile.edit.OnPostUserInfoCallBack;
import com.twt.service.wenjin.ui.profile.edit.OnUploadAvatarCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Rex on 2015/7/25.
 */
public class ProfileEditInteractorImpl implements ProfileEditInteractor {
    @Override
    public void OnGetUserInfo(int uid, final OnGetUserInfoCallback callback) {

        ApiClient.getUserInfo(uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Log.e("json", response.getJSONObject(ApiClient.RESP_MSG_KEY).toString());
                            Gson gson = new Gson();
                            UserInfo userInfo = gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), UserInfo.class);
                            callback.onGetSuccess(userInfo);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onGetFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void OnPostUserInfo(int uid, final String nick_name, final String signature, final OnPostUserInfoCallBack callBack) {
        ApiClient.editProfile(uid, nick_name, signature, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            callBack.onPostSuccess();
                            break;
                        case ApiClient.ERROR_CODE:
                            callBack.onPostFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onUploadAvatar(int uid, final String user_avatar, final OnUploadAvatarCallback callback) throws FileNotFoundException {

        if (!user_avatar.equals("") && user_avatar != null) {
            ApiClient.avatarUpload(uid, user_avatar, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    try {
                        switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                            case ApiClient.SUCCESS_CODE:
                                Log.e("上传成功", "上传成功");
                                callback.onUploadSuccess();
                                break;
                            case ApiClient.ERROR_CODE:
                                callback.onUploadFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}


