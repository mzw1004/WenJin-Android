package com.twt.service.wenjin.ui.question;

import com.twt.service.wenjin.interactor.QuestionInteractor;

/**
 * Created by M on 2015/3/27.
 */
public class QuestionPresenterImpl implements QuestionPresenter {

    private QuestionView mQuestionView;
    private QuestionInteractor mQuestionInteractor;

    public QuestionPresenterImpl(QuestionView questionView, QuestionInteractor questionInteractor) {
        this.mQuestionView = questionView;
        this.mQuestionInteractor = questionInteractor;
    }
}
