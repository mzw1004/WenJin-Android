package com.twt.service.wenjin.ui.answer;

/**
 * Created by M on 2015/4/5.
 */
public interface OnAnswerCallback {

    void onAnswerSuccess(int answerId);

    void onAnswerFailure(String errorMsg);

}
