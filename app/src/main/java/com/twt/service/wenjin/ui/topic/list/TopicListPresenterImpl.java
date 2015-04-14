package com.twt.service.wenjin.ui.topic.list;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.interactor.TopicListInteractor;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;

/**
 * Created by M on 2015/4/8.
 */
public class TopicListPresenterImpl implements TopicListPresenter, OnGetTopicsCallback {

    private final static String LOG_TAG = TopicListPresenterImpl.class.getSimpleName();

    private TopicListView mView;
    private TopicListInteractor mInteractor;

    private int page = 0;
    private boolean isLoadMore = false;

    public TopicListPresenterImpl(TopicListView view, TopicListInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void loadMoreTopics(int type) {
        mView.startRefresh();
        page += 1;
        isLoadMore = true;
        switch (type) {
            case 0:
                mInteractor.getTopics("hot", page, this);
                LogHelper.v(LOG_TAG,"hot page:"+page);
                break;
            case 1:
                mInteractor.getTopics("focus", page, this);
                LogHelper.v(LOG_TAG,"focus page:"+page);
                break;
        }
    }

    @Override
    public void refreshTopics(int type) {
        mView.startRefresh();
        page = 0;
        switch (type) {
            case 0:
                mInteractor.getTopics("hot", page, this);
                LogHelper.v(LOG_TAG,"hot page:"+page);
                break;
            case 1:
                mInteractor.getTopics("focus", page, this);
                LogHelper.v(LOG_TAG,"focus page:"+page);
                break;
        }
    }

    @Override
    public void onGetTopicsSuccess(Topic[] topics) {
        mView.stopRefresh();
        if (topics.length > 0) {
            if (isLoadMore) {
                mView.addTopics(topics);
            } else {
                mView.updateTopics(topics);
                mView.showFooter();
            }
        } else {
            mView.hideFooter();
            mView.toastMessage(ResourceHelper.getString(R.string.no_more_infomation));
        }
        isLoadMore = false;
    }

    @Override
    public void onGetTopicsFailure(String errorMsg) {
        isLoadMore = false;
        mView.toastMessage(errorMsg);
    }
}
