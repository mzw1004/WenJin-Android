package com.twt.service.wenjin.ui.profile.edit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.SelectPhotoDialogFragment;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends BaseActivity implements ProfileEditView {

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

    private ProgressDialog progressDialog;

    private int uid;
    private UserInfo _userInfo;
    private static String user_avatar;


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
        ivProfileEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectPhotoDialogFragment().show(ProfileEditActivity.this);
            }
        });
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
            Picasso.with(this).load(ApiClient.getAvatarUrl(userInfo.avatar_file)).skipMemoryCache().into(ivProfileEditAvatar);
        }
        edtProfileEditUsername.setText(userInfo.nick_name);
        edtProfileEditDescription.setText(userInfo.signature);
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
    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, null, null);
        progressDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showFailureDialog(String errorMsg) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(errorMsg);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }


    @Subscribe
    public void onSelectPhotoResult(SelectPhotoResultEvent event) {
        if (event.getPhotoFilePath() != null) {
            String path = event.getPhotoFilePath();
            user_avatar = path;
            Bitmap bitmap = ResourceHelper.readBitmapAutoSize(user_avatar, ivProfileEditAvatar.getWidth(), ivProfileEditAvatar.getHeight());
            ivProfileEditAvatar.setImageBitmap(bitmap);
            try {
                profileEditPresenter.uploadAvatar(uid, user_avatar);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
