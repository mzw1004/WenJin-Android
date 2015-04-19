package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.HomeResponse;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.home.OnGetItemsCallback;
import com.twt.service.wenjin.ui.publish.OnPublishCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/3/24.
 */
public class HomeInteractorImpl implements HomeInteractor {

    private static final String LOG_TAG = HomeInteractorImpl.class.getSimpleName();

    @Override
    public void getHomeItems(int perPgae, int page, final OnGetItemsCallback onGetItemsCallback) {
        ApiClient.getHome(perPgae, page, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, response.toString());
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            HomeResponse hrm =
                                    gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), HomeResponse.class);
                            onGetItemsCallback.onSuccess(hrm);
                            break;
                        case ApiClient.ERROR_CODE:
                            onGetItemsCallback.onFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                onGetItemsCallback.onFailure(responseString);
            }
        });
    }

}
