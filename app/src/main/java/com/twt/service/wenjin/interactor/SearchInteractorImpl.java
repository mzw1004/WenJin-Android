package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.SearchDetailArticle;
import com.twt.service.wenjin.bean.SearchDetailQuestion;
import com.twt.service.wenjin.bean.SearchDetailTopic;
import com.twt.service.wenjin.bean.SearchDetailUser;
import com.twt.service.wenjin.bean.SearchResponse;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.search.OnGetSearchCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Green on 15/11/12.
 */
public class SearchInteractorImpl implements SearchInteractor {
    private static final String LOG_TAG = SearchInteractorImpl.class.getSimpleName();

    @Override
    public void searchContent(String searchinfo, int page,final OnGetSearchCallback callback) {
        ApiClient.searchContent(searchinfo, page, 10,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, response.toString());
                SearchResponse searchResponse = new SearchResponse();
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            LogHelper.v(LOG_TAG,response.toString());
                            JSONArray jsonArray = response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONArray("info");
                            searchResponse.total_rows = response.getJSONObject(ApiClient.RESP_MSG_KEY).getInt("total_rows");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String type = jsonObject.getString("type");
                                LogHelper.v(LOG_TAG,jsonObject.toString());
                                switch (type){
                                    case "topics":
                                        SearchDetailTopic topic = gson.fromJson(jsonObject.getJSONObject("detail").toString(), SearchDetailTopic.class);

                                        searchResponse.topics.add(topic);
                                        LogHelper.v(LOG_TAG, "topic item add");
                                        break;
                                    case "questions":
                                        SearchDetailQuestion question = gson.fromJson(jsonObject.getJSONObject("detail").toString(), SearchDetailQuestion.class);
                                        question.header.name = jsonObject.getString("name");
                                        searchResponse.questions.add(question);
                                        LogHelper.v(LOG_TAG, "question item add");
                                        break;
                                    case "articles":
                                        SearchDetailArticle article = gson.fromJson(jsonObject.getJSONObject("detail").toString(), SearchDetailArticle.class);
                                        searchResponse.articles.add(article);
                                        LogHelper.v(LOG_TAG, "article item add");
                                        break;
                                    case "users":
                                        SearchDetailUser user = gson.fromJson(jsonObject.getJSONObject("detail").toString(), SearchDetailUser.class);
                                        searchResponse.users.add(user);
                                        LogHelper.v(LOG_TAG, "user item add");
                                        break;
                                }

                            }
                            callback.OnGetSearchSuccess(searchResponse);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.OnGetSearchFailure(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.OnGetSearchFailure(responseString);
            }
        });
    }
}
