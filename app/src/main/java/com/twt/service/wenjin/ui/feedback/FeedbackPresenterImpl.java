package com.twt.service.wenjin.ui.feedback;

import com.twt.service.wenjin.interactor.FeedbackInteractor;

/**
 * Created by M on 2015/4/18.
 */
public class FeedbackPresenterImpl implements FeedbackPresenter, OnPublishFeedbackCallback {

    private FeedbackView mView;
    private FeedbackInteractor mInteractor;

    public FeedbackPresenterImpl(FeedbackView view, FeedbackInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void onPublishSuccess(String msg) {
    }

    @Override
    public void onPublishFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }
}
