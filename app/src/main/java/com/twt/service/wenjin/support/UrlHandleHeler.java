package com.twt.service.wenjin.support;

import android.content.Context;
import android.os.Bundle;

import com.twt.service.wenjin.ui.answer.AnswerActivity;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.article.ArticleActivity;
import com.twt.service.wenjin.ui.innerweb.InnerWebActivity;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Green on 15/11/23.
 */
public class UrlHandleHeler {

    private final static String TYPE = "type";
    private final static String RELATE_ID = "id";
    private final static String TYPE_ARTICLE = "article";
    private final static String TYPE_QUESTION = "question";
    private final static String TYPE_ANSWER = "answer";
    private final static String TYPE_USER = "user";
    private final static String TYPE_UNKNOW = "unknow";

    private final String regQuestion = "^(((http|https):\\/\\/)?([\\w\\-\\_]+\\.)*)?wenjin\\.in\\/question\\/\\w+$";
    private final String regQuestionRep = "^(((http|https):\\/\\/)?([\\w\\-\\_]+\\.)*)?wenjin\\.in\\/question\\/";
    private final String regArticle = "^(((http|https):\\/\\/)?([\\w\\-\\_]+\\.)*)?wenjin\\.in\\/column\\/#\\/\\w+$";
    private final String regArticleRep = "^(((http|https):\\/\\/)?([\\w\\-\\_]+\\.)*)?wenjin\\.in\\/column\\/#\\/";
    private final String regUser = "^(((http|https):\\/\\/)?([\\w\\-\\_]+\\.)*)?wenjin\\.in\\/u\\/\\w+$";

    private Context mContext;
    private String mUrl = "";
    private String mType="";
    private int mId=0;

    public UrlHandleHeler(Context context, String url){
        mContext = context;
        mUrl = url;
    }

    private void SetUrlTypeMatch(){
        if(Pattern.compile(regQuestion).matcher(mUrl).matches()){
            String strId = mUrl.replaceAll(regQuestionRep,"");
            try{
                mId = Integer.parseInt(strId);
                mType = TYPE_QUESTION;
            }catch (Exception e){
                mType = TYPE_UNKNOW;
            }
        }else if(Pattern.compile(regArticle).matcher(mUrl).matches()){
            String strId = mUrl.replaceAll(regArticleRep,"");
            try{
                mId = Integer.parseInt(strId);
                mType = TYPE_ARTICLE;
            }catch (Exception e){
                mType = TYPE_UNKNOW;
            }
//        }else if(Pattern.compile(regUser).matcher(url).matches()){
//            int id = 0;
//            bundle.putString(TYPE,TYPE_USER);
//            bundle.putInt(RELATE_ID,id);
        }else {
            mType = TYPE_UNKNOW;
        }
    }

    private boolean UrlTypeHandler(){
        if(mType.equals("")){
            return false;
        }else{
            switch (mType){
                case TYPE_UNKNOW:
                    InnerWebActivity.actionStart(mContext,mUrl);
                    break;
                case TYPE_QUESTION:
                    QuestionActivity.actionStart(mContext,mId);
                    break;
                case TYPE_ARTICLE:
                    ArticleActivity.actionStart(mContext,mId);
                    break;

            }
            return true;
        }
    }

    public boolean hand(){
        SetUrlTypeMatch();
        return UrlTypeHandler();
    }



}
