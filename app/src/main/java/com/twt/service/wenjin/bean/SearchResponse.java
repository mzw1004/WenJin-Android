package com.twt.service.wenjin.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Green on 15/11/18.
 */
public class SearchResponse {

    public int total_rows;

    public ArrayList<SearchDetailQuestion> questions;

    public ArrayList<SearchDetailArticle> articles;

    public ArrayList<SearchDetailTopic> topics;

    public ArrayList<SearchDetailUser> users;

    public SearchResponse(){
        questions = new ArrayList<>();
        articles = new ArrayList<>();
        topics = new ArrayList<>();
        users = new ArrayList<>();
    }
}
