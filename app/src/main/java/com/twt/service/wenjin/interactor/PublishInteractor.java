package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.publish.OnPublishCallback;

/**
 * Created by M on 2015/4/5.
 */
public interface PublishInteractor {

    void publishQuestion(String title, String content, String attachKey, String topics, OnPublishCallback callback);

}
