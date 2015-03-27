package com.twt.service.wenjin.ui.question;

import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.interactor.QuestionInteractor;

/**
 * Created by M on 2015/3/27.
 */
public class QuestionPresenterImpl implements QuestionPresenter, OnGetQuestionCallback {

    private QuestionView mQuestionView;
    private QuestionInteractor mQuestionInteractor;

    public QuestionPresenterImpl(QuestionView questionView, QuestionInteractor questionInteractor) {
        this.mQuestionView = questionView;
        this.mQuestionInteractor = questionInteractor;
    }

    @Override
    public void loadingContent(int questionId) {
        mQuestionInteractor.getQuestionContent(questionId, this);
    }

    @Override
    public void onSuccess(QuestionResponse questionResponse) {
        mQuestionView.setAdapter(questionResponse);
    }

    @Override
    public void onFailure(String errorString) {

    }
}
