package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.answer.detail.OnGetAnswerCallback;
import com.twt.service.wenjin.ui.question.OnGetQuestionCallback;

import org.apache.http.entity.mime.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/3/29.
 */
public class AnswerDetailInteractorImpl implements AnswerDetailInteractor {

    @Override
    public void getAnswer(int answerId, final OnGetAnswerCallback callback) {
        ApiClient.getAnswer(answerId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            Answer answer = gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), Answer.class);
                            callback.onSuccess(answer);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

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
                            QuestionResponse qr =
                                    gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), QuestionResponse.class);
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

}
