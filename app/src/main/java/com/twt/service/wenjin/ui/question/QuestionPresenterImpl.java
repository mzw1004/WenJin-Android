package com.twt.service.wenjin.ui.question;

import android.view.View;

import com.twt.service.wenjin.R;
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
        mQuestionView.showProgressBar();
        mQuestionInteractor.getQuestionContent(questionId, this);
    }

    @Override
    public void itemClicked(View view, int position) {

        switch (view.getId()) {
            case R.id.tag_group_question:
                mQuestionView.toastMessage("tag clicked");
                break;
            case R.id.bt_question_focus:
                mQuestionView.toastMessage("button clicked");
                break;
            case R.id.iv_question_answer_avatar:
                mQuestionView.toastMessage("user avatar clicked " + position);
                break;
            case R.id.tv_question_answer_username:
                mQuestionView.toastMessage("username clicked " + position);
                break;
            case R.id.tv_question_answer_content:
                mQuestionView.toastMessage("answer content clicked " + position);
                mQuestionView.startAnswerActivty(position);
                break;
        }
    }

    @Override
    public void onSuccess(QuestionResponse questionResponse) {
        mQuestionView.hideProgressBar();
        mQuestionView.setAdapter(questionResponse);
    }

    @Override
    public void onFailure(String errorString) {

    }
}
