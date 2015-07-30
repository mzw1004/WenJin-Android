package com.twt.service.wenjin.interactor;

import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Comment;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.answer.comment.OnGetCommentCallback;
import com.twt.service.wenjin.ui.answer.comment.OnPublishCommentCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M on 2015/4/6.
 */
public class CommentInteractorImpl implements CommentInteractor {

    private static final String LOG_TAG = CommentInteractorImpl.class.getSimpleName();

    @Override
    public void loadComments(int answerId, final OnGetCommentCallback callback) {
        ApiClient.getComments(answerId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, "comment response: " + response.toString());
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            Comment[] comments = gson.fromJson(response.getJSONArray(ApiClient.RESP_MSG_KEY).toString(), Comment[].class);
                            callback.onGetCommentSuccess(comments);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onGetCommentFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void publishComment(int answerId, String content, final View view, final OnPublishCommentCallback callback) {
        ApiClient.publishComment(answerId, content, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, "publish comment response: " + response.toString());
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            callback.onPublishSuccess(view);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onPublishFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY), view);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
