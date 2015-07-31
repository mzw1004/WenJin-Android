package com.twt.service.wenjin.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.twt.service.wenjin.bean.HomeItem;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.common.PicassoImageGetter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/3/24.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;

    private Context mContext;
    private ArrayList<HomeItem> mDataset = new ArrayList<>();

    private boolean useFooter = false;

    private OnItemClickListener onItemClickListener;

    public HomeAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

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

    public static class FooterHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_footer_load_more)
        TextView tvLoadMore;
        @InjectView(R.id.pb_footer_load_more)
        ProgressBar pbLoadMore;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder;
        View view;
        if (viewType == ITEM_VIEW_TYPE_ITEM) {
            view = vi.inflate(R.layout.home_list_item, parent, false);
            viewHolder = new ItemHolder(view);
        } else {
            view = vi.inflate(R.layout.recyclerview_footer_load_more, parent, false);
            viewHolder = new FooterHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if (type == ITEM_VIEW_TYPE_ITEM) {
            ItemHolder itemHolder = (ItemHolder) holder;

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClicked(v, position);
                }
            };
            itemHolder.ivAvatar.setOnClickListener(clickListener);
            itemHolder.tvUsername.setOnClickListener(clickListener);
            itemHolder.tvTitle.setOnClickListener(clickListener);
//            itemHolder.ivAgree.setOnClickListener(clickListener);
            itemHolder.tvContent.setOnClickListener(clickListener);

            HomeItem homeItem = mDataset.get(position);

            itemHolder.tvTime.setText(FormatHelper.getTimeFromNow(homeItem.add_time));
            switch (homeItem.associate_action) {
                case 101:
                    if(homeItem.topic_info != null) {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.post_question_topic));
                    }
                    else {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.post_question));
                    }
                    break;
                case 105:
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.concern_question));
                    break;
                case 201:
                    if(homeItem.topic_info != null) {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.reply_question_topic));
                    }else {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.reply_question));
                    }
                    break;
                case 204:
                    if(homeItem.topic_info != null) {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.agree_answer_topic));
                    }else {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.agree_answer));
                    }
                    break;
                case 501:
                    if(homeItem.topic_info != null) {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.post_article_topic));
                    }else {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.post_article));
                    }
                    break;
                case 502:
                    if(homeItem.topic_info != null) {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.agree_article_topic));
                    }else {
                        itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.agree_article));
                    }
                    break;
            }
            if(homeItem.topic_info != null){
                itemHolder.tvUsername.setText(homeItem.topic_info.topic_title);
                if (!TextUtils.isEmpty(homeItem.topic_info.topic_pic)) {
                    Picasso.with(mContext).load(ApiClient.getTopicPicUrl(homeItem.topic_info.topic_pic)).into(itemHolder.ivAvatar);
                } else {
                    itemHolder.ivAvatar.setImageResource(R.drawable.ic_topic_pic);
                }
            }else {
                if (!TextUtils.isEmpty(homeItem.user_info.avatar_file)) {
                    Picasso.with(mContext).load(ApiClient.getAvatarUrl(homeItem.user_info.avatar_file)).into(itemHolder.ivAvatar);
                } else {
                    itemHolder.ivAvatar.setImageResource(R.drawable.ic_user_avatar);
                }
                itemHolder.tvUsername.setText(homeItem.user_info.nick_name);
            }
            if (homeItem.question_info != null) {
                itemHolder.tvTitle.setText(homeItem.question_info.question_content);
            }
            if (homeItem.article_info != null) {
                itemHolder.tvTitle.setText(homeItem.article_info.title);
            }
            if (homeItem.answer_info != null) {
                itemHolder.tvContent.setVisibility(View.VISIBLE);
//                itemHolder.ivAgree.setVisibility(View.VISIBLE);
//                itemHolder.tvAgreeNo.setVisibility(View.VISIBLE);

                String content = homeItem.answer_info.answer_content;
                itemHolder.tvContent.setText(Html.fromHtml(FormatHelper.formatHomeHtmlStr(content)));

//                itemHolder.tvAgreeNo.setText("" + homeItem.answer_info.agree_count);
            } else {
                itemHolder.tvContent.setVisibility(View.GONE);
//                itemHolder.ivAgree.setVisibility(View.GONE);
//                itemHolder.tvAgreeNo.setVisibility(View.GONE);
            }
        } else if (type == ITEM_VIEW_TYPE_FOOTER) {
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mDataset.size();
        if (useFooter) {
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (!useFooter) {
            return ITEM_VIEW_TYPE_ITEM;
        } else if (position < getItemCount() - 1) {
            return ITEM_VIEW_TYPE_ITEM;
        } else {
            return ITEM_VIEW_TYPE_FOOTER;
        }
    }

    public void refreshItems(ArrayList<HomeItem> items) {
        mDataset.clear();
        mDataset.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<HomeItem> items) {
        setUseFooter(false);
        mDataset.addAll(getItemCount(), items);
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter) {
        this.useFooter = useFooter;
        notifyDataSetChanged();
    }

    public HomeItem getItem(int position) {
        return mDataset.get(position);
    }
}
