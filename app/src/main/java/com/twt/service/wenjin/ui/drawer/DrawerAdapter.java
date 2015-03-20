package com.twt.service.wenjin.ui.drawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twt.service.wenjin.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/3/20.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private ArrayList<String> mDataset = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.drawer_list_item_name)
        TextView mTextView;

        View mRootView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mRootView = itemView;
        }
    }

    public DrawerAdapter(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater vi = LayoutInflater.from(viewGroup.getContext());
        View view = vi.inflate(R.layout.drawer_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.mTextView.setText(mDataset.get(i));
        viewHolder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(String item) {
        mDataset.add(item);
        notifyDataSetChanged();
    }
}
