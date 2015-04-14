package com.twt.service.wenjin.ui.explore.list;

import com.twt.service.wenjin.bean.ExploreItem;
import com.twt.service.wenjin.bean.ExploreResponse;
import com.twt.service.wenjin.interactor.ExploreInteractor;
import com.twt.service.wenjin.support.LogHelper;

import java.util.ArrayList;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExploreListPresenterImpl implements ExploreListPresenter,OnGetExploreItemsCallback {

    private final static String LOG_TAG = ExploreListPresenterImpl.class.getSimpleName();

    private ExploreListView _exploreListView;
    private ExploreInteractor _exploreInteractor;

    private  boolean isLoadMore;
    //发现模块page取0或者1都是第一页
    private int page = 1;

    public ExploreListPresenterImpl(ExploreListView exploreListView, ExploreInteractor exploreInteractor){
        this._exploreListView = exploreListView;
        this._exploreInteractor = exploreInteractor;
    }



    @Override
    public void loadExploreItems(int type) {
        this._exploreListView.startRefresh();
        page = 1;
        getExploreItems(type);
    }

    private void getExploreItems(int type){
        switch (type){
            case 0:
                this._exploreInteractor.getExploreItems(10,page,30,1,"new",this);
                LogHelper.v(LOG_TAG,"page:"+page+" recommend");
                break;
            case 1:
                this._exploreInteractor.getExploreItems(10,page,30,0,"hot",this);
                LogHelper.v(LOG_TAG,"page:"+page+" hot");
                break;
            case 2:
                this._exploreInteractor.getExploreItems(10,page,30,0,"new",this);
                LogHelper.v(LOG_TAG,"page:"+page+" new");
                break;
            case 3:
                this._exploreInteractor.getExploreItems(10,page,30,0,"unresponsive",this);
                LogHelper.v(LOG_TAG,"page:"+page+" unresponsive");
                break;
        }
    }

    @Override
    public void loadMoreExploreItems(int type) {

        page += 1;
        isLoadMore = true;
        _exploreListView.showFooter();
        getExploreItems(type);

    }

    @Override
    public void onSuccess(ExploreResponse exploreResponse) {
        this._exploreListView.stopRefresh();

        if(exploreResponse.total_rows > 0){
            ArrayList<ExploreItem> items = (ArrayList<ExploreItem>) exploreResponse.rows;
            if(isLoadMore){
                _exploreListView.addListData(items);
            }else{
                _exploreListView.updateListData(items);
                _exploreListView.showFooter();
            }
        }else{
            _exploreListView.hideFooter();
            this._exploreListView.toastMessage("Sorry,No more information");
        }
        isLoadMore = false;
    }


    @Override
    public void onFailed(String errorString) {
        page -= 1;
        this._exploreListView.stopRefresh();
        this._exploreListView.toastMessage(errorString);
        isLoadMore = false;
    }
}
