package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.ui.answer.OnAnswerCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/4/5.
 */
public class AnswerInteractorImpl implements AnswerInteractor {

    @Override
    public void publishAnswer(int questionId, String content, String attachKey, final OnAnswerCallback callback) {
        ApiClient.answer(questionId, content, attachKey, new JsonHttpResponseHandler() {
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
}
