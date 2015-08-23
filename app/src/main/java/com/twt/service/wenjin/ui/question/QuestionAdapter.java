package com.twt.service.wenjin.ui.question;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.bean.QuestionInfo;
import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.common.PicassoImageGetter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by M on 2015/3/27.
 */
public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_QUESTION = 0;
    private static final int ITEM_VIEW_TYPE_ANSWER = 1;

    private Context mContext;
    private QuestionResponse mQuestionResponse;

    private OnItemClickListener itemClickListener;

    public QuestionAdapter(Context context, QuestionResponse questionResponse, OnItemClickListener listener) {
        this.mContext = context;
        this.mQuestionResponse = questionResponse;
        this.itemClickListener = listener;
    }

    public static class QuestionHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tag_group_question)
        TagGroup tagGroup;
        @InjectView(R.id.tv_question_title)
        TextView tvTitle;
        @InjectView(R.id.tv_question_content)
        TextView tvContent;
        @InjectView(R.id.tv_question_has_focus)
        TextView tvFocus;
        @InjectView(R.id.tv_question_has_comment)
        TextView tvComment;
        @InjectView(R.id.bt_question_focus)
        Button btFocus;

        public QuestionHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public static class AnswerHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_question_answer_avatar)
        ImageView ivAvatar;
        @InjectView(R.id.tv_question_answer_username)
        TextView tvUsername;
        @InjectView(R.id.tv_question_answer_content)
        TextView tvContent;

        public AnswerHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(mContext);
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_VIEW_TYPE_QUESTION:
                view = vi.inflate(R.layout.question_content_header, parent, false);
                viewHolder = new QuestionHolder(view);
                break;
            case ITEM_VIEW_TYPE_ANSWER:
                view = vi.inflate(R.layout.question_answer_item, parent, false);
                viewHolder = new AnswerHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClicked(v, position);
            }
        };

        switch (getItemViewType(position)) {
            case ITEM_VIEW_TYPE_QUESTION:
                QuestionHolder question = (QuestionHolder) holder;

                question.tvTitle.setText(mQuestionResponse.question_info.question_content);
                question.tvContent
                        .setText(Html.fromHtml(mQuestionResponse.question_info.question_detail,
                                new PicassoImageGetter(mContext, question.tvContent),
                                null));

                // tag group
                question.tagGroup.setBrightColor(ResourceHelper.getColor(R.color.color_accent));
                ArrayList<String> tagStrings = new ArrayList<>();
                for (Topic topic : mQuestionResponse.question_topics) {
                    tagStrings.add(topic.topic_title);
                }
                question.tagGroup.setTags(tagStrings);

//                question.tagGroup.setOnClickListener(onClickListener);

                // focus and comment number
                question.tvFocus.setText("" + mQuestionResponse.question_info.focus_count);
                question.tvComment.setText("" + mQuestionResponse.answer_count);

                // focus button
                question.btFocus.setOnClickListener(onClickListener);
                if (mQuestionResponse.question_info.has_focus == 0) {
                    question.btFocus.setText(ResourceHelper.getString(R.string.action_focus));
                    question.btFocus.setBackgroundResource(R.drawable.button_focus);
                    question.btFocus.setTextColor(ResourceHelper.getColor(android.R.color.white));
                } else {
                    question.btFocus.setText(ResourceHelper.getString(R.string.action_not_focus));
                    question.btFocus.setBackgroundResource(R.drawable.button_focused_background);
                    question.btFocus.setTextColor(ResourceHelper.getColor(R.color.color_accent));
                }
                break;
            case ITEM_VIEW_TYPE_ANSWER:
                AnswerHolder answerHolder = (AnswerHolder) holder;
                Answer answer = mQuestionResponse.answers.get(position - 1);
                if (!TextUtils.isEmpty(answer.avatar_file)) {
                    Picasso.with(mContext).load(ApiClient.getAvatarUrl(answer.avatar_file)).into(answerHolder.ivAvatar);
                } else {
                    answerHolder.ivAvatar.setImageResource(R.drawable.ic_user_avatar);
                }
                answerHolder.tvUsername.setText(answer.nick_name);
                answerHolder.tvContent.setText(Html.fromHtml(FormatHelper.formatHomeHtmlStr(answer.answer_content)));

                answerHolder.ivAvatar.setOnClickListener(onClickListener);
                answerHolder.tvUsername.setOnClickListener(onClickListener);
                answerHolder.tvContent.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionResponse.answer_count + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == 0) {
            type = ITEM_VIEW_TYPE_QUESTION;
        } else {
            type = ITEM_VIEW_TYPE_ANSWER;
        }
        return type;
    }

    public Answer getAnswer(int position) {
        return mQuestionResponse.answers.get(position - 1);
    }

    public QuestionInfo getQuestionInfo() {
        return mQuestionResponse.question_info;
    }

    public void setFocused(boolean isFocus) {
        if (isFocus) {
            mQuestionResponse.question_info.has_focus = 1;
        } else {
            mQuestionResponse.question_info.has_focus = 0;
        }
        notifyDataSetChanged();
    }
}
