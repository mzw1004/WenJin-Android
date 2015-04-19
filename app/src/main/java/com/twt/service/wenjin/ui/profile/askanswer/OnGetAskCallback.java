package com.twt.service.wenjin.ui.profile.askanswer;

import com.twt.service.wenjin.bean.MyQustionResponse;

/**
 * Created by Administrator on 2015/4/17.
 */
public interface OnGetAskCallback {

    void onGetMyQuestionSuccess(MyQustionResponse myQustionResponse);

    void onGetMyQuestionFailed(String msg);
}
