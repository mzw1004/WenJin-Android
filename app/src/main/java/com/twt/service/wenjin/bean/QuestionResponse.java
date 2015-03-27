package com.twt.service.wenjin.bean;

import java.util.List;

/**
 * Created by M on 2015/3/27.
 */
public class QuestionResponse {

    public QuestionInfo question_info;

    public int answer_count;

    public List<Answer> answers;

    public List<QuestionTopic> question_topics;
}
