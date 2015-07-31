package com.twt.service.wenjin.interactor;

import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.ArticleComment;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.article.comment.OnGetCommentCallback;
import com.twt.service.wenjin.ui.article.comment.OnPublishCommentCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RexSun on 15/7/19.
 */
public class ArticleCommentInteractorImpl implements ArticleCommentInteractor {

    private static final String LOG_TAG = ArticleCommentInteractorImpl.class.getSimpleName();
    @Override
    public void loadComment(int articleId, final OnGetCommentCallback onGetCommentCallback) {
        ApiClient.getArticleComment(articleId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, "comment response: " + response.toString());
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            response = response.getJSONObject(ApiClient.RESP_MSG_KEY);
                            ArticleComment[] articleComments = gson.fromJson(response.getJSONArray("rows").toString(), ArticleComment[].class);
                            onGetCommentCallback.onGetArticleCommentSuccess(articleComments);
                            break;
                        case ApiClient.ERROR_CODE:
                            onGetCommentCallback.onGetArticleCommentFailure(ApiClient.RESP_ERROR_MSG_KEY);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void publishComment(int articleId, String content, final View view, final OnPublishCommentCallback onPublishCommentCallback) {
        ApiClient.publishArticleComment(articleId, content, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, "publish comment response: " + response.toString());
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            onPublishCommentCallback.onPublishSuccess(view);
                            break;
                        case ApiClient.ERROR_CODE:
                            onPublishCommentCallback.onPublishFailure(ApiClient.RESP_ERROR_CODE_KEY, view);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                LogHelper.v(LOG_TAG, "publish comment failed: " + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                LogHelper.v(LOG_TAG, "publish comment failed: " + throwable.toString());

            }

            @Override
            public void onFinish() {
                super.onFinish();

            }
        });



    }
}
