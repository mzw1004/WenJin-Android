package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.ui.profile.OnGetUserInfoCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/4/5.
 */
public class ProfileInteractorImpl implements ProfileInteractor {

    @Override
    public void onGetUserInfo(int uid, final OnGetUserInfoCallback callback) {
        ApiClient.getUserInfo(uid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
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
}
