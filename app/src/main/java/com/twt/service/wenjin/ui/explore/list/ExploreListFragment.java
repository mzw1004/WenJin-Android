package com.twt.service.wenjin.ui.explore.list;

import android.os.Bundle;
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

public class ExploreListFragment extends BaseFragment implements ExploreListView, SwipeRefreshLayout.OnRefreshListener{

    public final static String PARAM_TYPE = "type";
    private int type;

    @Inject
    ExploreListPresenter _exploreListPresenter;

    @InjectView(R.id.explore_recycler_view)
    RecyclerView _recyclerView;

    @InjectView(R.id.explore_swipe_refresh_layout)
    SwipeRefreshLayout _swipeRefreshLayout;

    private ExploreListAdapter _exploreListAdapter;

    public ExploreListFragment() {
        // Required empty public constructor
    }

    public static ExploreListFragment getInstance(int position){
        ExploreListFragment exploreListFragment = new ExploreListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ExploreListFragment.PARAM_TYPE,position);
        exploreListFragment.setArguments(bundle);
        return exploreListFragment;
    }

    @Override
    protected List<Object> getModules(){return Arrays.<Object>asList(new ExploreListModule(this));}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //_exploreListPresenter.loadingExploreItems();
        type = getArguments().getInt(PARAM_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_list,container,false);
        ButterKnife.inject(this,rootView);

        _swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        _swipeRefreshLayout.setOnRefreshListener(this);

        _exploreListAdapter = new ExploreListAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        _recyclerView.setLayoutManager(linearLayoutManager);
        _recyclerView.setAdapter(_exploreListAdapter);

        _recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        _exploreListPresenter.loadingExploreItems(type);
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
        _exploreListAdapter.updateData(items);
    }


    @Override
    public void onRefresh() {
        _exploreListPresenter.loadingExploreItems(type);
    }
}
