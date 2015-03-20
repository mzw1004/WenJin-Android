package com.twt.service.wenjin.ui.main;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.drawer.DrawerFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements MainView {

    @InjectView(R.id.navigation_drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.main_container)
    FrameLayout mFrameLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private DrawerFragment mDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mDrawerFragment.setUp(R.id.main_container, mDrawerLayout, toolbar);
    }

    @Override
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new MainModule());
    }

}
