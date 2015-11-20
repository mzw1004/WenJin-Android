package com.twt.service.wenjin.ui.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.search.list.SearchArticleFragment;
import com.twt.service.wenjin.ui.search.list.SearchQuestionFragment;
import com.twt.service.wenjin.ui.search.list.SearchTopicFragment;
import com.twt.service.wenjin.ui.search.list.SearchUserFragment;

import java.util.ArrayList;

/**
 * Created by Green on 15/11/16.
 */
public class SearchFragmentAdapter extends FragmentPagerAdapter {

    private final String[] SEARCH_TAB_TITLES = ResourceHelper.getStringArrays(R.array.search_tab_titles);


    private ArrayList<Fragment> mFragments = null;
    private FragmentManager fm;
    private String mKeyword = "";

    public SearchFragmentAdapter(FragmentManager fm){
        super(fm);
        this.fm = fm;
        updateFragments();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return SEARCH_TAB_TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);
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
        Object fragment = super.instantiateItem(container, position);
        switch (position){
            case 0:
                ((SearchQuestionFragment)fragment).setKeyword(mKeyword);
                break;
            case 1:
                ((SearchArticleFragment)fragment).setKeyword(mKeyword);
                break;
            case 2:
                ((SearchTopicFragment)fragment).setKeyword(mKeyword);
                break;
            case 3:
                ((SearchUserFragment)fragment).setKeyword(mKeyword);
                break;
            default:
                return null;
        }

        return fragment;
    }

    private void updateFragments(){
        if(mFragments == null){
            mFragments = new ArrayList<>();
        }else {
            mFragments.clear();
        }
        mFragments.add(SearchQuestionFragment.getInstance());
        mFragments.add(SearchArticleFragment.getInstance());
        mFragments.add(SearchTopicFragment.getInstance());
        mFragments.add(SearchUserFragment.getInstance());
        notifyDataSetChanged();
    }

    public void updateKeyword(String keyword){
        mKeyword = keyword;
        updateFragments();
    }

}
