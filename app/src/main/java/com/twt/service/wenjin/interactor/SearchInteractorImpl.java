package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.SearchArticle;
import com.twt.service.wenjin.bean.SearchQuestion;
import com.twt.service.wenjin.bean.SearchTopic;
import com.twt.service.wenjin.bean.SearchUser;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.search.OnGetSearchCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Green on 15/11/12.
 */
public class SearchInteractorImpl implements SearchInteractor {
    private static final String LOG_TAG = SearchInteractorImpl.class.getSimpleName();

    @Override
    public void searchContent(String searchinfo, final String type, int page,final OnGetSearchCallback callback) {
        ApiClient.searchContent(searchinfo,type, page, 10,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, response.toString());
                Object object = new Object();
                try {
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            LogHelper.v(LOG_TAG,response.toString());
                            JSONArray jsonArray = response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONArray("info");
                            switch (type){
                                case "topics":
                                    object = gson.fromJson(jsonArray.toString(),new TypeToken<List<SearchTopic>>(){}.getType());
                                    break;
                                case "questions":
                                    object = gson.fromJson(jsonArray.toString(),new TypeToken<List<SearchQuestion>>(){}.getType());
                                    break;
                                case "articles":
                                    object = gson.fromJson(jsonArray.toString(),new TypeToken<List<SearchArticle>>(){}.getType());
                                    break;
                                case "users":
                                    object = gson.fromJson(jsonArray.toString(),new TypeToken<List<SearchUser>>(){}.getType());
                                    break;
                            }
                            callback.OnGetSearchSuccess(object);
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
