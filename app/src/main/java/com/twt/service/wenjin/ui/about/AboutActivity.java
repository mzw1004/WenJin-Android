package com.twt.service.wenjin.ui.about;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.DeviceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends ActionBarActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_about_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvVersion.setText("version: " + DeviceUtils.getVersionName());
    }

}
