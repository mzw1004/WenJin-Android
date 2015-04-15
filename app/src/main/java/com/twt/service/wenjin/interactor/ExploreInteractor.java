package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.explore.list.OnGetExploreItemsCallback;

/**
 * Created by WGL on 2015/3/28.
 */
public interface ExploreInteractor {
    void getExploreItems(int per_page,int page,int day,int isRecommend,String sortType,OnGetExploreItemsCallback onGetExploreItemsCallback);
}
