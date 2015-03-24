package com.twt.service.wenjin.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.HomeItem;
import com.twt.service.wenjin.support.DateHelper;
import com.twt.service.wenjin.support.ResourceHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/3/24.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<HomeItem> mDataset = new ArrayList<>();

    public static class ItemHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_home_item_avatar)
        ImageView ivAvatar;
        @InjectView(R.id.iv_home_item_agree)
        ImageView ivAgree;
        @InjectView(R.id.tv_home_item_username)
        TextView tvUsername;
        @InjectView(R.id.tv_home_item_status)
        TextView tvStatus;
        @InjectView(R.id.tv_home_item_title)
        TextView tvTitle;
        @InjectView(R.id.tv_home_item_content)
        TextView tvContent;
        @InjectView(R.id.tv_home_item_time)
        TextView tvTime;
        @InjectView(R.id.tv_home_item_agree_number)
        TextView tvAgreeNo;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(parent.getContext());
        View view = vi.inflate(R.layout.home_list_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        HomeItem homeItem = mDataset.get(position);
        itemHolder.tvUsername.setText(homeItem.user_info.user_name);
        itemHolder.tvTitle.setText(homeItem.question_info.question_content);
        itemHolder.tvTime.setText(DateHelper.getTimeFromNow(homeItem.add_time));
        switch (homeItem.associate_action) {
            case 101:
                itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.post_question));
                break;
            case 105:
                itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.concern_question));
                break;
            case 201:
                itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.reply_question));
                break;
            case 204:
                itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.agree_answer));
                break;
            case 501:
                itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.post_article));
                break;
            case 502:
                itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.agree_article));
                break;
        }
        if (homeItem.answer_info != null) {
            itemHolder.tvContent.setVisibility(View.VISIBLE);
            itemHolder.ivAgree.setVisibility(View.VISIBLE);
            itemHolder.tvAgreeNo.setVisibility(View.VISIBLE);

            String content = homeItem.answer_info.answer_content;
            if (content.length() > 80) {
                itemHolder.tvContent.setText(content.toCharArray(), 0, 80);
            } else {
                itemHolder.tvContent.setText(content);
            }

            itemHolder.tvAgreeNo.setText("" + homeItem.answer_info.agree_count);
        } else {
            itemHolder.tvContent.setVisibility(View.GONE);
            itemHolder.ivAgree.setVisibility(View.GONE);
            itemHolder.tvAgreeNo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateData(ArrayList<HomeItem> items) {
        mDataset.clear();
        mDataset.addAll(items);
        notifyDataSetChanged();
    }
}
