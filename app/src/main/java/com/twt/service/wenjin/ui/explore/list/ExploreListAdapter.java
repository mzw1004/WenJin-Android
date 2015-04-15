package com.twt.service.wenjin.ui.explore.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.ExploreItem;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by WGL on 2015/3/28.
 */
public class ExploreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;

    private Context _context;
    private OnItemClickListener _onItemClicked;

    private ArrayList<ExploreItem> _DataSet = new ArrayList<>();

    private boolean _useFooter;

    public static class ItemHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.tv_explore_item_title)
        TextView _tvTitle;

        @InjectView(R.id.iv_explore_item_avatar)
        ImageView _ivAvatar;

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

    public ExploreListAdapter(Context context,OnItemClickListener onItemClicked){
        _context = context;
        _onItemClicked = onItemClicked;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        RecyclerView.ViewHolder viewHolder;

        if(viewType == ITEM_VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.explore_list_item,viewGroup,false);
            viewHolder = new ItemHolder(view);
        }else{
            View view = inflater.inflate(R.layout.recyclerview_footer_load_more,viewGroup,false);
            viewHolder = new FooterHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == ITEM_VIEW_TYPE_ITEM){
            ExploreItem exploreItem = _DataSet.get(position);
            ItemHolder itemHolder = (ItemHolder) viewHolder;
            itemHolder._tvTitle.setText(exploreItem.question_content);
            itemHolder._tvTime.setText(FormatHelper.getTimeFromNow(exploreItem.update_time));
            if(0 == exploreItem.answer_count){
                if(exploreItem.user_info != null) {
                    itemHolder._tvUser.setText(exploreItem.user_info.nick_name);
                    if(exploreItem.user_info.avatar_file != ""){
                        Picasso.with(_context).load(ApiClient.getAvatarUrl(exploreItem.user_info.avatar_file)).into(itemHolder._ivAvatar);
                    }
                }
                itemHolder._tvState.setText(ResourceHelper.getString(R.string.post_question));
            }else{
                if(exploreItem.answer_users.length > 0){
                    UserInfo userInfo = exploreItem.answer_users[0];
                    itemHolder._tvUser.setText(userInfo.nick_name);
                    if(userInfo.avatar_file != ""){
                        Picasso.with(_context).load(ApiClient.getAvatarUrl(userInfo.avatar_file)).into(itemHolder._ivAvatar);
                    }
                }
                itemHolder._tvState.setText(ResourceHelper.getString(R.string.reply_question));

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
        return position < _DataSet.size() ? ITEM_VIEW_TYPE_ITEM:ITEM_VIEW_TYPE_FOOTER;
    }

    public void updateData(ArrayList<ExploreItem> items){
        _DataSet.clear();
        _DataSet.addAll(items);
        notifyDataSetChanged();

    }

    public void addData(ArrayList<ExploreItem> items){
        _DataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }
}
