package com.twt.service.wenjin.ui.feedback;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.interactor.FeedbackInteractor;
import com.twt.service.wenjin.support.ResourceHelper;

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
    public void publish(String title, String message) {
        mInteractor.publishFeedback(title, message, this);
    }

    @Override
    public void onPublishSuccess() {
        mView.toastMessage(ResourceHelper.getString(R.string.publish_success));
        mView.finishActivity();
    }

    @Override
    public void onPublishFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }
}
