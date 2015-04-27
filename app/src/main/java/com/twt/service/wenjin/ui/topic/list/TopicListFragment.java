package com.twt.service.wenjin.ui.topic.list;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.interactor.TopicDetailInteractor;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseFragment;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.topic.detail.TopicDetailActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TopicListFragment extends BaseFragment implements
        TopicListView, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    private static final String LOG_TAG = TopicListFragment.class.getSimpleName();

    public static final String PARAM_TYPE = "type";

    @Inject
    TopicListPresenter mPresenter;

    @InjectView(R.id.swipe_refresh_layout_topic)
    SwipeRefreshLayout mRefreshLayout;
    @InjectView(R.id.recycler_view_topic)
    RecyclerView mRecyclerView;

    private TopicListAdapter mAdapter;
    private int type;

    public TopicListFragment() {
    }

    public static TopicListFragment getInstance(int position) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_TYPE, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(PARAM_TYPE);
        LogHelper.v(LOG_TAG, "onCreate, type: " + type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_topic_list, container, false);
        ButterKnife.inject(this, rootView);

        mRefreshLayout.setColorSchemeColors(ResourceHelper.getColor(R.color.color_primary));
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new TopicListAdapter(getActivity(), this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastitemposition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastitemposition == linearLayoutManager.getItemCount() - 1 && dy > 0) {
                    mPresenter.loadMoreTopics(type);
                }
            }
        });

        mPresenter.refreshTopics(type);

        return rootView;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new TopicListModule(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        LogHelper.v(LOG_TAG, "onStart, type: " + type);
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshTopics(type);
    }

    @Override
    public void updateTopics(Topic[] topics) {
        LogHelper.d(LOG_TAG, "topics length: " + topics.length);
        mAdapter.updateTopics(topics);
    }

    @Override
    public void addTopics(Topic[] topics) {
        mAdapter.addTopics(topics);
    }

    @Override
    public void startRefresh() {
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onItemClicked(View view, int position) {
        startTopicDetailActivity(position);
    }

    @Override
    public void stopRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showFooter() {
        mAdapter.setFooter(true);
    }

    @Override
    public void hideFooter() {
        mAdapter.setFooter(false);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startTopicDetailActivity(int position) {
        Topic topic = mAdapter.getItem(position);
        TopicDetailActivity.actionStart(getActivity(), topic.topic_id, topic.topic_title);
    }

}
