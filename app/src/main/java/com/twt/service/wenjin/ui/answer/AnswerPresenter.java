package com.twt.service.wenjin.ui.answer;

/**
 * Created by M on 2015/3/29.
 */
public interface AnswerPresenter {

    void loadAnswer(int answerId);

    void actionVote(int answerId, int value);

}
