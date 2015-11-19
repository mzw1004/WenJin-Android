package com.twt.service.wenjin.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.SearchDetailQuestion;
import com.twt.service.wenjin.bean.SearchResponse;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.search.list.SearchQuestionFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/12.
 */
public class SearchActivity extends BaseActivity implements SearchView, android.support.v7.widget.SearchView.OnQueryTextListener{

    private static final String LOG_TAG = SearchActivity.class.getSimpleName();

    @Bind(R.id.tabs_search)
    PagerSlidingTabStrip mPagerSlidingTabStrip;

    @Bind(R.id.viewpager_search)
    ViewPager mViewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SearchPresenter mPresenter;

    SearchFragmentAdapter fragmentPagerAdapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(SearchQuestionFragment.getInstance(null));
        fragments.add(SearchQuestionFragment.getInstance(null));
        fragments.add(SearchQuestionFragment.getInstance(null));
        fragments.add(SearchQuestionFragment.getInstance(null));
        fragmentPagerAdapter = new SearchFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint(getString(R.string.hint_action_search));
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new SearchModule(this));
    }

    @Override
    public void bindSearchResultsToView(SearchResponse response) {
//        ArrayList<Fragment> fragments2 = new ArrayList<>();
//        fragments2.add(SearchQuestionFragment.getInstance(response.questions));
//        fragments2.add(SearchQuestionFragment.getInstance(response.questions));
//        fragments2.add(SearchQuestionFragment.getInstance(response.questions));
//        fragments2.add(SearchQuestionFragment.getInstance(response.questions));
////        fragmentPagerAdapter.setFragments(fragments2);
//        fragmentPagerAdapter.setFragments(fragments2);
        LogHelper.v(LOG_TAG, response.questions.size());
        for(SearchDetailQuestion q:response.questions){
            LogHelper.v(LOG_TAG, q.header.name+" "+q.answer_count+" "+q.focus_count);

        }
        fragmentPagerAdapter.updateData(response);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        LogHelper.v(LOG_TAG, "querystring:" + query);
        mPresenter.getSearchItems(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
