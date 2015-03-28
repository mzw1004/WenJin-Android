package com.twt.service.wenjin.ui.question;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionActivity extends BaseActivity implements QuestionView {

    private static final String LOG_TAG = QuestionActivity.class.getSimpleName();

    private static final String PARAM_QUESTION_ID = "question_id";

    @Inject
    QuestionPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.question_recycler_view)
    RecyclerView mRecyclerView;

    private QuestionAdapter mQuestionAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public static void actionStart(Context context, int questionId) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(PARAM_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        int questionId = getIntent().getIntExtra(PARAM_QUESTION_ID, 0);
        LogHelper.v(LOG_TAG, "question id:" + questionId);
        mPresenter.loadingContent(questionId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new QuestionModule(this));
    }

    @Override
    public void setAdapter(QuestionResponse questionResponse) {
        mQuestionAdapter = new QuestionAdapter(this, questionResponse);
        mRecyclerView.setAdapter(mQuestionAdapter);
    }
}
