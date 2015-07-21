package com.twt.service.wenjin.ui.question;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.interactor.QuestionInteractor;

/**
 * Created by M on 2015/3/27.
 */
public class QuestionPresenterImpl implements QuestionPresenter, OnGetQuestionCallback, OnFocusedCallback {

    private QuestionView mQuestionView;
    private QuestionInteractor mQuestionInteractor;

    public QuestionPresenterImpl(QuestionView questionView, QuestionInteractor questionInteractor) {
        this.mQuestionView = questionView;
        this.mQuestionInteractor = questionInteractor;
    }

    @Override
    public void loadingContent(int questionId) {
        mQuestionView.showProgressBar();
        mQuestionInteractor.getQuestionContent(questionId, this);
    }

    @Override
    public void actionFocus(int questionId) {
        mQuestionInteractor.actionFocus(questionId, this);
    }

    @Override
    public void onGetQuestionSuccess(QuestionResponse questionResponse) {
        mQuestionView.hideProgressBar();
        mQuestionView.setAdapter(questionResponse);
    }

    @Override
    public void onGetQuestionFailure(String errorString) {
        mQuestionView.toastMessage(errorString);
    }

    @Override
    public void onFocusSuccess(boolean isFocus) {
        mQuestionView.setFocus(isFocus);
    }

    @Override
    public void onFocusFailure(String errorMsg) {
        mQuestionView.toastMessage(errorMsg);
    }

}
