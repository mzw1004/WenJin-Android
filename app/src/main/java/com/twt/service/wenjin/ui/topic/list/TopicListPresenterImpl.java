package com.twt.service.wenjin.ui.topic.list;

import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.interactor.TopicListInteractor;

/**
 * Created by M on 2015/4/8.
 */
public class TopicListPresenterImpl implements TopicListPresenter, OnGetTopicsCallback {

    private TopicListView mView;
    private TopicListInteractor mInteractor;

    public TopicListPresenterImpl(TopicListView view, TopicListInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void loadTopics(int position) {
        mView.startRefresh();
        switch (position) {
            case 0:
                mInteractor.getTopics("hot", 0, this);
                break;
            case 1:
                mInteractor.getTopics("focus", 0, this);
                break;
        }
    }

    @Override
    public void onGetTopicsSuccess(Topic[] topics) {
        mView.stopRefresh();
        mView.updateTopics(topics);
    }

    @Override
    public void onGetTopicsFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }
}
