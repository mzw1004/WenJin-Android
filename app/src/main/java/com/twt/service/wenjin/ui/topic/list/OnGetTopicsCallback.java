package com.twt.service.wenjin.ui.topic.list;

import com.twt.service.wenjin.bean.Topic;

/**
 * Created by M on 2015/4/8.
 */
public interface OnGetTopicsCallback {

    void onGetTopicsSuccess(Topic[] topics);

    void onGetTopicsFailure(String errorMsg);

}
