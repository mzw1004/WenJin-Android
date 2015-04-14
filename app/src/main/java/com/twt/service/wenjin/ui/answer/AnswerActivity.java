package com.twt.service.wenjin.ui.answer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.event.SelectPhotoResultEvent;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.SelectPhotoDialog;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnswerActivity extends BaseActivity implements AnswerView {

    private static final String LOG_TAG = AnswerActivity.class.getSimpleName();

    private static final String PARM_QUESTION_ID = "question_id";

    @Inject
    AnswerPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.et_answer_content)
    EditText etContent;

    private int questionId;

    public static void actionStart(Context context, int questionId) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(PARM_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        questionId = getIntent().getIntExtra(PARM_QUESTION_ID, 0);
        LogHelper.i(LOG_TAG, "question id: " + questionId);
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
                this.finishActivity();
                break;
            case R.id.action_insert_photo:
                new SelectPhotoDialog().show(this);
                break;
            case R.id.action_publish:
                mPresenter.publishAnswer(questionId, etContent.getText().toString(), "");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onSelectPhotoResult(SelectPhotoResultEvent event) {
        if (event.getPhotoFilePath() != null) {
            LogHelper.v(LOG_TAG, "select photo result");
            LogHelper.v(LOG_TAG, "photo file path: " + event.getPhotoFilePath());
        }
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
