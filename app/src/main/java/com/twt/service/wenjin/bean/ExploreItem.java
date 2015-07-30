package com.twt.service.wenjin.bean;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExploreItem {

    /*当类型为article时,id为文章id*/
    public int id;

    /*提问者信息*/
    public UserInfo user_info;

    //public ArrayList<UserInfo> answer_users;

    //是否匿名
    public int anonymous;

    /*回答信息*/
    public AnswerInfo[] answer_users;
    public String post_type;

    /*the title of article if post type is article*/
    public String title;

    /*浏览次数*/
    public int view_count;

    /*关注人数*/
    public int focus_count;

    /*回复人数*/
    public int answer_count;

    /*问题ID*/
    public int question_id;

    /*问题标题*/
    public String question_content;

    public int add_time;

    public int update_time;

    public int published_uid;
}
