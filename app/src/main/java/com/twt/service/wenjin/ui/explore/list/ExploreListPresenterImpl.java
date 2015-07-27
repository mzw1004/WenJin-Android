package com.twt.service.wenjin.ui.explore.list;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.ExploreItem;
import com.twt.service.wenjin.bean.ExploreResponse;
import com.twt.service.wenjin.interactor.ExploreInteractor;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExploreListPresenterImpl implements ExploreListPresenter, OnGetExploreItemsCallback {

    private final static String LOG_TAG = ExploreListPresenterImpl.class.getSimpleName();

    private ExploreListView _exploreListView;
    private ExploreInteractor _exploreInteractor;

    private boolean isLoadMore = false;
    //发现模块page取0或者1都是第一页
    private int page = 1;
    private boolean isFirstTimeLoad = true;

    private boolean isRefreshing = false;

    public ExploreListPresenterImpl(ExploreListView exploreListView, ExploreInteractor exploreInteractor) {
        this._exploreListView = exploreListView;
        this._exploreInteractor = exploreInteractor;
    }


    @Override
    public void loadExploreItems(int type) {
        if(isRefreshing){return;}
        this._exploreListView.startRefresh();
        page = 1;
        getExploreItems(type);
    }

    @Override
    public void firstTimeLoadExploreItems(int type) {
        isRefreshing = true;
        page = 1;
        _exploreListView.showFooter();
        getExploreItems(type);
    }

    private void getExploreItems(int type) {
        switch (type) {
            case 0:
                this._exploreInteractor.getExploreItems(10, page, 7, 0, "new", this);
                LogHelper.v(LOG_TAG, "page:" + page + " new");
                break;
            case 1:
                this._exploreInteractor.getExploreItems(10, page, 7, 0, "hot", this);
                LogHelper.v(LOG_TAG, "page:" + page + " hot");
                break;
            case 2:
                this._exploreInteractor.getExploreItems(10, page, 7, 1, "new", this);
                LogHelper.v(LOG_TAG, "page:" + page + " recommend");
                break;
            case 3:
                this._exploreInteractor.getExploreItems(10, page, 7, 0, "unresponsive", this);
                LogHelper.v(LOG_TAG, "page:" + page + " unresponsive");
                break;
        }
    }

    @Override
    public void loadMoreExploreItems(int type) {
        if(isLoadMore){return;}
        page += 1;
        isLoadMore = true;
        _exploreListView.showFooter();
        getExploreItems(type);

    }

    @Override
    public void onItemClicked(View v, int position) {
        switch (v.getId()) {
            case R.id.tv_home_item_username:
                _exploreListView.startProfileActivity(position);
                break;
            case R.id.iv_home_item_avatar:
                _exploreListView.startProfileActivity(position);
                break;
            case R.id.tv_home_item_title:
                _exploreListView.startQuestionArticlActivity(position);
                break;
            case R.id.tv_home_item_content:
                _exploreListView.startQuestionArticlActivity(position);
                break;
        }
    }

    @Override
    public void onSuccess(ExploreResponse exploreResponse) {
        this._exploreListView.stopRefresh();

        if (exploreResponse.total_rows > 0) {
            List<ExploreItem> items = exploreResponse.rows;
            if (isLoadMore) {
                _exploreListView.addListData(items);
                isLoadMore = false;
            } else {
                _exploreListView.updateListData(items);
                if(isFirstTimeLoad){
                    _exploreListView.hideFooter();
                    isFirstTimeLoad = !isFirstTimeLoad;
                }
            }
        } else {
            _exploreListView.hideFooter();
            this._exploreListView.toastMessage(ResourceHelper.getString(R.string.no_more_information));
        }
        isLoadMore = false;
        isRefreshing = false;
    }


    @Override
    public void onFailed(String errorString) {
        page -= 1;
        this._exploreListView.stopRefresh();
        this._exploreListView.toastMessage(errorString);
        isLoadMore = false;
        isRefreshing = false;
    }
}
