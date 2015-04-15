package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.topic.detail.OnFocusCallback;
import com.twt.service.wenjin.ui.topic.detail.OnGetBestAnswerCallback;
import com.twt.service.wenjin.ui.topic.detail.OnGetDetailCallback;

/**
 * Created by M on 2015/4/11.
 */
public interface TopicDetailInteractor {

    void getTopicDetail(int topicId, int uid, OnGetDetailCallback callback);

    void getTopicBestAnswer(int topicId, OnGetBestAnswerCallback callback);

    void actionFocus(int topicId, OnFocusCallback callback);

}
