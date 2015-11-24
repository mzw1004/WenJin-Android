package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.api.ResponseHandler;
import com.twt.service.wenjin.bean.HomeResponse;
import com.twt.service.wenjin.support.CacheHelper;
import com.twt.service.wenjin.support.NetworkHelper;
import com.twt.service.wenjin.ui.home.OnGetItemsCallback;

import org.json.JSONObject;

/**
 * Created by M on 2015/3/24.
 */
public class HomeInteractorImpl implements HomeInteractor {

    private static final String LOG_TAG = HomeInteractorImpl.class.getSimpleName();

    @Override
    public void getHomeItems(int perPgae, final int page, final OnGetItemsCallback onGetItemsCallback) {
        if (!NetworkHelper.isOnline()) {
            onGetItemsCallback.onFailure("请检查网络连接状态");
            return;
        }
        ApiClient.getHome(perPgae, page, new ResponseHandler() {
            @Override
            public void success(JSONObject response) {
                if (page == 0) {
                    // TODO: 2015/11/23 cache response
                    CacheHelper.getInstance().cacheJSONObject("http://test", response);
                }
                HomeResponse homeResponse = new Gson()
                        .fromJson(response.toString(), HomeResponse.class);
                onGetItemsCallback.onSuccess(homeResponse);
            }

            @Override
            public void failure(String error) {
                onGetItemsCallback.onFailure(error);
            }
        });
    }

}
