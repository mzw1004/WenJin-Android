package com.twt.service.wenjin.ui.feedback;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.ui.BaseActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FeedbackActivity extends BaseActivity implements FeedbackView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_feedback_content)
    EditText etContent;
    @Bind(R.id.et_feedback_detail)
    EditText etDetail;

    @Inject
    FeedbackPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new FeedbackModule(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_publish:
                item.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        item.setEnabled(true);
                    }
                }, 2000);
                mPresenter.publish(etContent.getText().toString(), etDetail.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

}
