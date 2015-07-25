package com.twt.service.wenjin.ui.profile.edit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.UserInfo;
import com.twt.service.wenjin.event.SelectPhotoResultEvent;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.SelectPhotoDialogFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends BaseActivity implements ProfileEditView, View.OnClickListener {

    private static final String PARAM_UID = "uid";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.iv_profile_edit_avatar)
    CircleImageView ivProfileEditAvatar;
    @InjectView(R.id.edt_profile_edit_username)
    EditText edtProfileEditUsername;
    @InjectView(R.id.edt_profile_edit_description)
    EditText edtProfileEditDescription;
    @InjectView(R.id.pb_profile_edit)
    ProgressBar pbProfileEdit;

    @Inject
    ProfileEditPresenter profileEditPresenter;

    private int uid;
    private UserInfo _userInfo;


    public static void actionStart(Context context, int uid) {
        Intent intent = new Intent(context, ProfileEditActivity.class);
        intent.putExtra(PARAM_UID, uid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uid = getIntent().getIntExtra(PARAM_UID, 0);
        Log.e("uid", uid + "");
        if (profileEditPresenter == null) {
            Log.e("null", "null");
        }
        profileEditPresenter.getUserInfo(uid);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ProfileEditModule(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.submit:
                String nick_name = edtProfileEditUsername.getText().toString();
                String signature = edtProfileEditDescription.getText().toString();
                profileEditPresenter.postUserInfo(uid, nick_name, signature);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bindUserInfo(UserInfo userInfo) {
        _userInfo = userInfo;
        if (!TextUtils.isEmpty(userInfo.avatar_file)) {
            Picasso.with(this).load(ApiClient.getAvatarUrl(userInfo.avatar_file)).into(ivProfileEditAvatar);
        }
        edtProfileEditUsername.setText(userInfo.nick_name);
        edtProfileEditDescription.setText(userInfo.signature);
    }

    @Override
    public void showProgressBar() {
        pbProfileEdit.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        pbProfileEdit.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_profile_edit_avatar:
                new SelectPhotoDialogFragment().show(this);
                break;
        }
    }
}
