package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.question.OnFocusedCallback;
import com.twt.service.wenjin.ui.question.OnGetQuestionCallback;

/**
 * Created by M on 2015/3/27.
 */
public interface QuestionInteractor {

    void getQuestionContent(int questionId, OnGetQuestionCallback onGetQuestionCallback);

    void actionFocus(int questionId, OnFocusedCallback onFocusedCallback);
}
