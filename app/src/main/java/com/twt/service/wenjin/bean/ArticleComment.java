package com.twt.service.wenjin.bean;

/**
 * Created by RexSun on 15/7/19.
 */
public class ArticleComment {

    public int id;
    public int uid;
    public String message;
    public long add_time;
    public int at_uid;
    public int votes;
    public UserInfo user_info;
    public Object at_user_info;
    public int vote_value;

    public static class UserInfo {
        public int uid;
        public String user_name;
        public String nick_name;
        public String avatar_file;
    }
}
