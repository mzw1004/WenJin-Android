package com.twt.service.wenjin.ui.topic.detail;

import com.twt.service.wenjin.bean.BestAnswer;

/**
 * Created by M on 2015/4/15.
 */
public interface OnGetBestAnswerCallback {

    void onGetAnswerSuccess(BestAnswer[] bestAnswers);

    void onGetAnswerFailure(String errorMsg);

}
