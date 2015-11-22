package com.twt.service.wenjin.bean;

/**
 * Created by Green on 15/11/19.
 */
public class SearchQuestion {
    public int uid;  //无用

    public String score;  //无用

    /*返回类型：topics/questions/users/article*/
    public String type;

    public String url;  //无用


    public int search_id;

    /*搜索结果的名字（标题）*/
    public String name;

    public SearchDetailQuestion detail;
}
