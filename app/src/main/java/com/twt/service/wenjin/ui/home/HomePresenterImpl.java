package com.twt.service.wenjin.ui.home;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.HomeItem;
import com.twt.service.wenjin.bean.HomeResponse;
import com.twt.service.wenjin.interactor.HomeInteractor;
import com.twt.service.wenjin.interactor.HomeInteractorImpl;
import com.twt.service.wenjin.support.ResourceHelper;

import java.util.ArrayList;

/**
 * Created by M on 2015/3/22.
 */
public class HomePresenterImpl implements HomePresenter, OnGetItemsCallback {

    private static final String LOG_TAG = HomeInteractorImpl.class.getSimpleName();

    private HomeView mHomeView;
    private HomeInteractor mHomeInteractor;

    private int mItemsPerPage = 20;
    private int mPage = 0;
    private boolean isLoadingMore = false;  //is loading more?
    private boolean isFirstTimeLoad = true; //is fitsrt time to load data for recylerView
    private boolean isRefreshing = false;  //is refreshing to reset the recylerView data?

    public HomePresenterImpl(HomeView homeView, HomeInteractor homeInteractor) {
        this.mHomeView = homeView;
        this.mHomeInteractor = homeInteractor;
    }

    @Override
    public void refreshHomeItems() {
        if(isRefreshing) return;
        mPage = 0;
        isRefreshing = true;
        mHomeView.showRefresh();
        mHomeInteractor.getHomeItems(mItemsPerPage, mPage, this);
    }

    @Override
    public void firstTimeRefreshHomeItems() {
        mPage = 0;
        isRefreshing = true;
        mHomeView.useLoadMoreFooter();
        mHomeInteractor.getHomeItems(mItemsPerPage, mPage, this);
    }

    @Override
    public void loadMoreHomeItems() {
        if(isLoadingMore){ return; }  //load more action only allowed existed one

        mPage += 1;
        isLoadingMore = true;
        mHomeView.useLoadMoreFooter();
        mHomeInteractor.getHomeItems(mItemsPerPage, mPage, this);
    }

    @Override
    public void onItemClicked(View v, int position) {
        switch (v.getId()) {
            case R.id.iv_home_item_avatar:
                mHomeView.startProfileActivity(position);
                break;
            case R.id.tv_home_item_username:
                mHomeView.startProfileActivity(position);
                break;
            case R.id.tv_home_item_title:
                mHomeView.startQuestionArticlActivity(position);
                break;
            case R.id.tv_home_item_content:
                mHomeView.startAnswerActivity(position);
                break;
            case R.id.iv_home_item_agree:
                mHomeView.toastMessage("position " + position + " agreed");
                break;
        }
    }

    @Override
    public void onSuccess(HomeResponse homeResponse) {

        if (homeResponse.total_rows == 0) {
            mHomeView.toastMessage(ResourceHelper.getString(R.string.no_more_information));
            mHomeView.hideLoadMoreFooter();
            isLoadingMore = false;
            isRefreshing = false;
            return;
        }
        if (isLoadingMore) {
            mHomeView.loadMoreItems((ArrayList<HomeItem>) homeResponse.rows);
            mHomeView.hideLoadMoreFooter();
            isLoadingMore = false;
        } else {
            mHomeView.hideRefresh();
            mHomeView.refreshItems((ArrayList<HomeItem>) homeResponse.rows);
            if(isFirstTimeLoad){
                mHomeView.hideLoadMoreFooter();
                isFirstTimeLoad = !isFirstTimeLoad;
            }
            isRefreshing = false;
        }

    }

    @Override
    public void onFailure(String errorString) {
        isLoadingMore = false;
        isRefreshing = false;
        mHomeView.hideRefresh();
        if (errorString != null) {
            mHomeView.toastMessage(errorString);
        }
    }

}
