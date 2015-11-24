package com.twt.service.wenjin.ui.search.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.SearchDetailQuestion;
import com.twt.service.wenjin.bean.SearchQuestion;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/17.
 */
public class SearchQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = SearchQuestionFragment.class.getSimpleName();

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;

    private Context _context;
    private OnItemClickListener _onItemClicked;

    private List<SearchQuestion> _DataSet = new ArrayList<>();

    private boolean _useFooter;

    public SearchQuestionAdapter(Context context, OnItemClickListener onItemClickListener){
        _context = context;
        _onItemClicked = onItemClickListener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_search_question_item_title)
        TextView mTvTile;

        @Bind(R.id.tv_search_question_item_answercount)
        TextView mTvAnswercount;

        @Bind(R.id.tv_search_question_item_followcount)
        TextView mTvFollowcount;

        View rootView;
        public ItemHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
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
            View view = inflater.inflate(R.layout.search_question_list_item,viewGroup,false);
            viewHolder = new ItemHolder(view);
        }else{
            View view = inflater.inflate(R.layout.recyclerview_footer_load_more,viewGroup,false);
            viewHolder = new FooterHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position) == ITEM_VIEW_TYPE_ITEM){
            SearchQuestion questionItem = _DataSet.get(position);
            ItemHolder itemHolder = (ItemHolder) holder;

            View.OnClickListener onClickListener = new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    _onItemClicked.onItemClicked(v, position);
                }
            };

            itemHolder.rootView.setOnClickListener(onClickListener);

            itemHolder.mTvTile.setText(questionItem.name);
            itemHolder.mTvAnswercount.setText(questionItem.detail.answer_count);
            itemHolder.mTvFollowcount.setText(questionItem.detail.focus_count);


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

    public SearchQuestion getItem(int position){
        return _DataSet.get(position);
    }

    public void updateData(List<SearchQuestion> items){
        _DataSet.clear();
        _DataSet.addAll(items);
        notifyDataSetChanged();

    }

    public void addData(List<SearchQuestion> items){
        _DataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }
}
