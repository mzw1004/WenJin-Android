package com.twt.service.wenjin.ui.search.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.SearchDetailQuestion;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/16.
 */
public class SearchQuestionFragment extends Fragment implements OnItemClickListener {

    @Bind(R.id.search_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SearchQuestionAdapter mSearchQuestionAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public SearchQuestionFragment(){

    }

    public static SearchQuestionFragment getInstance(){
        SearchQuestionFragment searchQuestionFragment = new SearchQuestionFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("questions",questions);
        return searchQuestionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_list,container,false);
        ButterKnife.bind(this,rootView);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));

        mSearchQuestionAdapter = new SearchQuestionAdapter(getActivity(),this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mSearchQuestionAdapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClicked(View view, int position) {

    }

    public void showFooter() {
        mSearchQuestionAdapter.setUseFooter(true);
    }

    public void hideFooter() {
        mSearchQuestionAdapter.setUseFooter(false);
    }

    public void addListData(List<SearchDetailQuestion> items){
        mSearchQuestionAdapter.addData(items);
    }

    public void updateListData(List<SearchDetailQuestion> items){
        mSearchQuestionAdapter.updateData(items);
    }




}
