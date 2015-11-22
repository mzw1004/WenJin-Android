package com.twt.service.wenjin.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.otto.Subscribe;
import com.twt.service.wenjin.BuildConfig;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.WenJinApp;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.NotificationNumInfo;
import com.twt.service.wenjin.event.DrawerItemClickedEvent;
import com.twt.service.wenjin.interactor.NotificationInteractor;
import com.twt.service.wenjin.interactor.NotificationInteractorImpl;
import com.twt.service.wenjin.receiver.JPushNotifiInMainReceiver;
import com.twt.service.wenjin.receiver.NotificationBuffer;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.UpdateDialogFragment;
import com.twt.service.wenjin.ui.draft.DraftFragment;
import com.twt.service.wenjin.ui.explore.ExploreFragment;
import com.twt.service.wenjin.ui.feedback.FeedbackActivity;
import com.twt.service.wenjin.ui.home.HomeFragment;
import com.twt.service.wenjin.ui.login.LoginActivity;
import com.twt.service.wenjin.ui.notification.NotificationMainFragment;
import com.twt.service.wenjin.ui.notification.readlist.NotificationFragment;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.publish.PublishActivity;
import com.twt.service.wenjin.ui.search.SearchActivity;
import com.twt.service.wenjin.ui.setting.SettingsActivity;
import com.twt.service.wenjin.ui.topic.TopicFragment;

import butterknife.Bind;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


public class MainActivity extends BaseActivity implements MainView,OnGetNotificationNumberInfoCallback,
        NotificationFragment.IUpdateNotificationIcon {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String[] DRAWER_TITLES = ResourceHelper.getStringArrays(R.array.drawer_list_items);

    @Inject
    MainPresenter mMainPresenter;

//    @Bind(R.id.navigation_drawer_layout)
//    DrawerLayout mDrawerLayout;
//    @Bind(R.id.nv_main_navigation)
//    NavigationView mNavigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private AccountHeader mHeaderResult = null;
    private Drawer mResult = null;


    private HomeFragment mHomeFragment;
    private ExploreFragment mExploreFragment;
    private TopicFragment mTopicFragment;
    private NotificationMainFragment mNotificationMainFragment;
    private DraftFragment mDraftFragment;
//    private UserFragment mUserFragment;
    private Context mContext;

    private JPushNotifiInMainReceiver mReceiver;

    private int mBadgeCount = 0;
    private long exitTime = 0;

    private NotificationInteractor notificationInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        WenJinApp.setAppLunchState(true);

        setSupportActionBar(toolbar);
//        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_drawer_menu);
//        ab.setDisplayHomeAsUpEnabled(true);

        initialDrawer(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new HomeFragment())
                .commit();
        setMainTitle(0);


        mReceiver = new JPushNotifiInMainReceiver(this);
        registerReceiver(mReceiver, JPushNotifiInMainReceiver.getIntentFilterInstance());

        if(NotificationBuffer.getsIntent() != null){
            Intent intent = NotificationBuffer.getsIntent();
            intent.setClass(this, (Class) NotificationBuffer.getObjClass());
            this.startActivity(intent);
            NotificationBuffer.setsIntent(null);
        }


        mContext = this;
        notificationInteractor = new NotificationInteractorImpl();

        ApiClient.checkNewVersion(BuildConfig.VERSION_CODE + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String isNew = response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONObject("info").getString("is_new");
                    if (isNew.equals("1")) {
                        String url = response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONObject("info").getString("url");
                        String description = response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONObject("info").getString("description");
                        UpdateDialogFragment.newInstance(url, description).show(MainActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialDrawer(Bundle savedInstanceState){
        final IProfile profile = new ProfileDrawerItem().withName(PrefUtils.getPrefUsername()).withEmail(PrefUtils.getPrefSignature()).withIcon(Uri.parse(ApiClient.getAvatarUrl(PrefUtils.getPrefAvatarFile()))).withIdentifier(100);
        mHeaderResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.guide_background)
                .withSelectionListEnabled(false)
                .addProfiles(profile)
                .withOnlyMainProfileImageVisible(true)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        ProfileActivity.actionStart(mContext, PrefUtils.getPrefUid());
                        return false;
                    }
                })
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        mResult.closeDrawer();
                        ProfileActivity.actionStart(mContext, PrefUtils.getPrefUid());
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
        mResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withStatusBarColor(ResourceHelper.getColor(R.color.color_accent_dark))
                .withAccountHeader(mHeaderResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(R.drawable.ic_drawer_home_grey).withIdentifier(1).withSelectable(true).withSelectedTextColor(ResourceHelper.getColor(R.color.color_primary))
                        , new PrimaryDrawerItem().withName(R.string.drawer_item_explore).withIcon(R.drawable.ic_drawer_explore_grey).withIdentifier(2).withSelectable(true).withSelectedTextColor(ResourceHelper.getColor(R.color.color_primary))
                        , new PrimaryDrawerItem().withName(R.string.drawer_item_topic).withIcon(R.drawable.ic_drawer_topic_grey).withIdentifier(3).withSelectable(true).withSelectedTextColor(ResourceHelper.getColor(R.color.color_primary))
                        , new PrimaryDrawerItem().withName(R.string.drawer_item_draft).withIcon(R.drawable.ic_drawer_draft_grey).withIdentifier(4).withSelectable(true).withSelectedTextColor(ResourceHelper.getColor(R.color.color_primary))
                        , new PrimaryDrawerItem().withName(R.string.drawer_item_notification).withIcon(R.drawable.ic_drawer_notifications).withIdentifier(5).withSelectable(true).withSelectedTextColor(ResourceHelper.getColor(R.color.color_primary)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))
                        , new SectionDrawerItem().withName(R.string.drawer_divider_system)
                        , new SecondaryDrawerItem().withName(R.string.drawer_item_setting).withIcon(R.drawable.ic_drawer_settings_grey).withIdentifier(21).withSelectable(false)
                        , new SecondaryDrawerItem().withName(R.string.drawer_item_helper_and_feedback).withIcon(R.drawable.ic_drawer_help_grey).withIdentifier(22).withSelectable(false)
                        , new SecondaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(R.drawable.ic_drawer_logout_grey).withIdentifier(23).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                   @Override
                                                   public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                                       if (drawerItem != null) {
                                                           if (drawerItem.getIdentifier() < 20) {
                                                               replaceFragment(drawerItem.getIdentifier());
                                                               setMainTitle(drawerItem.getIdentifier() - 1);
                                                           }else{
                                                               mMainPresenter.selectItem(drawerItem.getIdentifier());
                                                           }
                                                       }
                                                       return false;
                                                   }
                                               }

                )
                            .withSavedInstance(savedInstanceState)

                    .withShowDrawerOnFirstLaunch(true)
                .build();
        if(savedInstanceState == null){
            mResult.setSelection(1,false);
            mHeaderResult.setActiveProfile(profile);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search:
                SearchActivity.actionStart(this);
                return true;
            case R.id.action_create_question:
                startActivity(new Intent(this, PublishActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getBusInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getBusInstance().unregister(this);
        ApiClient.getInstance().cancelRequests(this, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(JPushInterface.isPushStopped(this) && PrefUtils.isLaunchNotification()){
            JPushInterface.onResume(this);
        }
        updateNotificationIcon();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //JPushInterface.onPause(this);
    }

    @Subscribe
    public void OnDrawerItemClicked(DrawerItemClickedEvent event) {
        LogHelper.v(LOG_TAG, "clicked position: " + event.getPosition());
        mMainPresenter.onNavigationDrawerItemSelected(event.getPosition());
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
    }

    @Override
    public void replaceFragment(int position) {
        LogHelper.v(LOG_TAG, "switch to: " + position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case 1:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                fragment = mHomeFragment;
                break;
            case 2:
                if (mExploreFragment == null) {
                    mExploreFragment = new ExploreFragment();
                }
                fragment = mExploreFragment;
                break;
            case 3:
                if (mTopicFragment == null) {
                    mTopicFragment = new TopicFragment();
                }
                fragment = mTopicFragment;
                break;
            case 4:
                if (mDraftFragment == null) {
                    mDraftFragment = new DraftFragment();
                }
                fragment = mDraftFragment;
                break;
            case 5:
                if (mNotificationMainFragment == null) {
                    mNotificationMainFragment = new NotificationMainFragment();
                }
                fragment = mNotificationMainFragment;
                break;
        }
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void setMainTitle(int position) {
        getSupportActionBar().setTitle(DRAWER_TITLES[position]);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mResult.isDrawerOpen()){
            mResult.closeDrawer();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, getString(R.string.quit), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WenJinApp.setAppLunchState(false);
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onGetNotificationNumberInfoSuccess(NotificationNumInfo notificationNumInfo) {
        mBadgeCount = notificationNumInfo.notifications_num;
        if(mBadgeCount > 0){
            mResult.updateBadge(5, new StringHolder(mBadgeCount + ""));
            mResult.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer_menu);
        }else {
            mResult.updateBadge(5,null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mResult.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        }
    }

    @Override
    public void onGetNotificationNumberInfoFailed(String argErrorMSG) {

    }

    @Override
    public void updateNotificationIcon() {
        notificationInteractor.getNotificationNumberInfo(Calendar.getInstance().getTimeInMillis(), this);
    }

    @Override
    public void startSettingsActivity() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void startFeedbackActivity() {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }

    @Override
    public void sendDrawerItemClickedEvent(int position) {
        BusProvider.getBusInstance().post(new DrawerItemClickedEvent(position));
    }

}
