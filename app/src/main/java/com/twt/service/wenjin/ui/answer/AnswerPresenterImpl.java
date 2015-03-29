package com.twt.service.wenjin.ui.answer;

import com.twt.service.wenjin.interactor.AnswerInteractor;

/**
 * Created by M on 2015/3/29.
 */
public class AnswerPresenterImpl implements AnswerPresenter {

    private AnswerView mAnswerView;
    private AnswerInteractor mAnswerInteractor;

    public AnswerPresenterImpl(AnswerView answerView, AnswerInteractor answerInteractor) {
        this.mAnswerView = answerView;
        this.mAnswerInteractor = answerInteractor;
    }

    @Override
    public void loadAnswer(int answerId) {
        mAnswerView.showProgressBar();
    }
}
