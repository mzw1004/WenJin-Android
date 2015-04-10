package com.twt.service.wenjin.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.ui.BaseActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends BaseActivity implements ProfileView {

    private static final String LOG_TAG = ProfileActivity.class.getSimpleName();

    private static final String PARM_USER_ID = "user_id";

    @Inject
    ProfilePresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private int uid;

    public static void actionStart(Context context, int uid) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(PARM_USER_ID, uid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uid = getIntent().getIntExtra(PARM_USER_ID, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getUserInfo(uid);
    }

    @Override
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new ProfileModule(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bindUserInfo(UserInfo userInfo) {

    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
