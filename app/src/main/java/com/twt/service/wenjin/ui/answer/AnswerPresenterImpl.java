package com.twt.service.wenjin.ui.answer;

import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.interactor.AnswerInteractor;
import com.twt.service.wenjin.support.LogHelper;

/**
 * Created by M on 2015/3/29.
 */
public class AnswerPresenterImpl implements AnswerPresenter, OnGetAnswerCallback {

    private static final String LOG_TAG = AnswerPresenterImpl.class.getSimpleName();

    private AnswerView mAnswerView;
    private AnswerInteractor mAnswerInteractor;

    public AnswerPresenterImpl(AnswerView answerView, AnswerInteractor answerInteractor) {
        this.mAnswerView = answerView;
        this.mAnswerInteractor = answerInteractor;
    }

    @Override
    public void loadAnswer(int answerId) {
        mAnswerView.showProgressBar();
        mAnswerInteractor.getAnswer(answerId, this);
        mAnswerView.hideProgressBar();
    }

    @Override
    public void onSuccess(Answer answer) {
        LogHelper.v(LOG_TAG, "answer content: " + answer.answer_content);
        mAnswerView.bindAnswerData(answer);
    }

    @Override
    public void onFailure(String errorMsg) {
        mAnswerView.toastMessage(errorMsg);
    }
}
