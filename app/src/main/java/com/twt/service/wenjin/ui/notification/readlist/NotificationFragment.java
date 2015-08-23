package com.twt.service.wenjin.ui.notification.readlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.NotificationItem;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseFragment;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.article.ArticleActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Green on 15-6-7.
 */
public class NotificationFragment extends BaseFragment implements NotificationView,
        SwipeRefreshLayout.OnRefreshListener,OnItemClickListener {


    public interface IUpdateNotificationIcon{
        void updateNotificationIcon();
    }

    public final static String PARAM_TYPE = "TYPE";
    private int type;

    @Inject
    NotificationPresenter mPresenter;

    @InjectView(R.id.notification_swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @InjectView(R.id.notification_recycler_view)
    RecyclerView mRecyclerView;

    private View mTvMarkall;
    private NotificationAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private IUpdateNotificationIcon mUpdateNotifiIconListener;

    public static NotificationFragment getInstance(int position){
        NotificationFragment notificationFragment = new NotificationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NotificationFragment.PARAM_TYPE, position);
        notificationFragment.setArguments(bundle);
        return notificationFragment;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new NotificationModule(this));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(PARAM_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.inject(this, rootView);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new NotificationAdapter(getActivity(),type, this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mTvMarkall = getParentFragment().getView().findViewById(R.id.tv_mark_all);
        if(type == 0){
            mTvMarkall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mUpdateNotifiIconListener != null){
                        mUpdateNotifiIconListener.updateNotificationIcon();
                    }
                    mPresenter.markAllNotificationAsRead();
                    hideMarkAllView();
                }
            });
        }
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fstVisibleItemPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lstVisibleItemPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                if (lstVisibleItemPos == totalItemCount - 1 && dy > 0) {
                    mPresenter.loadMoreNotificationItems(type);
                    mUpdateNotifiIconListener.updateNotificationIcon();
                }
            }
        });

        mPresenter.firstTimeLoadNotificationItems(type);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void hideViewMarkAll() {
        hideMarkAllView();
        mPresenter.refreshNotificationItems(type);
    }

    @Override
    public void startRefresh() {
        if(mRefreshLayout != null){
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopRefresh() {
        if(mRefreshLayout != null){
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void hideLoadMoreFooter() {
        mAdapter.setUserFooter(false);
    }

    @Override
    public void useLoadMoreFooter() {
        mAdapter.setUserFooter(true);
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshItems(List<NotificationItem> items) {
        LogHelper.v("showMarkAllviewInfo","type = "+type + "items.size() = "+items.size());
        mAdapter.refreshItems(items);
        if(mAdapter.getItemCount() > 0){
            showMarkAllView();
        }
    }

    @Override
    public void addMoreItems(List<NotificationItem> items, int argTotalRows) {
        if( mAdapter.getItemCount() < argTotalRows -1) {
            mAdapter.addItems(items);
            showMarkAllView();
        }else {
            //this.toastMessage(getResources().getString(R.string.no_more_information));
        }

    }

    @Override
    public void deleteItem(int argPosition) {
        if(type == 1 ){return;}
        NotificationItem item = mAdapter.getItems(argPosition);
        mPresenter.markNotificationAsRead(item.notification_id);
        mAdapter.deleteItem(argPosition);
        if(mAdapter.getItemCount() == 4){
            mPresenter.loadMoreNotificationItems(type);
        }
        if(mAdapter.getItemCount() < 1) {
            hideMarkAllView();
        }

        mUpdateNotifiIconListener.updateNotificationIcon();

    }

    @Override
    public void startQuestionArticleActivity(int position) {
        NotificationItem notificationItem = mAdapter.getItems(position);
        if(notificationItem.related != null){
            QuestionActivity.actionStart(getActivity(), Integer.valueOf(notificationItem.related.question_id));
        }else {
            ArticleActivity.actionStart(getActivity(), Integer.valueOf(notificationItem.key_url),"");
        }
    }

    @Override
    public void startAnswerActivity(int position) {
        NotificationItem item = mAdapter.getItems(position);
        if(item.related != null){
            AnswerDetailActivity.actionStart(getActivity(), Integer.valueOf(item.related.answer_id),"");
        }
    }

    @Override
    public void startProfileActivity(int position) {
        NotificationItem notificationItem = mAdapter.getItems(position);
        if(notificationItem.uid != null){
            ProfileActivity.actionStart(getActivity(), Integer.valueOf(notificationItem.uid));
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshNotificationItems(type);
    }

    @Override
    public void onItemClicked(View view, int position) {
        mPresenter.onItemClick(view, position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mUpdateNotifiIconListener = (IUpdateNotificationIcon) activity;
    }

    @Override
    public void onStop() {
        super.onStop();
        hideMarkAllView();
    }

    public void showMarkAllView(){
        if(mTvMarkall == null){return;}
        if(mTvMarkall.getVisibility() == View.GONE && type == 0){
            mTvMarkall.setTranslationY(mTvMarkall.getHeight());
            mTvMarkall.setVisibility(View.VISIBLE);
            mTvMarkall.setAlpha(0.0f);
            mTvMarkall.animate().translationY(0)
                    .alpha(1.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mTvMarkall.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }

    public void hideMarkAllView(){
        if(mTvMarkall == null){return;}
        if(mTvMarkall.getVisibility() == View.VISIBLE) {
            mTvMarkall.animate().translationY(mTvMarkall.getHeight())
                    .alpha(0.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mTvMarkall.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
