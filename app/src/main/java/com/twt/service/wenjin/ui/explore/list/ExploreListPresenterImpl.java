package com.twt.service.wenjin.ui.explore.list;

import com.twt.service.wenjin.bean.ExploreItem;
import com.twt.service.wenjin.bean.ExploreResponseMessage;
import com.twt.service.wenjin.interactor.ExploreInteractor;

import java.util.ArrayList;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExploreListPresenterImpl implements ExploreListPresenter,OnGetExploreItemsCallback {

    private ExploreListView _exploreListView;
    private ExploreInteractor _exploreInteractor;

    public ExploreListPresenterImpl(ExploreListView exploreListView, ExploreInteractor exploreInteractor){
        this._exploreListView = exploreListView;
        this._exploreInteractor = exploreInteractor;
    }

    @Override
    public void loadingExploreItems(int type) {
        this._exploreListView.startRefresh();
        switch (type){
            case 0:
                this._exploreInteractor.getExploreItems(10,1,30,1,"new",this);
                break;
            case 1:
                this._exploreInteractor.getExploreItems(10,1,30,0,"hot",this);
                break;
            case 2:
                this._exploreInteractor.getExploreItems(10,1,30,0,"new",this);
                break;
            case 3:
                this._exploreInteractor.getExploreItems(10,1,30,0,"unresponsive",this);
                break;
        }
    }

    @Override
    public void onSuccess(ExploreResponseMessage exploreResponseMessage) {
        this._exploreListView.stopRefresh();
        if(0 == exploreResponseMessage.total_rows){
            this._exploreListView.toastMessage("No more information");
        }
        ArrayList<ExploreItem> items = (ArrayList<ExploreItem>) exploreResponseMessage.rows;
        this._exploreListView.updateListData(items);
    }

    @Override
    public void onFailed(String errorString) {
        this._exploreListView.stopRefresh();
        this._exploreListView.toastMessage(errorString);
    }
}
