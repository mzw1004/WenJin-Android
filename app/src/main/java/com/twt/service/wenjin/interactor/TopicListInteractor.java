package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.topic.list.OnGetTopicsCallback;

/**
 * Created by M on 2015/4/8.
 */
public interface TopicListInteractor {

    void getTopics(String type, int page, OnGetTopicsCallback callback);

}
