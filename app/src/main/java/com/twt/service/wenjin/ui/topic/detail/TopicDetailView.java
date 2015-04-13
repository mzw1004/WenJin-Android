package com.twt.service.wenjin.ui.topic.detail;

import com.twt.service.wenjin.bean.Topic;

/**
 * Created by M on 2015/4/11.
 */
public interface TopicDetailView {

    void bindTopicDetail(Topic topic);

    void toastMessage(String msg);

}
