package com.twt.service.wenjin.ui.search.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.SearchQuestion;
import com.twt.service.wenjin.bean.SearchTopic;
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/17.
 */
public class SearchTopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = SearchTopicAdapter.class.getSimpleName();

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;

    private Context _context;
    private OnItemClickListener _onItemClicked;

    private List<SearchTopic> _DataSet = new ArrayList<>();

    private boolean _useFooter;

    public SearchTopicAdapter(Context context, OnItemClickListener onItemClickListener){
        _context = context;
        _onItemClicked = onItemClickListener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.iv_topic_item_pic)
        ImageView imageView;
        @Bind(R.id.tv_topic_item_title)
        TextView tvTitle;
        @Bind(R.id.tv_topic_item_description)
        TextView tvDescription;

        View rootView;
        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_footer_load_more)
        TextView tvLoadMore;
        @Bind(R.id.pb_footer_load_more)
        ProgressBar pbLoadMore;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        RecyclerView.ViewHolder viewHolder;

        if(viewType == ITEM_VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.topic_list_item,viewGroup,false);
            viewHolder = new ItemHolder(view);
        }else{
            View view = inflater.inflate(R.layout.recyclerview_footer_load_more,viewGroup,false);
            viewHolder = new FooterHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_ITEM) {
            SearchTopic topic = _DataSet.get(position);
            ItemHolder itemHolder = (ItemHolder) holder;

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _onItemClicked.onItemClicked(v, position);
                }
            });

            itemHolder.tvTitle.setText(topic.name);
            if (topic.detail.topic_description != null) {
                itemHolder.tvDescription.setText(topic.detail.topic_description);
            }

            if (TextUtils.isEmpty(topic.detail.topic_pic)) {
                itemHolder.imageView.setImageResource(R.drawable.ic_topic_pic);
                LogHelper.v(LOG_TAG, "no topic pic");
            } else {
                LogHelper.v(LOG_TAG,topic.detail.topic_pic);
                Picasso.with(_context).load(topic.detail.topic_pic).into(itemHolder.imageView);
            }
        }
    }


    @Override
    public int getItemCount() {
        int count = _DataSet.size();
        return _useFooter? ++count:count;
    }

    @Override
    public int getItemViewType(int position) {
        if (!_useFooter) {
            return ITEM_VIEW_TYPE_ITEM;
        } else if (position < getItemCount() - 1) {
            return ITEM_VIEW_TYPE_ITEM;
        } else {
            return ITEM_VIEW_TYPE_FOOTER;
        }
    }

    public SearchTopic getItem(int position){
        return _DataSet.get(position);
    }

    public void updateData(List<SearchTopic> items){
        _DataSet.clear();
        _DataSet.addAll(items);
        notifyDataSetChanged();

    }

    public void addData(List<SearchTopic> items){
        _DataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }
}
