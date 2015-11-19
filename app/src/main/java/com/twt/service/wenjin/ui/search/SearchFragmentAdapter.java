package com.twt.service.wenjin.ui.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.SearchDetailQuestion;
import com.twt.service.wenjin.bean.SearchResponse;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.explore.list.ExploreListFragment;
import com.twt.service.wenjin.ui.search.list.SearchArticleFragment;
import com.twt.service.wenjin.ui.search.list.SearchQuestionFragment;
import com.twt.service.wenjin.ui.search.list.SearchTopicFragment;
import com.twt.service.wenjin.ui.search.list.SearchUserFragment;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Green on 15/11/16.
 */
public class SearchFragmentAdapter extends FragmentPagerAdapter {

    private final String[] SEARCH_TAB_TITLES = ResourceHelper.getStringArrays(R.array.search_tab_titles);

    private SearchResponse mResponse = null;

    private ArrayList<Fragment> mFragments = null;
    private FragmentManager fm;

    public SearchFragmentAdapter(FragmentManager fm,ArrayList<Fragment> fragments){
        super(fm);
        this.fm = fm;
        this.mFragments = fragments;
//        mResponse = response;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return SEARCH_TAB_TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        /*
        switch (position){
            case 0:
                SearchQuestionFragment fragment;
                if(mResponse == null)
                    fragment =  SearchQuestionFragment.getInstance(null);
                else
                    fragment =  SearchQuestionFragment.getInstance(mResponse.questions);
                return fragment;
            case 1:
                SearchQuestionFragment fragment2;
                if(mResponse == null)
                    fragment2 =  SearchQuestionFragment.getInstance(null);
                else
                    fragment2 =  SearchQuestionFragment.getInstance(mResponse.questions);
                return fragment2;
//                return SearchArticleFragment.getInstance();
            case 2:
//                return SearchTopicFragment.getInstance();
            case 3:
//                return SearchUserFragment.getInstance();
            default:
                return null;
        }
        */
        if(mResponse == null){
            return SearchQuestionFragment.getInstance(null);
        }else {
            return SearchQuestionFragment.getInstance(mResponse.questions);
        }
    }

    @Override
    public int getCount() {
        return SEARCH_TAB_TITLES.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SearchQuestionFragment fragment = (SearchQuestionFragment)super.instantiateItem(container, position);
        if(mResponse != null && mResponse.questions != null) {
            fragment.updateData(mResponse.questions);
        }
        return fragment;
    }

    public void setFragments(ArrayList<Fragment> fragments){
        Iterator<Fragment> iterator = mFragments.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        mFragments.addAll(fragments);
        notifyDataSetChanged();
    }

    public void updateData(SearchResponse response){
        mResponse = response;
        notifyDataSetChanged();
    }

}
