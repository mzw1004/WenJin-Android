package com.twt.service.wenjin.ui.answer.comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Comment;
import com.twt.service.wenjin.support.FormatHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/4/6.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContect;
    private ArrayList<Comment> mDataSet = new ArrayList<>();

    public CommentAdapter(Context contect) {
        this.mContect = contect;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

//        @InjectView(R.id.iv_comment_item_avatar)
//        ImageView ivAvatar;
        @InjectView(R.id.tv_comment_item_username)
        TextView tvUsername;
        @InjectView(R.id.tv_comment_item_content)
        TextView tvContent;
        @InjectView(R.id.tv_comment_item_add_time)
        TextView tvAddTime;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContect).inflate(R.layout.comment_list_item, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Comment item = mDataSet.get(i);
        ItemHolder itemHolder = (ItemHolder) viewHolder;
        itemHolder.tvUsername.setText(item.user_name);
        if (item.at_user != null) {
            itemHolder.tvContent.setText(FormatHelper.formatCommentReply(item.at_user.user_name, item.content));
        } else {
            itemHolder.tvContent.setText(item.content);
        }
        itemHolder.tvAddTime.setText(FormatHelper.formatAddDate(item.add_time));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void updateData(Comment[] comments) {
        mDataSet.clear();
        for (Comment c : comments) {
            mDataSet.add(c);
        }
        notifyDataSetChanged();
    }
}
