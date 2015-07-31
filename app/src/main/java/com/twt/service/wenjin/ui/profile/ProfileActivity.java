package com.twt.service.wenjin.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Follows;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.NumberTextView;
import com.twt.service.wenjin.ui.profile.askanswer.ProfileAskanswerActivity;
import com.twt.service.wenjin.ui.profile.edit.ProfileEditActivity;
import com.twt.service.wenjin.ui.profile.follows.FollowsActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

public class ProfileActivity extends BaseActivity implements ProfileView, OnClickListener {

    private static final String LOG_TAG = ProfileActivity.class.getSimpleName();

    private static final String PARM_USER_ID = "user_id";
    private static final String ACTION_TYPE_ASK = "ask";
    private static final String ACTION_TYPE_ANSWER = "answer";

    private static final String ACTION_TYPE_FOLLOWING = "following";
    private static final String ACTION_TYPE_FOLLOWERS = "followers";

    @Inject
    ProfilePresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    public static ImageView ivAvatar;
    public static TextView tvUsername;
    public static TextView tvSignature;
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
    @InjectView(R.id.bt_profile_focus)
    Button btFocus;
    @InjectView(R.id.tv_profile_ask)
    TextView tvAsk;
    @InjectView(R.id.tv_profile_answer)
    TextView tvAnswer;
    @InjectView(R.id.profile_follow_loading)
    ProgressBar pbProfileFollowLoading;

    private int uid;
    private UserInfo _userInfo;

    public static void actionStart(Context context, int uid) {
        if(uid == -1){
            return;
        }
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(PARM_USER_ID, uid);
        context.startActivity(intent);
    }

//    @OnClick(R.id.tv_profile_ask)
//    public void startAskActivity() {
//        ProfileAskanswerActivity.anctionStart(this, ACTION_TYPE_ASK, uid, _userInfo.nick_name, _userInfo.avatar_file);
//    }
//
//    @OnClick(R.id.tv_profile_answer)
//    public void startAnswerActivity() {
//        ProfileAskanswerActivity.anctionStart(this, ACTION_TYPE_ANSWER, uid, _userInfo.nick_name, _userInfo.avatar_file);
//    }
//
//    @OnClick(R.id.ntv_profile_friends_number)
//    public void startFollowersActivity(){
//        FollowsActivity.actionStart(this,ACTION_TYPE_FOLLOWING,uid);
//    }
//
//    @OnClick(R.id.ntv_profile_fans_number)
//    public void startFollowingActivity(){
//        FollowsActivity.actionStart(this,ACTION_TYPE_FOLLOWERS,uid);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);

        tvUsername = (TextView)findViewById(R.id.tv_profile_username);
        tvSignature = (TextView)findViewById(R.id.tv_profile_signature);
        ivAvatar = (CircleImageView)findViewById(R.id.iv_profile_avatar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uid = getIntent().getIntExtra(PARM_USER_ID, 0);
        mPresenter.getUserInfo(uid);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ProfileModule(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (uid == PrefUtils.getPrefUid()) {
            getMenuInflater().inflate(R.menu.menu_profile, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_edit:
                ProfileEditActivity.actionStart(this, uid);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bindUserInfo(UserInfo userInfo) {
        _userInfo = userInfo;
        if (!TextUtils.isEmpty(userInfo.avatar_file)) {
            Picasso.with(this).load(ApiClient.getAvatarUrl(userInfo.avatar_file)).skipMemoryCache().into(ivAvatar);
        }
        tvUsername.setText(userInfo.nick_name);
        tvSignature.setText(userInfo.signature);
        if(userInfo.topic_focus_count != null) {
            ntvFocus.setNumber(Integer.parseInt(userInfo.topic_focus_count));
        }
        if(userInfo.friend_count != null) {
            ntvFriends.setNumber(Integer.parseInt(userInfo.friend_count));
        }
        if(userInfo.fans_count != null) {
            ntvFans.setNumber(Integer.parseInt(userInfo.fans_count));
        }
        tvAgreeNumber.setText(userInfo.agree_count);
        tvFavoriteNumber.setText(userInfo.answer_favorite_count);
        if (uid != PrefUtils.getPrefUid()) {
            if (userInfo.has_focus == 1) {
                addFocus();
            } else {
                removeFocus();
            }
            btFocus.setVisibility(View.VISIBLE);
        }
        btFocus.setOnClickListener(this);
        tvAsk.setOnClickListener(this);
        tvAnswer.setOnClickListener(this);
        ntvFans.setOnClickListener(this);
        ntvFriends.setOnClickListener(this);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addFocus() {
        btFocus.setBackgroundResource(R.drawable.button_focused_background);
        btFocus.setText(getString(R.string.action_not_focus));
        btFocus.setTextColor(getResources().getColor(R.color.color_accent));
    }

    @Override
    public void removeFocus() {
        btFocus.setBackgroundResource(R.drawable.button_focus);
        btFocus.setText(getString(R.string.action_focus));
        btFocus.setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void showProgressBar() {
        if(pbProfileFollowLoading == null || pbProfileFollowLoading.getVisibility() == VISIBLE) return;
        pbProfileFollowLoading.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        if(pbProfileFollowLoading == null || pbProfileFollowLoading.getVisibility() == View.GONE) return;
        pbProfileFollowLoading.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_profile_focus:

                mPresenter.actionFocus(uid);
                break;
            case R.id.tv_profile_ask:
                ProfileAskanswerActivity.anctionStart(this, ACTION_TYPE_ASK, uid, _userInfo.nick_name, _userInfo.avatar_file);
                break;
            case R.id.tv_profile_answer:
                ProfileAskanswerActivity.anctionStart(this, ACTION_TYPE_ANSWER, uid, _userInfo.nick_name, _userInfo.avatar_file);
                break;
            case R.id.ntv_profile_friends_number:
                FollowsActivity.actionStart(this, ACTION_TYPE_FOLLOWING, uid);
                break;
            case R.id.ntv_profile_fans_number:
                FollowsActivity.actionStart(this, ACTION_TYPE_FOLLOWERS, uid);
                break;
        }
    }

}
