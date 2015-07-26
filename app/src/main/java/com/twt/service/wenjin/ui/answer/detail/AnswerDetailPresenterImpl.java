package com.twt.service.wenjin.ui.answer.detail;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.interactor.AnswerDetailInteractor;
import com.twt.service.wenjin.interactor.NotificationInteractor;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.question.OnGetQuestionCallback;

/**
 * Created by M on 2015/3/29.
 */
public class AnswerDetailPresenterImpl implements AnswerDetailPresenter, OnGetAnswerCallback, OnGetQuestionCallback {

    private static final String LOG_TAG = AnswerDetailPresenterImpl.class.getSimpleName();


    private static final int VOTE_STATE_UPVOTE = 1;
    private static final int VOTE_STATE_DOWNVOTE = -1;
    private static final int VOTE_STATE_NONE = 0;

    private AnswerDetailView mAnswerDetailView;
    private AnswerDetailInteractor mAnswerDetailInteractor;
    private NotificationInteractor mNotificationInteractor;

    private boolean isAgree;
    private boolean isDisagree;
    private boolean isThank;
    private int agreeCount;

    private int voteState = 0 ;

    public AnswerDetailPresenterImpl(AnswerDetailView answerDetailView,
                                     AnswerDetailInteractor answerDetailInteractor,
                                     NotificationInteractor notificationInteractor) {
        this.mAnswerDetailView = answerDetailView;
        this.mAnswerDetailInteractor = answerDetailInteractor;
        this.mNotificationInteractor = notificationInteractor;
    }

    @Override
    public void loadAnswer(int answerId) {
        mAnswerDetailView.showProgressBar();
        mAnswerDetailInteractor.getAnswer(answerId, this);
        mAnswerDetailView.hideProgressBar();
    }

    @Override
    public void actionVote(int answerId, int value) {
        if(voteState == VOTE_STATE_NONE || voteState == VOTE_STATE_DOWNVOTE){
            voteState = VOTE_STATE_UPVOTE;
            agreeCount++;
            mAnswerDetailView.toastMessage(ResourceHelper.getString(R.string.action_upvote_msg));
        }else {
            voteState = VOTE_STATE_NONE;
            agreeCount--;
            mAnswerDetailView.toastMessage(ResourceHelper.getString(R.string.action_cancel_upvote_msg));
        }
        ApiClient.voteAnswer(answerId, value);
        mAnswerDetailView.setAgree(voteState, agreeCount);
    }

    @Override
    public void actionDownVote(int answerId, int value) {
        if(voteState == VOTE_STATE_UPVOTE){
            agreeCount--;
            mAnswerDetailView.setAgreeCount(agreeCount);
            voteState = VOTE_STATE_DOWNVOTE;
            mAnswerDetailView.toastMessage(ResourceHelper.getString(R.string.action_downvote_msg));
        }else if(voteState == VOTE_STATE_NONE){
            voteState = VOTE_STATE_DOWNVOTE;
            mAnswerDetailView.toastMessage(ResourceHelper.getString(R.string.action_downvote_msg));
        }else {
            voteState = VOTE_STATE_NONE;
            mAnswerDetailView.toastMessage(ResourceHelper.getString(R.string.action_cancel_downvote_msg));
        }
        ApiClient.voteAnswer(answerId, value);
        mAnswerDetailView.setDisAgree(voteState);
    }

    @Override
    public void actionThank(int answerId) {
        if(!isThank){
            isThank = true;
            mAnswerDetailView.toastMessage(ResourceHelper.getString(R.string.action_thank_msg));
            ApiClient.thankAnswer(answerId,"thanks",null);
            mAnswerDetailView.setThank(isThank);
        }else {
            mAnswerDetailView.toastMessage(ResourceHelper.getString(R.string.action_thanked_msg));
        }

    }

    @Override
    public void loadTitle(int argQuestionId) {
        mAnswerDetailInteractor.getQuestionContent(argQuestionId, this);
    }

    @Override
    public void markNoticeAsRead(int argNoticeId) {
        mNotificationInteractor.setNotificationMarkasread(argNoticeId);
    }

    @Override
    public void onSuccess(Answer answer) {
        LogHelper.v(LogHelper.makeLogTag(AnswerDetailPresenterImpl.class), "answer content: " + answer.answer_content);
        voteState = answer.vote_value;
        agreeCount = answer.agree_count;
        if(answer.thank_value == 1){
            isThank = true;
        }
        mAnswerDetailView.bindAnswerData(answer);
    }

    @Override
    public void onGetQuestionSuccess(QuestionResponse questionResponse) {
        mAnswerDetailView.bindTitle(questionResponse.question_info.question_content);
    }

    @Override
    public void onGetQuestionFailure(String errorString) {
        mAnswerDetailView.toastMessage(errorString);
    }

    @Override
    public void onFailure(String errorMsg) {
        mAnswerDetailView.toastMessage(errorMsg);
    }


}
