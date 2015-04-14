package com.twt.service.wenjin.ui.publish;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.interactor.PublishInteractor;
import com.twt.service.wenjin.support.ResourceHelper;

/**
 * Created by M on 2015/4/5.
 */
public class PublishPresenterImpl implements PublishPresenter, OnPublishCallback {

    private PublishView mPublishView;
    private PublishInteractor mInteractor;

    public PublishPresenterImpl(PublishView publishView, PublishInteractor interactor) {
        this.mPublishView = publishView;
        this.mInteractor = interactor;
    }

    @Override
    public void publishQuestion(String title, String content, String attachKey, String[] topics) {
        String s = "";
        if (topics.length > 0) {
            s += topics[0];
            for (int i = 1; i < topics.length; i++) {
                s += topics[i];
            }
        }
        mInteractor.publishQuestion(title, content, attachKey, s, this);
    }

    @Override
    public void publishSuccess(int questionId) {
        mPublishView.toastMessage(ResourceHelper.getString(R.string.publish_success));
        mPublishView.finishActivity();
    }

    @Override
    public void publishFailure(String errorMsg) {
        mPublishView.toastMessage(errorMsg);
    }

}
