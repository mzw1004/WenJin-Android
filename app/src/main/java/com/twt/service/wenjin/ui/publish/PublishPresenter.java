package com.twt.service.wenjin.ui.publish;

/**
 * Created by M on 2015/4/5.
 */
public interface PublishPresenter {

    void actionPublish(String title, String content, String[] topics, boolean isAnonymous);

    void addPath(String path);

}
