package com.twt.service.wenjin.ui.home;

import com.twt.service.wenjin.bean.HomeItem;
import com.twt.service.wenjin.bean.HomeResponseMessage;
import com.twt.service.wenjin.interactor.HomeInteractor;
import com.twt.service.wenjin.interactor.HomeInteractorImpl;
import com.twt.service.wenjin.support.LogHelper;

import java.util.ArrayList;

/**
 * Created by M on 2015/3/22.
 */
public class HomePresenterImpl implements HomePresenter, OnGetItemsCallback {

    private static final String LOG_TAG = HomeInteractorImpl.class.getSimpleName();

    private HomeView mHomeView;
    private HomeInteractor mHomeInteractor;

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
        ArrayList<HomeItem> items = (ArrayList<HomeItem>) homeResponseMessage.rows;
        LogHelper.v(LOG_TAG, "size: " + items.size());
        for (int i = 0; i < items.size(); i++) {
            LogHelper.v(LOG_TAG, "username: "+ items.get(i).user_info.user_name);
           // LogHelper.v(LOG_TAG, "title: "+ items.get(i).question_info.question_content);
//            LogHelper.v(LOG_TAG, "answer: "+ items.get(i).answer_info.answer_content);
        }
        mHomeView.updateListData((ArrayList<HomeItem>) homeResponseMessage.rows);
    }

    @Override
    public void onFailure(String errorString) {
        mHomeView.stopRefresh();
        mHomeView.toastMessage(errorString);
    }
}
