package com.twt.service.wenjin.ui.topic.detail;

import com.twt.service.wenjin.bean.Topic;

/**
 * Created by M on 2015/4/11.
 */
public interface OnGetDetailCallback {

    void onGetDetailSuccess(Topic topic);

    void onGetDetailFailure(String errorMsg);

}
