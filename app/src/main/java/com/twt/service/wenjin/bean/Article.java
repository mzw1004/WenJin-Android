package com.twt.service.wenjin.bean;

import java.util.List;

/**
 * Created by RexSun on 15/7/17.
 */
public class Article {

    public Info article_info;
    public List<ArticleTopic> article_topics;


    public static class Info {
        public int id;
        public int uid;
        public String title;
        public String message;
        public int votes;
        public String user_name;
        public String nick_name;
        public String avatar_file;
        public String signature;
        public int vote_value;
    }

    public static class ArticleTopic {
        public int topic_id;
        public String topic_title;
    }
}
