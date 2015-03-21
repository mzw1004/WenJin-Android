package com.twt.service.wenjin.ui.drawer;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twt.service.wenjin.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/3/20.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_DIVIDER = 1;

    private ArrayList<Integer> mIcons = new ArrayList<>();
    private ArrayList<String> mDataset = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
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

    public DrawerAdapter(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        LayoutInflater vi = LayoutInflater.from(viewGroup.getContext());
        View view = null;
        switch (viewType) {
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
            case ITEM_VIEW_TYPE_ITEM:
                ItemHolder itemHolder = (ItemHolder) viewHolder;
                itemHolder.mTextView.setText(mDataset.get(i));
                itemHolder.mImageView.setImageResource(mIcons.get(i));
                itemHolder.mRootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, i);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (mDataset.get(position) != null) {
            type = ITEM_VIEW_TYPE_ITEM;
        } else {
            type = ITEM_VIEW_TYPE_DIVIDER;
        }
        return type;
    }

    public void addItem(Integer iconId, String item) {
        mIcons.add(iconId);
        mDataset.add(item);
        notifyDataSetChanged();
    }

    public void addDivider() {
        addItem(null, null);
    }
}
