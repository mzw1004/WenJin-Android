package com.twt.service.wenjin.interactor;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.ui.profile.edit.OnGetUserInfoCallback;
import com.twt.service.wenjin.ui.profile.edit.OnPostUserInfoCallBack;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rex on 2015/7/25.
 */
public class ProfileEditInteractorImpl implements ProfileEditInteractor{
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
    public void OnPostUserInfo(int uid, String nick_name, String signature, final OnPostUserInfoCallBack callBack) {
        ApiClient.editProfile(uid, nick_name, signature, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)){
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
    public void UploadAvatar(int uid, String path, final OnPostUserInfoCallBack callBack) {
        if (!path.equals("")&& path!=null){
            ApiClient.avatarUpload(uid, path, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)){
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
    }
}
