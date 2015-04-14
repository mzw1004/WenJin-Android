package com.twt.service.wenjin.ui.home;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.HomeItem;
import com.twt.service.wenjin.bean.HomeResponse;
import com.twt.service.wenjin.interactor.HomeInteractor;
import com.twt.service.wenjin.interactor.HomeInteractorImpl;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.publish.OnPublishCallback;

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
    private boolean isLoadingMore = false;

    public HomePresenterImpl(HomeView homeView, HomeInteractor homeInteractor) {
        this.mHomeView = homeView;
        this.mHomeInteractor = homeInteractor;
    }

    @Override
    public void refreshHomeItems() {
        mPage = 0;
        mHomeView.showRefresh();
        mHomeView.useLoadMoreFooter();
        mHomeInteractor.getHomeItems(mItemsPerPage, mPage, this);
    }

    @Override
    public void loadMoreHomeItems() {
        mPage += 1;
        isLoadingMore = true;
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
                mHomeView.startQuestionActivity(position);
                break;
            case R.id.tv_home_item_content:
                mHomeView.startAnswerActivity(position);
                break;
            case R.id.iv_home_item_agree:
                mHomeView.toastMessage("position " + position + " agreed");
                break;
        }

//        ArrayList<HomeItem> items = (ArrayList<HomeItem>) homeResponseMessage.rows;
        //LogHelper.v(LOG_TAG, "size: " + items.size());
//        for (int i = 0; i < items.size(); i++) {
//            LogHelper.v(LOG_TAG, "username: " + items.get(i).user_info.user_name);
//            LogHelper.v(LOG_TAG, "title: "+ items.get(i).question_info.question_content);
//            LogHelper.v(LOG_TAG, "answer: "+ items.get(i).answer_info.answer_content);

//        }
    }

    @Override
    public void onSuccess(HomeResponse homeResponse) {
        mHomeView.hideRefresh();

        if (homeResponse.total_rows == 0) {
            mHomeView.toastMessage(ResourceHelper.getString(R.string.no_more_infomation));
            mHomeView.hideLoadMoreFooter();
            return;
        }
        if (isLoadingMore) {
            mHomeView.loadMoreItems((ArrayList<HomeItem>) homeResponse.rows);
            isLoadingMore = false;
        } else {
            mHomeView.refreshItems((ArrayList<HomeItem>) homeResponse.rows);

        }
    }

    @Override
    public void onFailure(String errorString) {
        mHomeView.hideRefresh();
        mHomeView.toastMessage(errorString);
    }

}
