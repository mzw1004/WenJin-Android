package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.BestAnswer;
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.ui.topic.detail.OnFocusCallback;
import com.twt.service.wenjin.ui.topic.detail.OnGetBestAnswerCallback;
import com.twt.service.wenjin.ui.topic.detail.OnGetDetailCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/4/11.
 */
public class TopicDetailInteractorImpl implements TopicDetailInteractor {

    @Override
    public void getTopicDetail(int topicId, int uid, final OnGetDetailCallback callback) {
        ApiClient.getTopicDetail(topicId, uid, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            Topic topics = gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), Topic.class);
                            callback.onGetDetailSuccess(topics);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onGetDetailFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getTopicBestAnswer(int topicId, final OnGetBestAnswerCallback callback) {
        ApiClient.getTopicBestAnswer(topicId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            BestAnswer[] bestAnswers
                                    = gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONArray("rows").toString(),BestAnswer[].class);
                            callback.onGetAnswerSuccess(bestAnswers);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onGetAnswerFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionFocus(int topicId, final OnFocusCallback callback) {
        ApiClient.focusTopic(topicId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            if (response.getJSONObject(ApiClient.RESP_MSG_KEY).getString("type").equals("add")) {
                                callback.onFocusSuccess(true);
                            } else {
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
