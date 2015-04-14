package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.answer.detail.OnGetAnswerCallback;

/**
 * Created by M on 2015/3/29.
 */
public interface AnswerDetailInteractor {

    void getAnswer(int answerId, OnGetAnswerCallback callback);
}
