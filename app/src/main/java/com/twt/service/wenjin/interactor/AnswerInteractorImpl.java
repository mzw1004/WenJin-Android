package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.answer.OnAnswerCallback;
import com.twt.service.wenjin.ui.answer.OnUploadCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by M on 2015/4/5.
 */
public class AnswerInteractorImpl implements AnswerInteractor {

    @Override
    public void publishAnswer(int questionId, String content, String attachKey, boolean isAnonymous, final OnAnswerCallback callback) {
        ApiClient.answer(questionId, content, attachKey, isAnonymous, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            int answerId = response.getJSONObject(ApiClient.RESP_MSG_KEY).getInt("answer_id");
                            callback.onAnswerSuccess(answerId);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onAnswerFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
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
        ApiClient.uploadFile("answer", attachKey, file, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
//                            LogHelper.v(LOG_TAG, "upload response: " + response.toString());
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
