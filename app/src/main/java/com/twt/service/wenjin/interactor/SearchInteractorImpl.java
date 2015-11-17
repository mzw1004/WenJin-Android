package com.twt.service.wenjin.interactor;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.ui.search.OnGetSearchCallback;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Green on 15/11/12.
 */
public class SearchInteractorImpl implements SearchInteractor {
    @Override
    public void searchContent(String searchinfo, int page,final OnGetSearchCallback callback) {
        ApiClient.searchContent(searchinfo, page, 10,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callback.OnGetSearchFailure(responseString);
            }
        });
    }
}
