package com.twt.service.wenjin.ui.answer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnswerActivity extends BaseActivity implements AnswerView {

    private static final String LOG_TAG = AnswerActivity.class.getSimpleName();

    private static final String PARAM_ANSWER_ID = "answer_id";

    @Inject
    AnswerPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.pb_answer_loading)
    ProgressBar mPbLoading;

    public static void actionStart(Context context, int answerId) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(PARAM_ANSWER_ID, answerId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int answerId = getIntent().getIntExtra(PARAM_ANSWER_ID, 0);
        LogHelper.v(LOG_TAG, "answer id: " + answerId);
        mPresenter.loadAnswer(answerId);
    }

    @Override
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new AnswerModule(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer, menu);
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

    @Override
    public void showProgressBar() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mPbLoading.setVisibility(View.GONE);
    }
}
