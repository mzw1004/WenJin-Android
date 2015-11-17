package com.twt.service.wenjin.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/12.
 */
public class SearchActivity extends BaseActivity implements SearchView{



    @Bind(R.id.tabs_search)
    PagerSlidingTabStrip mPagerSlidingTabStrip;

    @Bind(R.id.viewpager_search)
    ViewPager mViewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SearchPresenter mPresenter;

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

        FragmentPagerAdapter adapter = new SearchFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
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
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new SearchModule(this));
    }
}
