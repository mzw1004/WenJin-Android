package com.twt.service.wenjin.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by M on 2015/5/17.
 */
@Table(
        name = "Answers",
        id = BaseColumns._ID
)
public class AnswerDraft extends Model implements Parcelable {

    @Column(name = "question_id")
    public int question_id;

    @Column(name = "question_title")
    public String question_title;

    @Column(name = "content")
    public String content;

    @Column(name = "anonymous")
    public boolean anonymous;

    public AnswerDraft() {
        super();
    }

    public AnswerDraft(int question_id, String question_title, String content, boolean anonymous) {
        this.question_id = question_id;
        this.question_title = question_title;
        this.content = content;
        this.anonymous = anonymous;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(question_id);
        dest.writeString(question_title);
        dest.writeString(content);
    }

    public static final Creator<AnswerDraft> CREATOR = new Creator<AnswerDraft>() {
        @Override
        public AnswerDraft createFromParcel(Parcel source) {
            AnswerDraft draft = new AnswerDraft();
            draft.question_id = source.readInt();
            draft.question_title = source.readString();
            draft.content = source.readString();
            return draft;
        }

        @Override
        public AnswerDraft[] newArray(int size) {
            return new AnswerDraft[size];
        }
    };
}
