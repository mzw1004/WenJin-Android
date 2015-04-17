package com.twt.service.wenjin.ui.topic.list;

import com.twt.service.wenjin.bean.Topic;

/**
 * Created by M on 2015/4/8.
 */
public interface TopicListView {

    void updateTopics(Topic[] topics);

    void addTopics(Topic[] topics);

    void startRefresh();

    void stopRefresh();

    void showFooter();

    void hideFooter();

    void toastMessage(String msg);

    void startTopicDetailActivity(int position);

}
