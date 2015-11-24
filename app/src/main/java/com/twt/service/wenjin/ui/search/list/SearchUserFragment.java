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
import com.twt.service.wenjin.bean.SearchUser;
import com.twt.service.wenjin.interactor.SearchInteractor;
import com.twt.service.wenjin.interactor.SearchInteractorImpl;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.search.OnGetSearchCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/16.
 */
public class SearchUserFragment extends Fragment implements OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,OnGetSearchCallback {
    private static final String LOG_TAG = SearchQuestionFragment.class.getSimpleName();

    @Bind(R.id.search_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.tv_search_noinfo)
    TextView tvNoinfo;

    private SearchUserAdapter mSearchUserAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SearchInteractor mSearchInteractor;

    ArrayList<SearchUser> mUsers = null;

    String mKeyword="";
    String mQueryType = "users";
    int mPage = 0;

    public SearchUserFragment(){

    }

    public static SearchUserFragment getInstance(){
        SearchUserFragment searchUserFragment = new SearchUserFragment();
        return searchUserFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_list,container,false);
        ButterKnife.bind(this, rootView);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSearchUserAdapter = new SearchUserAdapter(getActivity(),this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchInteractor = new SearchInteractorImpl();
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mSearchUserAdapter);
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
        SearchUser user = mSearchUserAdapter.getItem(position);
        ProfileActivity.actionStart(getActivity(), user.search_id);
    }


    public void showFooter() {
        mSearchUserAdapter.setUseFooter(true);
    }

    public void hideFooter() {
        mSearchUserAdapter.setUseFooter(false);
    }

    public void addListData(List<SearchUser> items){
        mSearchUserAdapter.addData(items);
    }

    public void updateListData(List<SearchUser> items){
        mSearchUserAdapter.updateData(items);
    }


    public void setKeyword(String keyword){
        this.mKeyword = keyword;
    }

    public void loadItems(int page){
        if(TextUtils.isEmpty(mKeyword)){return;}
        if(!mSwipeRefreshLayout.isRefreshing()) {
            showFooter();
            if(tvNoinfo.getVisibility() == View.VISIBLE) {
                tvNoinfo.setVisibility(View.GONE);
            }
        }
        mPage = page + 1;
        mSearchInteractor.searchContent(mKeyword,mQueryType, mPage, this);

    }


    @Override
    public void OnGetSearchSuccess(Object object) {
        hideFooter();
        if(mSwipeRefreshLayout.isRefreshing()){mSwipeRefreshLayout.setRefreshing(false);}
        mUsers = (ArrayList<SearchUser>)object;
        if(mPage == 1){
            if(mUsers.size() > 0){
                tvNoinfo.setVisibility(View.GONE);
            }else{
                tvNoinfo.setVisibility(View.VISIBLE);
            }
            updateListData(mUsers);
        }else{
            addListData(mUsers);
        }
    }

    @Override
    public void OnGetSearchFailure(String errorString) {
        hideFooter();
        if(mSwipeRefreshLayout.isRefreshing()){mSwipeRefreshLayout.setRefreshing(false);}
        Toast.makeText(getActivity(), errorString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        loadItems(0);
    }
}
