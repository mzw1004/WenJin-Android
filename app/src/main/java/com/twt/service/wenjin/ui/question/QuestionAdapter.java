package com.twt.service.wenjin.ui.question;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.QuestionResponse;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/3/27.
 */
public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_QUESTION = 0;
    private static final int ITEM_VIEW_TYPE_ANSWER = 1;

    private Context mContext;
    private QuestionResponse mQuestionResponse;

    public QuestionAdapter(Context context, QuestionResponse questionResponse) {
        this.mContext = context;
        this.mQuestionResponse = questionResponse;
    }

    public static class QuestionHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_question_title)
        TextView tvTitle;
        @InjectView(R.id.tv_question_content)
        TextView tvContent;
        @InjectView(R.id.tv_question_has_focus)
        TextView tvFocus;
        @InjectView(R.id.tv_question_has_comment)
        TextView tvComment;

        public QuestionHolder(View itemView) {
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
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_VIEW_TYPE_QUESTION:
                QuestionHolder question = (QuestionHolder) holder;
                question.tvTitle.setText(mQuestionResponse.question_info.question_detail);
                question.tvContent.setText(mQuestionResponse.question_info.question_content);
                question.tvFocus.setText(mQuestionResponse.question_info.focus_count);
                question.tvComment.setText(mQuestionResponse.answer_count);
                break;
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
}
