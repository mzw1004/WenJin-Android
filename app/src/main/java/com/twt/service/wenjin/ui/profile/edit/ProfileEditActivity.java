package com.twt.service.wenjin.ui.profile.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.twt.service.wenjin.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileEditActivity extends AppCompatActivity {

    private static final String PARAM_UID = "uid";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private int uid;

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

        uid = getIntent().getIntExtra(PARAM_UID, 0);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
