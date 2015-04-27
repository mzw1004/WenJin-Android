package com.twt.service.wenjin.interactor;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.publish.OnPublishCallback;
import com.twt.service.wenjin.ui.publish.OnUploadCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by M on 2015/4/5.
 */
public class PublishInteractorImpl implements PublishInteractor {

    private static final String LOG_TAG = PublishInteractorImpl.class.getSimpleName();

    @Override
    public void publishQuestion(String title, String content, String attachKey, String topics, boolean isAnonymous, final OnPublishCallback callback) {
        ApiClient.publishQuestion(title, content, attachKey, topics, isAnonymous, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            LogHelper.v(LOG_TAG, "publish response: " + response.toString());
                            callback.publishSuccess(response.getJSONObject(ApiClient.RESP_MSG_KEY).getInt("question_id"));
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.publishFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void uploadFile(File file, String attachKey, final OnUploadCallback callback) {
        ApiClient.uploadFile("question", attachKey, file, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            LogHelper.v(LOG_TAG, "upload response: " + response.toString());
                            callback.onUploadSuccess(response.getJSONObject(ApiClient.RESP_MSG_KEY).getString("attach_id"));
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
