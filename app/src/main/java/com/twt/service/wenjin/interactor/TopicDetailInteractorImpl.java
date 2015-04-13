package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Topic;
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
}
