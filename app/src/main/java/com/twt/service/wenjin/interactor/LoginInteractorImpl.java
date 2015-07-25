package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.login.OnLoginCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.GZIPOutputStream;

/**
 * Created by M on 2015/3/23.
 */
public class LoginInteractorImpl implements LoginInteractor {

    private static final String LOG_TAG = LoginInteractorImpl.class.getSimpleName();

    @Override
    public void login(String username, String password, final OnLoginCallback onLoginCallback){
        ApiClient.userLogin(username, password, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, response.toString());
                try {
                    int errorCode = response.getInt(ApiClient.RESP_ERROR_CODE_KEY);
                    switch (errorCode) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            UserInfo userInfo = gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), UserInfo.class);
                            onLoginCallback.onSuccess(userInfo);
                            break;
                        case ApiClient.ERROR_CODE:
                            onLoginCallback.onFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
//                LogHelper.v(LOG_TAG, errorResponse.toString());
            }
        });
    }
}
