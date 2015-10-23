package com.twt.service.wenjin.ui.topic.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.BestAnswer;
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by M on 2015/4/15.
 */
public class TopicDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BestAnswer> mAnswers = new ArrayList<>();

    private Context mContext;
    private OnItemClickListener listener;

    public TopicDetailAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_explore_item_title)
        TextView tvTitle;
        @Bind(R.id.tv_explore_item_user)
        TextView tvUsername;
        @Bind(R.id.tv_explore_item_time)
        TextView tvAddTime;
        @Bind(R.id.iv_explore_item_avatar)
        ImageView ivAvatar;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.explore_list_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        BestAnswer bestAnswer = mAnswers.get(position);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(v, position);
            }
        };
        itemHolder.tvTitle.setText(bestAnswer.question_info.question_content);
        itemHolder.tvUsername.setText(bestAnswer.answer_info.nick_name);
        if (!TextUtils.isEmpty(bestAnswer.answer_info.avatar_file)) {
            Picasso.with(mContext).load(ApiClient.getAvatarUrl(bestAnswer.answer_info.avatar_file)).into(itemHolder.ivAvatar);
        } else {
            itemHolder.ivAvatar.setImageResource(R.drawable.ic_user_avatar);
        }
        itemHolder.tvAddTime.setVisibility(View.GONE);

        itemHolder.tvTitle.setOnClickListener(clickListener);
        itemHolder.ivAvatar.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return mAnswers.size();
    }

    public void updateItem(BestAnswer[] bestAnswers) {
        mAnswers.clear();
        mAnswers.addAll(Arrays.asList(bestAnswers));
        notifyDataSetChanged();
    }

    public BestAnswer getItem(int position) {
        return mAnswers.get(position);
    }
}
