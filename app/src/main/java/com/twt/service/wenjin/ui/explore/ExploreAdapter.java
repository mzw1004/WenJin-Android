package com.twt.service.wenjin.ui.explore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.ExploreItem;
import com.twt.service.wenjin.support.ResourceHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExploreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ExploreItem> _DataSet = new ArrayList<>();

    public static class ItemHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.tv_explore_item_title)
        TextView _tvTitle;

        @InjectView(R.id.iv_explore_item_avatar)
        ImageView _tvAvatar;

        @InjectView(R.id.tv_explore_item_user)
        TextView _tvUser;

        @InjectView(R.id.tv_explore_item_state)
        TextView _tvState;

        @InjectView(R.id.tv_explore_item_time)
        TextView _tvTime;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.explore_list_item,viewGroup,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemHolder itemHolder = (ItemHolder) viewHolder;
        ExploreItem exploreItem = _DataSet.get(position);
        itemHolder._tvTitle.setText(exploreItem.question_content);
        if(0 == exploreItem.answer_count){
            itemHolder._tvState.setText(ResourceHelper.getString(R.string.post_question));
        }else{
            itemHolder._tvState.setText(ResourceHelper.getString(R.string.reply_question));
        }

        if(exploreItem.user_info != null) {
            itemHolder._tvUser.setText(exploreItem.user_info.user_name);
        }
//        itemHolder._tvTime.setText("");
//        itemHolder._tvAvatar.setImageResource();
    }

    @Override
    public int getItemCount() {
        return _DataSet.size();
    }

    public void updateData(ArrayList<ExploreItem> items){
        _DataSet.clear();
        _DataSet.addAll(items);
        notifyDataSetChanged();

    }
}
