package com.twt.service.wenjin.ui.topic.detail;

import com.twt.service.wenjin.bean.BestAnswer;
import com.twt.service.wenjin.bean.Topic;

/**
 * Created by M on 2015/4/11.
 */
public interface TopicDetailView {

    void bindTopicDetail(Topic topic);

    void bindTopicBestAnswer(BestAnswer[] bestAnswers);

    void addFocus();

    void removeFocus();

    void toastMessage(String msg);

    void startQuestionActivity(int position);

    void startProfileActivity(int position);

}
