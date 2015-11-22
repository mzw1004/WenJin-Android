package com.twt.service.wenjin.ui.draft;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.AnswerDraft;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by M on 2015/5/17.
 */
public class DraftAdapter extends RecyclerView.Adapter {

    private ArrayList<AnswerDraft> mDataSet;

    private OnItemClickListener listener;

    private boolean isDeleteMode = false;

    public DraftAdapter(ArrayList<AnswerDraft> drafts, OnItemClickListener listener) {
        this.mDataSet = drafts;
        this.listener = listener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_draft_item_title)
        TextView tvTitle;
        @Bind(R.id.tv_draft_item_content)
        TextView tvContent;
        @Bind(R.id.tv_draft_item_delete)
        TextView tvDelete;

        View rootView;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.draft_list_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        AnswerDraft draft = mDataSet.get(position);
        itemHolder.tvTitle.setText(draft.question_title);
        itemHolder.tvContent.setText(draft.content);

        if (isDeleteMode()) {
            itemHolder.tvDelete.setVisibility(View.VISIBLE);
            itemHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(v, position);
                    LogHelper.d("DraftAdapter", "position: " + position);
                }
            });
            itemHolder.rootView.setOnClickListener(null);
        } else {
            itemHolder.tvDelete.setVisibility(View.GONE);
            itemHolder.tvDelete.setOnClickListener(null);
            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(v, position);
                    LogHelper.d("DraftAdapter", "position: " + position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setDeleteMode(boolean isDeleteMode) {
        this.isDeleteMode = isDeleteMode;
        notifyDataSetChanged();
    }

    public boolean isDeleteMode() {
        return isDeleteMode;
    }

    public AnswerDraft getAnswerDraft(int position) {
        return mDataSet.get(position);
    }

    public void deleteAnswerDraft(int position) {
        new Delete().from(AnswerDraft.class).where("question_id = ?", mDataSet.get(position).question_id).execute();
        mDataSet.remove(position);
        notifyDataSetChanged();
    }

}
