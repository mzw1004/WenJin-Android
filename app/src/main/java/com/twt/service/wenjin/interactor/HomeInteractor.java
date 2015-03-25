package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.home.OnGetItemsCallback;

/**
 * Created by M on 2015/3/24.
 */
public interface HomeInteractor {

    void getHomeItems(int perPage, int page, OnGetItemsCallback onGetItemsCallback);
}
