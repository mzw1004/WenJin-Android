package com.twt.service.wenjin.ui.answer;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.interactor.AnswerInteractor;
import com.twt.service.wenjin.support.ResourceHelper;

/**
 * Created by M on 2015/4/5.
 */
public class AnswerPresenterImpl implements AnswerPresenter, OnAnswerCallback {

    private AnswerView mView;
    private AnswerInteractor mInteractor;

    public AnswerPresenterImpl(AnswerView view, AnswerInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void publishAnswer(int questionId, String content, String attachKey) {
        mInteractor.publishAnswer(questionId, content, attachKey, this);
    }

    @Override
    public void onAnswerSuccess(int answerId) {
        mView.toastMessage(ResourceHelper.getString(R.string.publish_success));
        mView.finishActivity();
    }

    @Override
    public void onAnswerFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }

}
