package com.twt.service.wenjin.ui.drawer;

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
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/3/20.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_DIVIDER = 2;

    private Context mContext;
    private ArrayList<Integer> mIcons = new ArrayList<>();
    private ArrayList<String> mDataset = new ArrayList<>();
    private ArrayList<Integer> mDividerPositions = new ArrayList<>();

    private int mHeaderId;
    private boolean mUseHeader;
    String mUsername;
    String mAvatarFile;
    String mSignature;
//    String mEmail;

    private int mSelectedItemIndex = 0;

    private OnItemClickListener mItemListener;
    private OnUserClickListener mUserListener;

    public interface OnUserClickListener {
        public void onUserClick(View view);
    }

    public DrawerAdapter(Context context, OnItemClickListener itemClickListener, OnUserClickListener userClickListener) {
        this.mContext = context;
        this.mItemListener = itemClickListener;
        this.mUserListener = userClickListener;
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.user_profile_image)
        ImageView mIvProfile;
        @InjectView(R.id.user_profile_background)
        ImageView mIvBackground;
        @InjectView(R.id.tv_user_profile_name)
        TextView mTvUsername;
        @InjectView(R.id.tv_user_profile_signature)
        TextView mTvUserSignature;
//        @InjectView(R.id.tv_user_profile_email)
//        TextView mTvEmail;

        View mRootView;

        public HeaderHolder(View itemView) {
            super(itemView);
            mRootView = itemView;
            ButterKnife.inject(this, mRootView);
        }
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.drawer_list_item_name)
        TextView mTextView;
        @InjectView(R.id.drawer_lsit_item_icon)
        ImageView mImageView;

        View mRootView;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mRootView = itemView;
        }
    }

    public static class DividerHolder extends RecyclerView.ViewHolder {

        View mRootView;

        public DividerHolder(View itemView) {
            super(itemView);
            mRootView = itemView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        LayoutInflater vi = LayoutInflater.from(mContext);
        View view;
        switch (viewType) {
            case ITEM_VIEW_TYPE_HEADER:
                view = vi.inflate(mHeaderId, viewGroup, false);
                viewHolder = new HeaderHolder(view);
                break;
            case ITEM_VIEW_TYPE_ITEM:
                view = vi.inflate(R.layout.drawer_list_item, viewGroup, false);
                viewHolder = new ItemHolder(view);
                break;
            case ITEM_VIEW_TYPE_DIVIDER:
                view = vi.inflate(R.layout.drawer_list_divider, viewGroup, false);
                viewHolder = new DividerHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        switch (getItemViewType(i)) {
            case ITEM_VIEW_TYPE_HEADER:
                HeaderHolder headerHolder = (HeaderHolder) viewHolder;
                if (mUsername != null) {
                    headerHolder.mTvUsername.setText(mUsername);
                }
//                if (mEmail != null) {
//                    headerHolder.mTvEmail.setText(mEmail);
//                }
                if(mSignature != null){
                    headerHolder.mTvUserSignature.setText(mSignature);
                }
                if (!TextUtils.isEmpty(mAvatarFile)) {
                    Picasso.with(mContext).load(ApiClient.getAvatarUrl(mAvatarFile)).skipMemoryCache().into(headerHolder.mIvProfile);
                }
                headerHolder.mIvProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserListener.onUserClick(v);
                    }
                });
                break;
            case ITEM_VIEW_TYPE_ITEM:
                final int itemIndex = getItemIndex(i);
                ItemHolder itemHolder = (ItemHolder) viewHolder;
                itemHolder.mTextView.setText(mDataset.get(itemIndex));
                itemHolder.mImageView.setImageResource(mIcons.get(itemIndex));
                itemHolder.mRootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemListener.onItemClicked(v, itemIndex);
                    }
                });
                if(itemIndex == mSelectedItemIndex) {
                    itemHolder.mRootView.setBackgroundColor(ResourceHelper.getColor(R.color.color_drawer_item_selected_background));
                    itemHolder.mTextView.setTextColor(ResourceHelper.getColor(R.color.color_primary));
                } else {
                    itemHolder.mRootView.setBackgroundColor(ResourceHelper.getColor(android.R.color.white));
                    itemHolder.mTextView.setTextColor(ResourceHelper.getColor(R.color.color_text_primary));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mDataset.size() + mDividerPositions.size();
        if (mUseHeader) {
            itemCount++;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == 0 && mUseHeader) {
            type = ITEM_VIEW_TYPE_HEADER;
        } else if (mDividerPositions.contains(position)) {
            type = ITEM_VIEW_TYPE_DIVIDER;
        } else {
            type = ITEM_VIEW_TYPE_ITEM;
        }
        return type;
    }

    private int getItemIndex(int position) {
        int index = position;
        int dividerCount = mDividerPositions.size();
        if (dividerCount > 0) {
            for(int i = dividerCount - 1; i >= 0; i--){
                if(index > mDividerPositions.get(i)) {
                    index -= 1;
                }
            }
        }
        if (mUseHeader) {
            index -= 1;
        }
        return index;
    }

    public void addItem(Integer iconId, String item) {
        mIcons.add(iconId);
        mDataset.add(item);
        notifyDataSetChanged();
    }

    public void addHeader(int resourceId) {
        mHeaderId = resourceId;
        mUseHeader = true;
        notifyDataSetChanged();
    }

    public void addDivider() {
        int itemCount = getItemCount();
        mDividerPositions.add(itemCount);
        notifyDataSetChanged();
    }

    public void setSelected(int position) {
        mSelectedItemIndex = position;
        notifyDataSetChanged();
    }

    public void updateUserInfo() {
        mUsername = PrefUtils.getPrefUsername();
        mAvatarFile = PrefUtils.getPrefAvatarFile();
        mSignature = PrefUtils.getPrefSignature();
        notifyDataSetChanged();
    }

}
