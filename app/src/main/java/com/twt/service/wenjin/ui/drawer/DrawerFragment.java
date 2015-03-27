package com.twt.service.wenjin.ui.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.event.DrawerItemClickedEvent;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.ui.BaseFragment;
import com.twt.service.wenjin.ui.login.LoginActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DrawerFragment extends BaseFragment implements DrawerView,
        DrawerAdapter.OnItemClickListener, DrawerAdapter.OnUserClickListener {

    private static final String LOG_TAG = DrawerFragment.class.getSimpleName();

    private static final String STATE_SELECTED_POSITION = "navigation_drawer_selected_position";

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    @Inject
    DrawerPresenter mPresenter;

    @InjectView(R.id.drawer_recycler_view)
    RecyclerView mDrawerRecyclerView;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mContainer;
    private DrawerAdapter mDrawerAdapter;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    public DrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        mPresenter.selectItem(mCurrentSelectedPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.inject(this, rootView);

        mDrawerAdapter = new DrawerAdapter(getActivity(), this, this);
        addDrawerItems();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDrawerRecyclerView.setLayoutManager(linearLayoutManager);
        mDrawerRecyclerView.setAdapter(mDrawerAdapter);

        updateUserInfo();

        return rootView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mContainer);
    }

    public void setUp(int framelayoutId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mContainer = getActivity().findViewById(framelayoutId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu();
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(Gravity.START);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems() {
        mDrawerAdapter.addHeader(R.layout.drawer_list_header);
        mDrawerAdapter.addItem(R.drawable.ic_drawer_home_grey, getString(R.string.drawer_item_home));
        mDrawerAdapter.addItem(R.drawable.ic_drawer_explore_grey, getString(R.string.drawer_item_explore));
        mDrawerAdapter.addItem(R.drawable.ic_drawer_topic_grey, getString(R.string.drawer_item_topic));
        mDrawerAdapter.addItem(R.drawable.ic_drawer_user_grey, getString(R.string.drawer_item_user));
        mDrawerAdapter.addDivider();
        mDrawerAdapter.addItem(R.drawable.ic_drawer_settings_grey, getString(R.string.drawer_item_setting));
        mDrawerAdapter.addItem(R.drawable.ic_drawer_help_grey, getString(R.string.drawer_item_helper_and_feedback));
        mDrawerAdapter.addDivider();
        mDrawerAdapter.addItem(R.drawable.ic_drawer_logout_grey, getString(R.string.drawer_item_logout));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getBusInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getBusInstance().unregister(this);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DrawerModule(this));
    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.selectItem(position);
    }

    @Override
    public void onUserClick(View view) {
        LogHelper.v(LOG_TAG, "user profile clicked");
//        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void updateUserInfo() {
        mDrawerAdapter.updateUserInfo();
    }

    @Override
    public void setSelectedItemColor(int position) {
        if (mDrawerAdapter != null) {
            mDrawerAdapter.setSelected(position);
        }
    }

    @Override
    public void sendDrawerItemClickedEvent(int position) {
        BusProvider.getBusInstance().post(new DrawerItemClickedEvent(position));
        LogHelper.d(LOG_TAG, "send event successfully");
    }

}
