package com.twt.service.wenjin.ui.answer.detail;

import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.interactor.AnswerDetailInteractor;
import com.twt.service.wenjin.support.LogHelper;

/**
 * Created by M on 2015/3/29.
 */
public class AnswerDetailPresenterImpl implements AnswerDetailPresenter, OnGetAnswerCallback {

    private static final String LOG_TAG = AnswerDetailPresenterImpl.class.getSimpleName();

    private AnswerDetailView mAnswerDetailView;
    private AnswerDetailInteractor mAnswerDetailInteractor;

    private boolean isAgree;
    private int agreeCount;

    public AnswerDetailPresenterImpl(AnswerDetailView answerDetailView, AnswerDetailInteractor answerDetailInteractor) {
        this.mAnswerDetailView = answerDetailView;
        this.mAnswerDetailInteractor = answerDetailInteractor;
    }

    @Override
    public void loadAnswer(int answerId) {
        mAnswerDetailView.showProgressBar();
        mAnswerDetailInteractor.getAnswer(answerId, this);
        mAnswerDetailView.hideProgressBar();
    }

    @Override
    public void actionVote(int answerId, int value) {
        isAgree = !isAgree;
        if (isAgree) {
            agreeCount++;
        } else {
            agreeCount--;
        }
        ApiClient.voteAnswer(answerId, value);
        mAnswerDetailView.setAgree(isAgree, agreeCount);
    }

    @Override
    public void onSuccess(Answer answer) {
        LogHelper.v(LOG_TAG, "answer content: " + answer.answer_content);
        if (answer.vote_value == 1) {
            isAgree = true;
        } else {
            isAgree = false;
        }
        agreeCount = answer.agree_count;
        mAnswerDetailView.bindAnswerData(answer);
    }

    @Override
    public void onFailure(String errorMsg) {
        mAnswerDetailView.toastMessage(errorMsg);
    }
}
