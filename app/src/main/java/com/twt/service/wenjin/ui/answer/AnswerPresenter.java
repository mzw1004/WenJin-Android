package com.twt.service.wenjin.ui.answer;

/**
 * Created by M on 2015/4/5.
 */
public interface AnswerPresenter {

    void publishAnswer(int questionId, String content, String attachKey, boolean isAnonymous);

}
