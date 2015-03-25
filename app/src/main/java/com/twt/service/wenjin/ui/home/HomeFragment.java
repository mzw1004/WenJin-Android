package com.twt.service.wenjin.ui.home;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.HomeItem;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

    @Inject
    HomePresenter mPresenter;

    @InjectView(R.id.home_swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @InjectView(R.id.home_recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.fab_menu_add)
    FloatingActionsMenu mFabMenu;
    @InjectView(R.id.fab_post_question)
    FloatingActionButton mFabQuestion;
    @InjectView(R.id.fab_post_article)
    FloatingActionButton mFabArticle;

    private HomeAdapter mHomeAdapter;
    private LinearLayoutManager mLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.refreshHomeItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, rootView);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mRefreshLayout.setOnRefreshListener(this);

        mHomeAdapter = new HomeAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mHomeAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    LogHelper.v(LOG_TAG, "start loading more");
                    mPresenter.loadMoreHomeItems();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return rootView;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new HomeModule(this));
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
        mHomeAdapter.setUseFooter(true);
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
