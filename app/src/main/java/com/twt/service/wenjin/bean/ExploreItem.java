package com.twt.service.wenjin.bean;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExploreItem {

    /*提问者信息*/
    public UserInfo user_info;

    public Map<String,Object> answer_users;

    /*回答信息*/
    public AnswerInfo answer_info;

    /*浏览次数*/
    public int  view_count;

    /*关注人数*/
    public int focus_count;

    /*回复人数*/
    public  int answer_count;

    /*问题ID*/
    public int question_id;

    /*问题标题*/
    public String question_content;

    public int add_time;

    public int update_time;

    public int published_uid;
}
