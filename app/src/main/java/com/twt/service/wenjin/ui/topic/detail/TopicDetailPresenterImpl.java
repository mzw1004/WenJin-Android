package com.twt.service.wenjin.ui.topic.detail;

import com.twt.service.wenjin.bean.BestAnswer;
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.interactor.TopicDetailInteractor;
import com.twt.service.wenjin.support.PrefUtils;

/**
 * Created by M on 2015/4/11.
 */
public class TopicDetailPresenterImpl implements
        TopicDetailPresenter, OnGetDetailCallback, OnGetBestAnswerCallback, OnFocusCallback {

    private TopicDetailView mView;
    private TopicDetailInteractor mInteractor;

    private int topicId;

    public TopicDetailPresenterImpl(TopicDetailView view, TopicDetailInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void getTopicDetail(int topicId) {
        this.topicId = topicId;
        mInteractor.getTopicDetail(topicId, PrefUtils.getPrefUid(), this);
    }

    @Override
    public void loadingItems(int topicId) {
        mInteractor.getTopicBestAnswer(topicId, this);
    }

    @Override
    public void actionFocus() {
        mInteractor.actionFocus(topicId, this);
    }

    @Override
    public void onGetDetailSuccess(Topic topic) {
        mView.bindTopicDetail(topic);
        loadingItems(topicId);
    }

    @Override
    public void onGetDetailFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }

    @Override
    public void onGetAnswerSuccess(BestAnswer[] bestAnswers) {
        mView.bindTopicBestAnswer(bestAnswers);
    }

    @Override
    public void onGetAnswerFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }

    @Override
    public void onFocusSuccess(boolean isfocused) {
        if (isfocused) {
            mView.addFocus();
        } else {
            mView.removeFocus();
        }
    }

    @Override
    public void onFocusFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }
}
