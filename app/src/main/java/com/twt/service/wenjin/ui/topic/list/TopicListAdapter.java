package com.twt.service.wenjin.ui.topic.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Topic;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/4/8.
 */
public class TopicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Topic> mTopics = new ArrayList<>();

    public TopicListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_topic_item_pic)
        ImageView imageView;
        @InjectView(R.id.tv_topic_item_title)
        TextView tvTitle;
        @InjectView(R.id.tv_topic_item_description)
        TextView tvDescription;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(mContext);
        View view = vi.inflate(R.layout.topic_list_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Topic topic = mTopics.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.tvTitle.setText(topic.topic_title);
        if (topic.topic_description != null) {
            itemHolder.tvDescription.setText(topic.topic_description);
        }
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    public void updateTopics(Topic[] topics) {
        mTopics.clear();
        mTopics.addAll(Arrays.asList(topics));
        notifyDataSetChanged();
    }

    public void addTopics(Topic[] topics) {
        mTopics.addAll(Arrays.asList(topics));
        notifyDataSetChanged();
    }
}
