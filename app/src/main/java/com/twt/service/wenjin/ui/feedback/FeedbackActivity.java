package com.twt.service.wenjin.ui.feedback;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.DeviceUtils;
import com.twt.service.wenjin.ui.BaseActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FeedbackActivity extends BaseActivity implements FeedbackView {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.et_feedback_content)
    EditText etContent;
    @InjectView(R.id.et_feedback_detail)
    EditText etDetail;

    @Inject
    FeedbackPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new FeedbackModule(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_publish:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
