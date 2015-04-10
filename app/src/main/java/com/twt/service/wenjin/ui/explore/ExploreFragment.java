package com.twt.service.wenjin.ui.explore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.ExploreItem;
import com.twt.service.wenjin.ui.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExploreFragment extends BaseFragment implements ExploreView, SwipeRefreshLayout.OnRefreshListener{

    @Inject
    ExplorePresenter _explorePresenter;

    @InjectView(R.id.explore_recycler_view)
    RecyclerView _recyclerView;

    @InjectView(R.id.explore_swipe_refresh_layout)
    SwipeRefreshLayout _swipeRefreshLayout;

    private ExploreAdapter _exploreAdapter;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    protected List<Object> getModules(){return Arrays.<Object>asList(new ExploreModule(this));}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _explorePresenter.loadingExploreItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore,container,false);
        ButterKnife.inject(this,rootView);

        _swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        _swipeRefreshLayout.setOnRefreshListener(this);

        _exploreAdapter = new ExploreAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        _recyclerView.setLayoutManager(linearLayoutManager);
        _recyclerView.setAdapter(_exploreAdapter);

        return rootView;
    }


    @Override
    public void startRefresh() {
        if(null != _swipeRefreshLayout){
            _swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopRefresh() {
        if(null != _swipeRefreshLayout){
            _swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateListData(ArrayList<ExploreItem> items) {
        _exploreAdapter.updateData(items);
    }


    @Override
    public void onRefresh() {
        _explorePresenter.loadingExploreItems();
    }
}
