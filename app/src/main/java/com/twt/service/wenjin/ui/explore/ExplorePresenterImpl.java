package com.twt.service.wenjin.ui.explore;

import com.twt.service.wenjin.bean.ExploreItem;
import com.twt.service.wenjin.bean.ExploreResponseMessage;
import com.twt.service.wenjin.interactor.ExploreInteractor;

import java.util.ArrayList;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExplorePresenterImpl implements ExplorePresenter,OnGetExploreItemsCallback {

    private ExploreView _exploreView;
    private ExploreInteractor _exploreInteractor;

    public ExplorePresenterImpl(ExploreView exploreView,ExploreInteractor exploreInteractor){
        this._exploreView = exploreView;
        this._exploreInteractor = exploreInteractor;
    }

    @Override
    public void loadingExploreItems() {
        this._exploreView.startRefresh();
        this._exploreInteractor.getExploreItems(this);
    }

    @Override
    public void onSuccess(ExploreResponseMessage exploreResponseMessage) {
        this._exploreView.stopRefresh();
        if(0 == exploreResponseMessage.total_rows){
            this._exploreView.toastMessage("No more information");
        }
        ArrayList<ExploreItem> items = (ArrayList<ExploreItem>) exploreResponseMessage.rows;
        this._exploreView.updateListData(items);
    }

    @Override
    public void onFailed(String errorString) {
        this._exploreView.stopRefresh();
        this._exploreView.toastMessage(errorString);
    }
}
