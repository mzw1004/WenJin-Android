package com.twt.service.wenjin.bean;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by M on 2015/5/15.
 */
@Table(
        name = "Questions",
        id = BaseColumns._ID
)
public class QuestionDraft extends Model {

    @Column(name = "title")
    public String title;

    @Column(name = "topics")
    public String topics;

    @Column(name = "content")
    public String content;

    @Column(name = "anonymous")
    public boolean anonymous;

    public QuestionDraft() {
        super();
    }

    public QuestionDraft(String title, String topics, String content, boolean anonymous) {
        this.title = title;
        this.topics = topics;
        this.content = content;
        this.anonymous = anonymous;
    }
}
