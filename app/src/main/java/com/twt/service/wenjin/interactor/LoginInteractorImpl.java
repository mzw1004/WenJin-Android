package com.twt.service.wenjin.interactor;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.ui.login.OnLoginCallback;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by M on 2015/3/23.
 */
public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(String username, String password, final OnLoginCallback onLoginCallback){
        ApiClient.userLogin(username, password, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                onLoginCallback.onSuccess();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                onLoginCallback.onFailure();
            }
        });
    }
}
