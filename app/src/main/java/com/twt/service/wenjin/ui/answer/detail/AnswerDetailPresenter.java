package com.twt.service.wenjin.ui.answer.detail;

/**
 * Created by M on 2015/3/29.
 */
public interface AnswerDetailPresenter {

    void loadAnswer(int answerId);

    void actionVote(int answerId, int value);

    void actionDownVote(int answerId, int value);

    void actionThank(int answerId);

    void loadTitle(int argQuestionId);

    void markNoticeAsRead(int argNoticeId);

}
