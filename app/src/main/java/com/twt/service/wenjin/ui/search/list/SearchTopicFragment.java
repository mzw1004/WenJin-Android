package com.twt.service.wenjin.ui.search.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twt.service.wenjin.ui.common.OnItemClickListener;

/**
 * Created by Green on 15/11/16.
 */
public class SearchTopicFragment extends Fragment implements OnItemClickListener {
    private SearchTopicAdapter mSearchTopicAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public SearchTopicFragment(){

    }

    public static SearchTopicFragment getInstance(){
        SearchTopicFragment searchTopicFragment = new SearchTopicFragment();
        return searchTopicFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onItemClicked(View view, int position) {

    }
}
