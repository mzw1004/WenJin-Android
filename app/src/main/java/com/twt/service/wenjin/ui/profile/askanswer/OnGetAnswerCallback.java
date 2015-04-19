package com.twt.service.wenjin.ui.profile.askanswer;

import com.twt.service.wenjin.bean.MyAnswerResponse;

/**
 * Created by Administrator on 2015/4/17.
 */
public interface OnGetAnswerCallback {

    void onGetAnswerSuccess(MyAnswerResponse myAnswerResponse);

    void onGetAnswerFailed(String msg);

}
