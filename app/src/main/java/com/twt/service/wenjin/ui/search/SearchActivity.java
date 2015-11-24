package com.twt.service.wenjin.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseActivity;


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
    TabLayout mTabLayout;

    @Bind(R.id.viewpager_search)
    ViewPager mViewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SearchPresenter mPresenter;

    SearchFragmentAdapter fragmentPagerAdapter;
    android.support.v7.widget.SearchView mSearchView;
    boolean isFocused = false;

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

        fragmentPagerAdapter = new SearchFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        mSearchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menuItem);
        mSearchView.setQueryHint(getString(R.string.hint_action_search));
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new SearchModule(this));
    }


    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        LogHelper.v(LOG_TAG, "querystring:" + query);
        if(!TextUtils.isEmpty(query)) {
            fragmentPagerAdapter.updateKeyword(query);
            mSearchView.clearFocus();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
