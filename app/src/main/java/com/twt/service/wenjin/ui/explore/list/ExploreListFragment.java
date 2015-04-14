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
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseFragment;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExploreListFragment extends BaseFragment implements ExploreListView,
        SwipeRefreshLayout.OnRefreshListener,OnItemClickListener{

    public final static String LOG_TAG = ExploreListFragment.class.getSimpleName();

    public final static String PARAM_TYPE = "type";
    private int type;

    @Inject
    ExploreListPresenter _exploreListPresenter;

    @InjectView(R.id.explore_recycler_view)
    RecyclerView _recyclerView;

    @InjectView(R.id.explore_swipe_refresh_layout)
    SwipeRefreshLayout _swipeRefreshLayout;

    private ExploreListAdapter _exploreListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean _isFirstTimeVisibleToUser = true;

    public ExploreListFragment() {
        // Required empty public constructor
    }

    public static ExploreListFragment getInstance(int position){
        ExploreListFragment exploreListFragment = new ExploreListFragment();
        LogHelper.v(LOG_TAG,"new ExploreFragment:"+position);
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
        type = getArguments().getInt(PARAM_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_list,container,false);
        LogHelper.v(LOG_TAG,"new ButterKnife injected");
        ButterKnife.inject(this,rootView);

        _swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        _swipeRefreshLayout.setOnRefreshListener(this);

        _exploreListAdapter = new ExploreListAdapter(getActivity(),this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        _recyclerView.setLayoutManager(linearLayoutManager);
        _recyclerView.setAdapter(_exploreListAdapter);

        _recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if( (lastPosition == linearLayoutManager.getItemCount() - 1 ) && dy > 0 ){
                    _exploreListPresenter.loadMoreExploreItems(type);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        _exploreListPresenter.loadExploreItems(type);
    }


/*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(_recyclerView == null){return;}
        if(isVisibleToUser){
            //if(_isFirstTimeVisibleToUser){ _exploreListPresenter.loadExploreItems(type);}
            //_isFirstTimeVisibleToUser = false;

            _recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if( (lastPosition == linearLayoutManager.getItemCount() - 1 ) && dy > 0 ){
                        _exploreListPresenter.loadMoreExploreItems(type);
                    }
                }
            });

        }else{
            _recyclerView.setOnScrollListener(null);
        }
        LogHelper.v(LOG_TAG,type+"is" +isVisibleToUser);

    }
*/


    @Override
    public void startRefresh() {
        if(!_swipeRefreshLayout.isRefreshing()){
            _swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopRefresh() {
        if(_swipeRefreshLayout.isRefreshing()){
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
    public void addListData(ArrayList<ExploreItem> items) {
        _exploreListAdapter.addData(items);
    }

    @Override
    public void showFooter() {
        _exploreListAdapter.setUseFooter(true);
    }

    @Override
    public void hideFooter() {
        _exploreListAdapter.setUseFooter(false);
    }


    @Override
    public void onRefresh() {
       _exploreListPresenter.loadExploreItems(type);
    }

    @Override
    public void onItemClicked(View view, int position) {

    }
}
