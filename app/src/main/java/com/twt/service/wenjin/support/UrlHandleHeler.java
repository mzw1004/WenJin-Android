package com.twt.service.wenjin.support;

import android.os.Bundle;

import com.twt.service.wenjin.ui.answer.AnswerActivity;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.article.ArticleActivity;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;

/**
 * Created by Green on 15/11/23.
 */
public class UrlHandleHeler {

    private static String TYPE = "type";
    private static String RELATE_ID = "id";
    private static String TYPE_ARTICLE = "article";
    private static String TYPE_QUESTION = "question";
    private static String TYPE_ANSWER = "answer";
    private static String TYPE_USER = "user";
    private static String TYPE_UNKNOW = "unknow";

    private String mType="";
    private int mId = 0;

    public Bundle UrlTypeMatcher(String url){
        Bundle bundle = new Bundle();
        if(url == "match type question"){
            int id = 0;
//            QuestionActivity.actionStart(context,int questionid);
            bundle.putString(TYPE,TYPE_QUESTION);
            bundle.putInt(RELATE_ID,id);
        }else if(url == "match type answer"){
//            AnswerDetailActivity.actionStart(context,int answerId,String question);
            int id = 0;
            bundle.putString(TYPE,TYPE_ANSWER);
            bundle.putInt(RELATE_ID, id);
        }else if(url == "match type article"){
//            ArticleActivity.actionStart(context,int articleid);
            int id = 0;
            bundle.putString(TYPE,TYPE_ARTICLE);
            bundle.putInt(RELATE_ID, id);
        }else if(url == "match type user"){
//            ProfileActivity.actionStart(context,int uid);
            int id = 0;
            bundle.putString(TYPE,TYPE_USER);
            bundle.putInt(RELATE_ID,id);
        }else {
            bundle.putString(TYPE,TYPE_UNKNOW);
        }

        return bundle;
    }



}
