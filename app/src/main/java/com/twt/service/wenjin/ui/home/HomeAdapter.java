package com.twt.service.wenjin.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.HomeItem;

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
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
