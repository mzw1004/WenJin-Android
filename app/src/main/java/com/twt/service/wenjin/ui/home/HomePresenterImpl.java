package com.twt.service.wenjin.ui.home;

import com.twt.service.wenjin.bean.HomeItem;
import com.twt.service.wenjin.bean.HomeResponseMessage;
import com.twt.service.wenjin.interactor.HomeInteractor;

import java.util.ArrayList;

/**
 * Created by M on 2015/3/22.
 */
public class HomePresenterImpl implements HomePresenter, OnGetItemsCallback {

    private HomeView mHomeView;
    private HomeInteractor mHomeInteractor;

    private ArrayList<HomeItem> mDataset;

    public HomePresenterImpl(HomeView homeView, HomeInteractor homeInteractor) {
        this.mHomeView = homeView;
        this.mHomeInteractor = homeInteractor;
    }

    @Override
    public void loadingHomeItems() {
        mHomeView.startRefresh();
        mHomeInteractor.getHomeItems(this);
    }

    @Override
    public void onSuccess(HomeResponseMessage homeResponseMessage) {
        mHomeView.stopRefresh();
        if (homeResponseMessage.total_rows == 0) {
            mHomeView.toastMessage("No more information");
        }
        mDataset = (ArrayList<HomeItem>) homeResponseMessage.rows;
        mHomeView.updateListData(mDataset);
    }

    @Override
    public void onFailure(String errorString) {
        mHomeView.stopRefresh();
        mHomeView.toastMessage(errorString);
    }
}
