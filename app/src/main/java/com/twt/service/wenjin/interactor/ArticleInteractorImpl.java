package com.twt.service.wenjin.interactor;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Article;
import com.twt.service.wenjin.ui.article.OnGetArticleCallback;

import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RexSun on 15/7/16.
 */
public class ArticleInteractorImpl implements ArticleInteractor {
    @Override
    public void getArticle(int articleId, final OnGetArticleCallback callback) {
        ApiClient.getArticle(articleId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("here", "here");
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            Article article = gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), Article.class);
                            Log.e("json", response.getJSONObject(ApiClient.RESP_MSG_KEY).toString());
                            callback.onSuccess(article);
                            break;
                        case ApiClient.ERROR_CODE:
                            Log.e("error message", response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            callback.onFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
