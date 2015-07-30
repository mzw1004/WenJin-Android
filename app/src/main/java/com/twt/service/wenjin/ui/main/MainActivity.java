package com.twt.service.wenjin.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
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
import com.twt.service.wenjin.support.BadgeView;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.UpdateDialogFragment;
import com.twt.service.wenjin.ui.draft.DraftFragment;
import com.twt.service.wenjin.ui.drawer.DrawerFragment;
import com.twt.service.wenjin.ui.explore.ExploreFragment;
import com.twt.service.wenjin.ui.home.HomeFragment;
import com.twt.service.wenjin.ui.notification.NotificationMainFragment;
import com.twt.service.wenjin.ui.notification.readlist.NotificationFragment;
import com.twt.service.wenjin.ui.topic.TopicFragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;


public class MainActivity extends BaseActivity implements MainView,OnGetNotificationNumberInfoCallback,
        NotificationFragment.IUpdateNotificationIcon,View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String[] DRAWER_TITLES = ResourceHelper.getStringArrays(R.array.drawer_list_items);

    @Inject
    MainPresenter mMainPresenter;

    @InjectView(R.id.navigation_drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private DrawerFragment mDrawerFragment;
    private HomeFragment mHomeFragment;
    private ExploreFragment mExploreFragment;
    private TopicFragment mTopicFragment;
    private NotificationMainFragment mNotificationMainFragment;
    private DraftFragment mDraftFragment;
//    private UserFragment mUserFragment;

    private JPushNotifiInMainReceiver mReceiver;

    private int mBadgeCount = 0;
    private long exitTime = 0;

    private NotificationInteractor notificationInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        WenJinApp.setAppLunchState(true);

        notificationInteractor = new NotificationInteractorImpl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mDrawerFragment.setUp(R.id.main_container, mDrawerLayout, toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        View menuNotification = menu.findItem(R.id.action_notification).getActionView();
        menuNotification.setOnClickListener(this);

        View notificationIcon = menuNotification.findViewById(R.id.iv_action_notification);
        if(mBadgeCount > 0){
            BadgeView badgeView = new BadgeView(this, notificationIcon);
            badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            badgeView.setBadgeBackgroundColor(ResourceHelper.getColor(R.color.color_button));
            badgeView.setTextSize(8);
            if(mBadgeCount > 99){
                badgeView.setText("99+");
            }else {
                badgeView.setText(String.valueOf(mBadgeCount));
            }
            badgeView.show();
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                fragment = mHomeFragment;
                break;
            case 1:
                if (mExploreFragment == null) {
                    mExploreFragment = new ExploreFragment();
                }
                fragment = mExploreFragment;
                break;
            case 2:
                if (mTopicFragment == null) {
                    mTopicFragment = new TopicFragment();
                }
                fragment = mTopicFragment;
                break;
            case 3:
                if (mDraftFragment == null) {
                    mDraftFragment = new DraftFragment();
                }
                fragment = mDraftFragment;
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
        invalidateOptionsMenu();

    }

    @Override
    public void onGetNotificationNumberInfoFailed(String argErrorMSG) {

    }

    @Override
    public void updateNotificationIcon() {
        notificationInteractor.getNotificationNumberInfo(Calendar.getInstance().getTimeInMillis(), this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.action_notification){
            FragmentManager fragmentManager = getSupportFragmentManager();
            mNotificationMainFragment = new NotificationMainFragment();
            fragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.main_container, mNotificationMainFragment, ResourceHelper.getString(R.string.action_notification))
                    .commit();

            getSupportActionBar().setTitle(R.string.action_notification);

        }
    }

    //    @Override
//    public void startNewActivity(int position) {
//        LogHelper.v(LOG_TAG, "start new activity: " + position);
//        switch (position) {
//            case 4:
//                LogHelper.v(LOG_TAG, "start setting activity");
//                break;
//            case 5:
//                LogHelper.v(LOG_TAG, "start help activity");
//                break;
//            case 6:
//                LogHelper.v(LOG_TAG, "user logout");
//                startActivity(new Intent(this, LoginActivity.class));
//                break;
//        }
//    }
}
