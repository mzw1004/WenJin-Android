package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.question.OnFocusedCallback;
import com.twt.service.wenjin.ui.question.OnGetQuestionCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/3/27.
 */
public class QuestionInteractorImpl implements QuestionInteractor {

    private static final String LOG_TAG = QuestionInteractorImpl.class.getSimpleName();

    @Override
    public void getQuestionContent(int questionId, final OnGetQuestionCallback onGetQuestionCallback) {
        ApiClient.getQuestion(questionId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            LogHelper.v(LOG_TAG, response.getJSONObject(ApiClient.RESP_MSG_KEY).toString());
                            QuestionResponse qr =
                                    gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), QuestionResponse.class);
                            LogHelper.v(LOG_TAG, "id: " + qr.question_info.question_id);
                            LogHelper.v(LOG_TAG, "content: " + qr.question_info.question_content);
                            LogHelper.v(LOG_TAG, "detail: " + qr.question_info.question_detail);
                            LogHelper.v(LOG_TAG, "focus count: " + qr.question_info.focus_count);
                            LogHelper.v(LOG_TAG, "answer count: " + qr.answer_count);
                            onGetQuestionCallback.onGetQuestionSuccess(qr);
                            break;
                        case ApiClient.ERROR_CODE:
                            onGetQuestionCallback.onGetQuestionFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionFocus(int questionId, final OnFocusedCallback callback) {
        ApiClient.focusQuestion(questionId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            LogHelper.v(LOG_TAG, response.getString(ApiClient.RESP_MSG_KEY));
                            if (response.getJSONObject(ApiClient.RESP_MSG_KEY).getString("type").equals("add")) {
                                callback.onFocusSuccess(true);
                            } else if (response.getJSONObject(ApiClient.RESP_MSG_KEY).getString("type").equals("remove")) {
                                callback.onFocusSuccess(false);
                            }
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onFocusFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
