package com.twt.service.wenjin.ui.answer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.event.SelectPhotoResultEvent;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.SelectPhotoDialogFragment;

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
    @InjectView(R.id.cb_answer_anonymous)
    CheckBox cbAnonymous;

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
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AnswerModule(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finishActivity();
                break;
            case R.id.action_insert_photo:
                new SelectPhotoDialogFragment().show(this);
                break;
            case R.id.action_publish:
                item.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        item.setEnabled(true);
                    }
                }, 2000);
                mPresenter.actionAnswer(
                        questionId,
                        etContent.getText().toString(),
                        cbAnonymous.isChecked()
                );
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onSelectPhotoResult(SelectPhotoResultEvent event) {
        if (event.getPhotoFilePath() != null) {
            LogHelper.v(LOG_TAG, "photo file path: " + event.getPhotoFilePath());
            String path = event.getPhotoFilePath();
            mPresenter.addPath(path);
//            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Bitmap bitmap = ResourceHelper.readBitmapAutoSize(path, etContent.getWidth(), etContent.getWidth());
            ImageSpan span = new ImageSpan(bitmap, ImageSpan.ALIGN_BASELINE);

            int start = etContent.getSelectionStart();
            int end = start + path.length();
            etContent.getText().insert(start, path);

            SpannableString ss = new SpannableString(etContent.getText());
            ss.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            etContent.setText(ss, TextView.BufferType.SPANNABLE);
            LogHelper.d(LOG_TAG, etContent.getText().toString());
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
