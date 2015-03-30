package com.twt.service.wenjin.ui.answer;

import com.twt.service.wenjin.bean.Answer;

/**
 * Created by M on 2015/3/30.
 */
public interface OnGetAnswerCallback {

    void onSuccess(Answer answer);

    void onFailure(String errorMsg);

}
