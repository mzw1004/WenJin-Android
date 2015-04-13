package com.twt.service.wenjin.ui.topic.detail;

import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.interactor.TopicDetailInteractor;
import com.twt.service.wenjin.support.PrefUtils;

/**
 * Created by M on 2015/4/11.
 */
public class TopicDetailPresenterImpl implements TopicDetailPresenter, OnGetDetailCallback {

    private TopicDetailView mView;
    private TopicDetailInteractor mInteractor;

    public TopicDetailPresenterImpl(TopicDetailView view, TopicDetailInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void getTopicDetail(int topicId) {
        mInteractor.getTopicDetail(topicId, PrefUtils.getPrefUid(), this);
    }

    @Override
    public void loadingItems(int topicId) {
    }

    @Override
    public void onGetDetailSuccess(Topic topic) {
        mView.bindTopicDetail(topic);
    }

    @Override
    public void onGetDetailFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }
}
