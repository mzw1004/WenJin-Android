package com.twt.service.wenjin.ui.profile.follows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import com.twt.service.wenjin.bean.Follows;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/4/25.
 */
public class FollowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;

    private Context _context;
    private String _actionType;
    private int _uid;
    private boolean _useFooter;
    private OnItemClickListener _onItemClickListener;

    private List<Follows> _DataSet = new ArrayList<>();

    static class ItemHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.iv_follows_item_avatar)
        ImageView _ivAvatar;

        @Bind(R.id.tv_follows_item_name)
        TextView _tvName;

        @Bind(R.id.tv_follows_item_signature)
        TextView _tvSignatrue;

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

    public FollowsAdapter(Context context,String actionType,OnItemClickListener onItemClickListener){
        _context = context;
        _actionType = actionType;
        _onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        RecyclerView.ViewHolder viewHolder;

        if(viewType == ITEM_VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.follow_list_item,viewGroup,false);
            viewHolder = new ItemHolder(view);
        }else{
            View view = inflater.inflate(R.layout.recyclerview_footer_load_more,viewGroup,false);
            viewHolder = new FooterHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,final int position) {
        if(getItemViewType(position) == ITEM_VIEW_TYPE_ITEM){
            ItemHolder itemHolder = (ItemHolder) viewHolder;
            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _onItemClickListener.onItemClicked(v,position);
                }
            });
            Follows follow = _DataSet.get(position);
            itemHolder._tvName.setText(follow.nick_name);
            itemHolder._tvSignatrue.setText(follow.signature);
            if(!TextUtils.isEmpty(follow.signature)){
                itemHolder._tvSignatrue.setText(follow.signature);
            }else{
                itemHolder._tvSignatrue.setText("");
            }
            if(!TextUtils.isEmpty(follow.avatar_file)){
                Picasso.with(_context).load(ApiClient.getAvatarUrl(follow.avatar_file)).into(itemHolder._ivAvatar);
            } else {
                itemHolder._ivAvatar.setImageResource(R.drawable.ic_user_avatar);
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

    public void addData(List<Follows> items){
        _DataSet.addAll(items);
        notifyDataSetChanged();
    }

    public Follows getItem(int position){
        return _DataSet.get(position);
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }
}
