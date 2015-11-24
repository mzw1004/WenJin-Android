package com.twt.service.wenjin.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.HomeItem;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseFragment;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.article.ArticleActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;
import com.twt.service.wenjin.ui.topic.detail.TopicDetailActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements
        HomeView, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();


    @Inject
    HomePresenter mPresenter;

    @Bind(R.id.home_swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.home_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.fab_fastbacktotop)
    FloatingActionButton mFabFastTotop;
//    @InjectView(R.id.fab_post_article)
//    FloatingActionButton mFabArticle;

    private HomeAdapter mHomeAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mPrevFirstVisiblePosition;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPresenter.refreshHomeItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mRefreshLayout.setOnRefreshListener(this);

        mHomeAdapter = new HomeAdapter(getActivity(), this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHomeAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem == totalItemCount - 1 && dy > 0) {
                    LogHelper.v(LOG_TAG, "start loading more");
                    mPresenter.loadMoreHomeItems();
                }
                if (firstVisibleItemPosition > mPrevFirstVisiblePosition) {
//                    LogHelper.v(LOG_TAG, "scroll down");
                    showFastTotopFab();
                } else if (firstVisibleItemPosition < mPrevFirstVisiblePosition) {
//                    LogHelper.v(LOG_TAG, "scroll up");
                    hideFastTotopFab();
                }
                mPrevFirstVisiblePosition = firstVisibleItemPosition;
            }
        });

        mFabFastTotop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });

        //mPresenter.refreshHomeItems();
        mPresenter.firstTimeRefreshHomeItems();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new HomeModule(this));
    }

    @Override
    public void onItemClicked(View v, int position) {
        mPresenter.onItemClicked(v, position);
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshHomeItems();
    }

    @Override
    public void refreshItems(ArrayList<HomeItem> items) {
        mHomeAdapter.refreshItems(items);
    }

    @Override
    public void loadMoreItems(ArrayList<HomeItem> items) {
        mHomeAdapter.addItems(items);
    }

    @Override
    public void startQuestionArticlActivity(int position) {
        HomeItem item = mHomeAdapter.getItem(position);
        if (item.question_info != null) {
            LogHelper.v(LOG_TAG, "start question activity");
            QuestionActivity.actionStart(getActivity(), item.question_info.question_id);
        }else if (item.article_info !=null){
            ArticleActivity.actionStart(getActivity(), item.article_info.id, item.article_info.title);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void startAnswerActivity(int position) {
        HomeItem item = mHomeAdapter.getItem(position);
        if (item.answer_info != null) {
            AnswerDetailActivity.actionStart(getActivity(), item.answer_info.answer_id, item.question_info.question_content);
        }
    }

    @Override
    public void startProfileActivity(int position) {
        HomeItem item = mHomeAdapter.getItem(position);
        if(item.topic_info != null){
            TopicDetailActivity.actionStart(getActivity(),item.topic_info.topic_id ,item.topic_info.topic_title);
            return;
        }
        if (item.user_info != null) {
            ProfileActivity.actionStart(getActivity(), item.user_info.uid);
        }
    }

    @Override
    public void showRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void hideLoadMoreFooter() {
        mHomeAdapter.setUseFooter(false);
    }

    @Override
    public void useLoadMoreFooter() {
        if (mHomeAdapter != null) {
            mHomeAdapter.setUseFooter(true);
        }
    }


    @Override
    public void showFastTotopFab() {
//        if(mFabFastTotop.getVisibility() == View.GONE) {
//            mFabFastTotop.setVisibility(View.VISIBLE);
//        }
        mFabFastTotop.animate()
                .alpha(1.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFabFastTotop.setVisibility(View.VISIBLE);
                    }
                })
                .start();
    }

    @Override
    public void hideFastTotopFab() {
        mFabFastTotop.animate()
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFabFastTotop.setVisibility(View.GONE);
                    }
                }).start();
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
