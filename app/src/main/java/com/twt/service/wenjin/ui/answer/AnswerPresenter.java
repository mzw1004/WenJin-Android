package com.twt.service.wenjin.ui.answer;

/**
 * Created by M on 2015/4/5.
 */
public interface AnswerPresenter {

    void actionAnswer(int questionId, String content, boolean isAnonymous);

    void addPath(String path);

}
