package com.twt.service.wenjin.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.support.JSONHelper;
import com.twt.service.wenjin.support.LogHelper;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by M on 2015/11/23.
 */
public abstract class ResponseHandler extends JsonHttpResponseHandler {
    private static final String LOG_TAG = ResponseHandler.class.getSimpleName();
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

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        switch (JSONHelper.getInt(response, RESP_ERROR_CODE_KEY)) {
            case SUCCESS_CODE:
                JSONObject jsonObject = JSONHelper.getJSONObject(response, RESP_MSG_KEY);
                if (jsonObject != null) {
                    success(jsonObject);
                } else {
                    failure("JSON解析错误");
                }
                break;
            case ERROR_CODE:
                failure(JSONHelper.getString(response, RESP_ERROR_MSG_KEY));
                break;
        }
    }

    public abstract void success(JSONObject response);

    public abstract void failure(String error);
}
