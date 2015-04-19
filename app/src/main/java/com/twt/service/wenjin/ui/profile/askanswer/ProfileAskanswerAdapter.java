package com.twt.service.wenjin.ui.profile.askanswer;

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
import com.twt.service.wenjin.bean.MyAnswer;
import com.twt.service.wenjin.bean.MyQuestion;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/4/17.
 */
public class ProfileAskanswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String ACTION_TYPE_ASK = "ask";
    private static final String ACTION_TYPE_ANSWER = "answer";
    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;

    private String _actionType;
    private Context _context;
    private OnItemClickListener _onItemClicked;
    private int _uid;
    private String _uname;
    private String _avatarurl;

    private List<Object> _DataSet = new ArrayList<>();

    private boolean _useFooter;

    public static class ItemHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.tv_home_item_title)
        TextView _tvTitle;

        @InjectView(R.id.iv_home_item_avatar)
        ImageView _ivAvatar;

        @InjectView(R.id.tv_home_item_username)
        TextView _tvUser;

        @InjectView(R.id.tv_home_item_status)
        TextView _tvState;

        @InjectView(R.id.tv_home_item_time)
        TextView _tvTime;

        @InjectView(R.id.tv_home_item_content)
        TextView _tvContent;

        @InjectView(R.id.v_home_divider)
        View _vDivider;

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

    public ProfileAskanswerAdapter(Context context,String actionType,int uid,String uname,String avatarurl,OnItemClickListener onItemClicked){
        _context = context;
        _onItemClicked = onItemClicked;
        _actionType = actionType;
        _uid = uid;
        _uname = uname;
        _avatarurl = avatarurl;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        RecyclerView.ViewHolder viewHolder;

        if(viewType == ITEM_VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.home_list_item,viewGroup,false);
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

            View.OnClickListener onClickListener = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    _onItemClicked.onItemClicked(v,position);
                }
            };

            itemHolder._tvTitle.setOnClickListener(onClickListener);
            itemHolder._tvUser.setText(_uname);

            if(_avatarurl != null){
                Picasso.with(_context).load(ApiClient.getAvatarUrl(_avatarurl)).into(itemHolder._ivAvatar);
            }

            if(_actionType.compareTo(ACTION_TYPE_ASK) == 0){
                itemHolder._tvTitle.setOnClickListener(onClickListener);
                MyQuestion myQuestion = (MyQuestion)_DataSet.get(position);
                itemHolder._tvState.setText(ResourceHelper.getString(R.string.post_question));
                itemHolder._vDivider.setVisibility(View.VISIBLE);
                itemHolder._tvContent.setVisibility(View.INVISIBLE);
                itemHolder._tvTitle.setText(myQuestion.title);
                itemHolder._tvTime.setVisibility(View.VISIBLE);
                itemHolder._tvTime.setText(FormatHelper.getTimeFromNow(myQuestion.add_time));
            }

            if(_actionType.compareTo(ACTION_TYPE_ANSWER) == 0){
                itemHolder._tvTitle.setOnClickListener(onClickListener);
                itemHolder._tvContent.setOnClickListener(onClickListener);
                MyAnswer myAnswer = (MyAnswer)_DataSet.get(position);
                itemHolder._tvState.setText(ResourceHelper.getString(R.string.reply_question));
                itemHolder._vDivider.setVisibility(View.INVISIBLE);
                itemHolder._tvTitle.setText(myAnswer.question_title);
                itemHolder._tvTime.setVisibility(View.INVISIBLE);
                itemHolder._tvContent.setVisibility(View.VISIBLE);
                itemHolder._tvContent.setText(myAnswer.answer_content);
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

    public Object getItem(int position){
        return _DataSet.get(position);
    }

    public void updateData(List<Object> items){
        //_DataSet.clear();
        //_DataSet.addAll(items);
        //notifyDataSetChanged();

    }

    public void addData(List<Object> items){
        _DataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }
}
