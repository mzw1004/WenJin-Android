package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.topic.detail.OnGetDetailCallback;

/**
 * Created by M on 2015/4/11.
 */
public interface TopicDetailInteractor {

    void getTopicDetail(int topicId, int uid, OnGetDetailCallback callback);

}
