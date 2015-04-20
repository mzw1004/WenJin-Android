package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.feedback.OnPublishFeedbackCallback;

/**
 * Created by M on 2015/4/18.
 */
public interface FeedbackInteractor {

    void publishFeedback(String title, String message, OnPublishFeedbackCallback callback);

}
