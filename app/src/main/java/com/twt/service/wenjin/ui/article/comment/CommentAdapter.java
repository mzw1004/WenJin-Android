package com.twt.service.wenjin.ui.article.comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.ArticleComment;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by RexSun on 15/7/17.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private ArrayList<ArticleComment> mDataSet = new ArrayList<>();

    public CommentAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_comment_item_username)
        TextView tvUsername;
        @Bind(R.id.tv_comment_item_content)
        TextView tvContent;
        @Bind(R.id.tv_comment_item_add_time)
        TextView tvAddTime;
        View rootView;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ArticleComment comment = mDataSet.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(v, position);
            }
        });
        itemHolder.tvUsername.setText(comment.user_info.nick_name);
        itemHolder.tvAddTime.setText(FormatHelper.formatAddDate(comment.add_time));
        itemHolder.tvContent.setText(comment.message);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void updateData(ArticleComment[] comments) {
        mDataSet.clear();
        for (ArticleComment comment : comments) {
            mDataSet.add(comment);
        }
        notifyDataSetChanged();
    }

    public String getUserName(int position){
        return mDataSet.get(position).user_info.user_name;
    }
}
