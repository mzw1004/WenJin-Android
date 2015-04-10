package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.ExploreResponseMessage;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.explore.OnGetExploreItemsCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExploreInteractorImpl implements ExploreInteractor {

    private static final String LOG_TAG = ExploreInteractorImpl.class.getSimpleName();

    @Override
    public void getExploreItems(final OnGetExploreItemsCallback onGetExploreItemsCallback) {
        ApiClient.getExplore(10,1,30,0,"new",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, response.toString());
                try{
                    switch (response.getInt(ApiClient.RESP_ERROR_CODE_KEY)){
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            ExploreResponseMessage erm =
                                    gson.fromJson(response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(),ExploreResponseMessage.class);
                            LogHelper.v(LOG_TAG, erm.toString());
                            onGetExploreItemsCallback.onSuccess(erm);
                            break;
                        case ApiClient.ERROR_CODE:
                            onGetExploreItemsCallback.onFailed(response.getString(ApiClient.RESP_ERROR_MSG_KEY));
                            break;

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
