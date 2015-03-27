package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.ui.question.OnGetQuestionCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/3/27.
 */
public class QuestionInteractorImpl implements QuestionInteractor {

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
                            onGetQuestionCallback.onSuccess(qr);
                            break;
                        case ApiClient.ERROR_CODE:
                            onGetQuestionCallback.onFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
