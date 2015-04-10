package com.twt.service.wenjin.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.NumberTextView;

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
    @InjectView(R.id.iv_profile_avatar)
    ImageView ivAvatar;
    @InjectView(R.id.tv_profile_username)
    TextView tvUsername;
    @InjectView(R.id.tv_profile_signature)
    TextView tvSignature;
    @InjectView(R.id.tv_profile_agree_number)
    TextView tvAgreeNumber;
    @InjectView(R.id.tv_profile_favorite_number)
    TextView tvFavoriteNumber;
    @InjectView(R.id.ntv_profile_focus_number)
    NumberTextView ntvFocus;
    @InjectView(R.id.ntv_profile_friends_number)
    NumberTextView ntvFriends;
    @InjectView(R.id.ntv_profile_fans_number)
    NumberTextView ntvFans;

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
        if (userInfo.avatar_file != null) {
            Picasso.with(this).load(ApiClient.getAvatarUrl(userInfo.avatar_file)).into(ivAvatar);
        }
        tvUsername.setText(userInfo.user_name);
        tvSignature.setText(userInfo.signature);
        ntvFocus.setNumber(Integer.parseInt(userInfo.topic_focus_count));
        ntvFriends.setNumber(Integer.parseInt(userInfo.friend_count));
        ntvFans.setNumber(Integer.parseInt(userInfo.fans_count));
        tvAgreeNumber.setText(userInfo.agree_count);
        tvFavoriteNumber.setText(userInfo.answer_favorite_count);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
