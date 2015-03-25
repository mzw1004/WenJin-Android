package com.twt.service.wenjin.api;

import android.support.annotation.Nullable;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by M on 2015/3/23.
 */
public class ApiClient {

    /*
    接口总是会返回一个json，包含三个字段
    rsm：成功时包含返回的数据，失败时此字段为null
    errno：1-成功，2-失败
    err：成功时为null，失败时包含错误原因，可原样输出
     */
    public static final String RESP_MSG_KEY = "rsm";
    public static final String RESP_ERROR_CODE_KEY = "errno";
    public static final String RESP_ERROR_MSG_KEY = "err";

    public static final int SUCCESS_CODE = 1;
    public static final int ERROR_CODE = -1;

    private static final AsyncHttpClient sClient = new AsyncHttpClient();
    private static final int DEFAULT_TIMEOUT = 20000;

    private static final String BASE_URL = "http://2014shequ.twtstudio.com/";
    private static final String LOGIN_URL = "?/api/account/login_process/";
    private static final String HOME_URL = "?/api/home/";

    static {
        sClient.setTimeout(DEFAULT_TIMEOUT);
    }

    public static AsyncHttpClient getInstance() {
        return sClient;
    }

    public static void userLogin(String username, String password, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_name", username);
        params.put("password", password);

        sClient.post(BASE_URL + LOGIN_URL, params, handler);
    }

    public static void getHome(int perPage, int page, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("per_page", perPage);
        params.put("page", page);

        sClient.get(BASE_URL + HOME_URL, params, handler);
    }

    public static String getAvatarUrl(String url) {
        return BASE_URL + "uploads/avatar/" + url;
    }
}
