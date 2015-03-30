package com.twt.service.wenjin.ui.answer;

import com.twt.service.wenjin.bean.Answer;

/**
 * Created by M on 2015/3/29.
 */
public interface AnswerView {

    void showProgressBar();

    void hideProgressBar();

    void bindAnswerData(Answer answer);

    void toastMessage(String msg);

}
