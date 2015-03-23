package com.twt.service.wenjin.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by M on 2015/3/23.
 */
public class ApiClient {

    private static final AsyncHttpClient sClient = new AsyncHttpClient();

    private static final String BASE_URL = "http://2014shequ.twtstudio.com/";
    private static final String LOGIN_URL = "?/api/account/login_process/";

    public static AsyncHttpClient getInstance() {
        return sClient;
    }

    public static void userLogin(String username, String password, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_name", username);
        params.put("password", password);

        sClient.post(BASE_URL + LOGIN_URL, params, handler);
    }
}
