package com.twt.service.wenjin.ui.topic.list;

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
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.home.HomeAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by M on 2015/4/8.
 */
public class TopicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;

    private Context mContext;
    private ArrayList<Topic> mTopics = new ArrayList<>();

    private OnItemClickListener listener;
    private boolean useFooter;

    public TopicListAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_topic_item_pic)
        ImageView imageView;
        @Bind(R.id.tv_topic_item_title)
        TextView tvTitle;
        @Bind(R.id.tv_topic_item_description)
        TextView tvDescription;

        View rootview;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootview = itemView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(mContext);
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ITEM_VIEW_TYPE_ITEM) {
            view = vi.inflate(R.layout.topic_list_item, parent, false);
            viewHolder = new ItemHolder(view);
        } else {
            view = vi.inflate(R.layout.recyclerview_footer_load_more, parent, false);
            viewHolder = new HomeAdapter.FooterHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_ITEM) {
            Topic topic = mTopics.get(position);
            ItemHolder itemHolder = (ItemHolder) holder;

            itemHolder.rootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(v, position);
                }
            });

            itemHolder.tvTitle.setText(topic.topic_title);
            if (topic.topic_description != null) {
                itemHolder.tvDescription.setText(topic.topic_description);
            }
            if (TextUtils.isEmpty(topic.topic_pic)) {
                itemHolder.imageView.setImageResource(R.drawable.ic_topic_pic);
            } else {
                Picasso.with(mContext).load(ApiClient.getTopicPicUrl(topic.topic_pic)).into(itemHolder.imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = mTopics.size();
        if (useFooter) {
            count += 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mTopics.size()) {
            return ITEM_VIEW_TYPE_ITEM;
        } else {
            return ITEM_VIEW_TYPE_FOOTER;
        }
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

    public void setFooter(boolean useFooter) {
        this.useFooter = useFooter;
        notifyDataSetChanged();
    }

    public Topic getItem(int position) {
        return mTopics.get(position);
    }
}
