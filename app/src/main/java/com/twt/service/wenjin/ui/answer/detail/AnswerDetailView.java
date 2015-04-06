package com.twt.service.wenjin.ui.answer.detail;

import com.twt.service.wenjin.bean.Answer;

/**
 * Created by M on 2015/3/29.
 */
public interface AnswerDetailView {

    void showProgressBar();

    void hideProgressBar();

    void bindAnswerData(Answer answer);

    void toastMessage(String msg);

    void setAgree(boolean isAgree, int agreeCount);

    void startProfileActivity();

    void startCommentActivity();

}
