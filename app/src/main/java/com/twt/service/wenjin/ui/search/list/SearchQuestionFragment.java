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
import com.twt.service.wenjin.interactor.SearchInteractor;
import com.twt.service.wenjin.interactor.SearchInteractorImpl;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.question.QuestionActivity;
import com.twt.service.wenjin.ui.search.OnGetSearchCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/16.
 */
public class SearchQuestionFragment extends Fragment implements OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,OnGetSearchCallback {

    private static final String LOG_TAG = SearchQuestionFragment.class.getSimpleName();

    @Bind(R.id.search_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.tv_search_noinfo)
    TextView tvNoinfo;

    private SearchQuestionAdapter mSearchQuestionAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SearchInteractor mSearchInteractor;

    ArrayList<SearchQuestion> mQuestions = null;

    String mKeyword="";
    String mQueryType = "questions";
    int mPage = 0;

    boolean mIsRefresh = false;

    public SearchQuestionFragment(){

    }

    public static SearchQuestionFragment getInstance(){
        SearchQuestionFragment searchQuestionFragment = new SearchQuestionFragment();
        return searchQuestionFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_list,container,false);
        ButterKnife.bind(this, rootView);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSearchQuestionAdapter = new SearchQuestionAdapter(getActivity(),this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchInteractor = new SearchInteractorImpl();
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mSearchQuestionAdapter);
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
        SearchQuestion question = mSearchQuestionAdapter.getItem(position);
        QuestionActivity.actionStart(getActivity(), question.search_id);
    }

    public void showFooter() {
        mSearchQuestionAdapter.setUseFooter(true);
    }

    public void hideFooter() {
        mSearchQuestionAdapter.setUseFooter(false);
    }

    public void addListData(List<SearchQuestion> items){
        mSearchQuestionAdapter.addData(items);
    }

    public void updateListData(List<SearchQuestion> items){
        mSearchQuestionAdapter.updateData(items);
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
        mSearchInteractor.searchContent(mKeyword,mQueryType,mPage,this);

    }


    @Override
    public void OnGetSearchSuccess(Object object) {
        mIsRefresh = false;
        hideFooter();
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mQuestions = (ArrayList<SearchQuestion>)object;
        if(mPage == 1){
            if(mQuestions.size() > 0){
                tvNoinfo.setVisibility(View.GONE);
            }else{
                tvNoinfo.setVisibility(View.VISIBLE);
            }
            updateListData(mQuestions);
        }else{
            addListData(mQuestions);
        }
    }

    @Override
    public void OnGetSearchFailure(String errorString) {
        mIsRefresh = false;
        hideFooter();
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(),errorString,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        LogHelper.v(LOG_TAG, "onRefresh loadItems(0)");
        if(mIsRefresh){
            return;
        }else{
            mIsRefresh = true;
        }
        loadItems(0);
    }
}
