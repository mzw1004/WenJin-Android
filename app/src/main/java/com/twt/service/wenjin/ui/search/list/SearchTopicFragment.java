package com.twt.service.wenjin.ui.search.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.SearchQuestion;
import com.twt.service.wenjin.bean.SearchTopic;
import com.twt.service.wenjin.interactor.SearchInteractor;
import com.twt.service.wenjin.interactor.SearchInteractorImpl;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.search.OnGetSearchCallback;
import com.twt.service.wenjin.ui.topic.detail.TopicDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/16.
 */
public class SearchTopicFragment extends Fragment implements OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,OnGetSearchCallback {

    private static final String LOG_TAG = SearchTopicFragment.class.getSimpleName();

    @Bind(R.id.search_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.tv_search_noinfo)
    TextView tvNoinfo;

    private SearchTopicAdapter mSearchTopicAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SearchInteractor mSearchInteractor;

    ArrayList<SearchTopic> mTopics = null;

    String mKeyword="";
    String mQueryType ="topics";
    int mPage = 0;

    public SearchTopicFragment(){

    }

    public static SearchTopicFragment getInstance(){
        SearchTopicFragment searchTopicFragment = new SearchTopicFragment();
        return searchTopicFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_list,container,false);
        ButterKnife.bind(this, rootView);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSearchTopicAdapter = new SearchTopicAdapter(getContext(),this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchInteractor = new SearchInteractorImpl();
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mSearchTopicAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if( (lastPosition == mLinearLayoutManager.getItemCount() - 1 ) && dy > 0 ){
                    loadItems(mPage);
                }
            }
        });
        loadItems(0);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClicked(View view, int position) {
        SearchTopic topic = mSearchTopicAdapter.getItem(position);
        TopicDetailActivity.actionStart(getActivity(), topic.search_id, topic.name);
    }

    @Override
    public void OnGetSearchSuccess(Object object) {
        hideFooter();
        if(mSwipeRefreshLayout.isRefreshing()){mSwipeRefreshLayout.setRefreshing(false);}
        mTopics = (ArrayList<SearchTopic>)object;
        if(mPage == 1){
            if(mTopics.size() > 0){
                tvNoinfo.setVisibility(View.GONE);
            }else{
                tvNoinfo.setVisibility(View.VISIBLE);
            }
            updateListData(mTopics);
        }else{
            addListData(mTopics);
        }
    }

    @Override
    public void OnGetSearchFailure(String errorString) {
        hideFooter();
        if(mSwipeRefreshLayout.isRefreshing()){mSwipeRefreshLayout.setRefreshing(false);}
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_LONG).show();
    }


    public void showFooter() {
        mSearchTopicAdapter.setUseFooter(true);
    }

    public void hideFooter() {
        mSearchTopicAdapter.setUseFooter(false);
    }

    public void addListData(List<SearchTopic> items){
        mSearchTopicAdapter.addData(items);
    }

    public void updateListData(List<SearchTopic> items){
        mSearchTopicAdapter.updateData(items);
    }


    public void setKeyword(String keyword){
        this.mKeyword = keyword;
    }

    public void loadItems(int page){
        if(TextUtils.isEmpty(mKeyword)){return;}
        if(!mSwipeRefreshLayout.isRefreshing()) {
            showFooter();
        }
        mPage = page + 1;
        mSearchInteractor.searchContent(mKeyword,mQueryType, mPage, this);

    }

    @Override
    public void onRefresh() {
        loadItems(0);
    }
}
