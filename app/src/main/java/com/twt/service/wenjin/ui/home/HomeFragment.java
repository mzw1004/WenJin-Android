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

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.HomeItem;
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

    @Inject
    HomePresenter mPresenter;

    @InjectView(R.id.home_swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @InjectView(R.id.home_recycler_view)
    RecyclerView mRecyclerView;

    private HomeAdapter mHomeAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.loadingHomeItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, rootView);

        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mRefreshLayout.setOnRefreshListener(this);

        mHomeAdapter = new HomeAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mHomeAdapter);

        return rootView;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new HomeModule(this));
    }


    @Override
    public void onRefresh() {
        mPresenter.loadingHomeItems();
    }

    @Override
    public void updateListData(ArrayList<HomeItem> items) {
        mHomeAdapter.updateData(items);
    }

    @Override
    public void startRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
