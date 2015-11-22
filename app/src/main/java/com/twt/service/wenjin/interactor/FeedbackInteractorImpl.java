package com.twt.service.wenjin.interactor;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.ui.feedback.OnPublishFeedbackCallback;

import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/4/18.
 */
public class FeedbackInteractorImpl implements FeedbackInteractor {
    @Override
    public void publishFeedback(String title, String message, final OnPublishFeedbackCallback callback) {
        ApiClient.publishFeedback(title, message, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            callback.onPublishSuccess();
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onPublishFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
