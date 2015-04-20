package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.answer.OnAnswerCallback;

/**
 * Created by M on 2015/4/5.
 */
public interface AnswerInteractor {

    void publishAnswer(int questionId, String Content, String attachKey, boolean isAnonymous, OnAnswerCallback callback);

}
