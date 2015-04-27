package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.publish.OnPublishCallback;
import com.twt.service.wenjin.ui.publish.OnUploadCallback;

import java.io.File;

/**
 * Created by M on 2015/4/5.
 */
public interface PublishInteractor {

    void publishQuestion(String title, String content, String attachKey, String topics, boolean isAnoymous, OnPublishCallback callback);

    void uploadFile(File file, String attachKey, OnUploadCallback callback);

}
