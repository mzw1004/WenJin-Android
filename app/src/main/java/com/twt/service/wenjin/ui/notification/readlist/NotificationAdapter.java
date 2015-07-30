package com.twt.service.wenjin.ui.notification.readlist;

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
import com.twt.service.wenjin.bean.NotificationItem;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Green on 15-6-22.
 */
public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;
    private static final int UNREAD = 0;
    private static final int READ = 1;

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private int mPageType;  //0:unread 1:read

    private List<NotificationItem> mDataSet = new ArrayList<>();

    private Boolean isUseFooter = false;

    public static class ItemHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.iv_notifi_item_avatar)
        ImageView ivAvatar;
        @InjectView(R.id.tv_notifi_item_username)
        TextView tvUsername;
        @InjectView(R.id.tv_notifi_item_status)
        TextView tvStatus;
        @InjectView(R.id.tv_notifi_item_title)
        TextView tvTitle;
        @InjectView(R.id.tv_notifi_item_content)
        TextView tvContent;
        @InjectView(R.id.tv_notifi_item_time)
        TextView tvTime;
        @InjectView(R.id.tv_notifi_item_markasread)
        TextView tvMarkasread;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public static class FooterHoler extends RecyclerView.ViewHolder{

        @InjectView(R.id.tv_footer_load_more)
        TextView tvLoadMore;
        @InjectView(R.id.pb_footer_load_more)
        ProgressBar pbLoadMore;

        public FooterHoler(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public NotificationAdapter(Context argContext,int argPageType , OnItemClickListener argOnItemClickListener){
        this.mContext = argContext;
        this.mOnItemClickListener = argOnItemClickListener;
        this.mPageType = argPageType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder;
        View view;
        if(viewType == ITEM_VIEW_TYPE_ITEM){
            view = layoutInflater.inflate(R.layout.notification_list_item, parent, false);
            viewHolder = new ItemHolder(view);
        }else {
            view = layoutInflater.inflate(R.layout.recyclerview_footer_load_more, parent, false);
            viewHolder = new FooterHoler(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder argHolder, final int argPosition) {

        int type = getItemViewType(argPosition);
        if(type == ITEM_VIEW_TYPE_ITEM){
            ItemHolder itemHolder = (ItemHolder) argHolder;

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClicked(v, argPosition);
                }
            };

            itemHolder.tvTitle.setOnClickListener(clickListener);
            itemHolder.tvContent.setOnClickListener(clickListener);

            if(mPageType == READ){
                itemHolder.tvMarkasread.setVisibility(View.GONE);
            }else {
                itemHolder.tvMarkasread.setOnClickListener(clickListener);
            }

            NotificationItem notificationItem = mDataSet.get(argPosition);
            if(notificationItem.anonymous == 1){
                itemHolder.ivAvatar.setOnClickListener(null);
                itemHolder.tvUsername.setOnClickListener(null);

                itemHolder.tvUsername.setText(ResourceHelper.getString(R.string.anonymous));
                itemHolder.ivAvatar.setImageResource(R.drawable.ic_user_avatar);

            }else {
                itemHolder.ivAvatar.setOnClickListener(clickListener);
                itemHolder.tvUsername.setOnClickListener(clickListener);

                itemHolder.tvUsername.setText(notificationItem.nick_name);
                if (!TextUtils.isEmpty(notificationItem.avatar)) {
                    Picasso.with(mContext).load(ApiClient.getAvatarUrl(notificationItem.avatar)).into(itemHolder.ivAvatar);
                } else {
                    itemHolder.ivAvatar.setImageResource(R.drawable.ic_user_avatar);
                }
            }

            itemHolder.tvTime.setText(FormatHelper.getTimeFromNow( Long.valueOf( notificationItem.add_time)));
            switch (notificationItem.action_type){
                case "101":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.followed_you));
                    break;
                case "102":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.reply_question));
                    break;
                case "103":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.question_comment_ated_you));
                    break;
                case "104":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.invite_you_answer_question));
                    break;
                case "105":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.replied_your_comment));
                    break;
                case "106":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.comment_your_question));
                    break;
                case "107":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.agree_your_answer));
                    break;
                case "108":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.thanked_your_answer));
                    break;
                case "110":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.edited_your_question));
                    break;
                case "111":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.deleted_your_question));
                    break;
                case "114":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.thanked_your_question));
                    break;
                case "115":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.answer_ated_you));
                    break;
                case "116":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.answer_comment_atted_you));
                    break;
                case "117":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.comment_your_article));
                    break;
                case "118":
                    itemHolder.tvStatus.setText(ResourceHelper.getString(R.string.article_comment_mentioned_you));
                    break;
            }

            if(notificationItem.title == null){
                //itemHolder.tvContent.setVisibility(View.GONE);
                itemHolder.tvTitle.setVisibility(View.GONE);
            }else {
                itemHolder.tvTitle.setVisibility(View.VISIBLE);
                //itemHolder.tvContent.setVisibility(View.VISIBLE);
                itemHolder.tvTitle.setText(notificationItem.title);
            }


        }else{}
    }

    @Override
    public int getItemCount() {
        int itemCount = mDataSet.size();
        if(isUseFooter){
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if(!isUseFooter){
            return  ITEM_VIEW_TYPE_ITEM;
        }else if(position < getItemCount() -1){
            return ITEM_VIEW_TYPE_ITEM;
        }else {
            return ITEM_VIEW_TYPE_FOOTER;
        }
    }

    public void setUserFooter(Boolean argIsVisible){
        this.isUseFooter = argIsVisible;
        notifyDataSetChanged();
    }

    public void refreshItems(List<NotificationItem> argItems){
        mDataSet.clear();
        mDataSet.addAll(argItems);
        notifyDataSetChanged();
    }

    public void addItems(List<NotificationItem> items){
        mDataSet.addAll(items);
        notifyDataSetChanged();
    }

    public NotificationItem getItems(int argPosition){
        return mDataSet.get(argPosition);
    }

    public void deleteItem(int argPostion){
        mDataSet.remove(argPostion);
        notifyDataSetChanged();
    }


}
