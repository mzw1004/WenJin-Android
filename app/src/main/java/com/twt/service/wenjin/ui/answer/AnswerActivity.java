package com.twt.service.wenjin.ui.answer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.otto.Subscribe;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.AnswerDraft;
import com.twt.service.wenjin.event.SelectPhotoResultEvent;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.PromptDialogFragment;
import com.twt.service.wenjin.ui.common.SelectPhotoDialogFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnswerActivity extends BaseActivity implements AnswerView {

    private static final String LOG_TAG = AnswerActivity.class.getSimpleName();

    private static final String PARAM_QUESTION_ID = "question_id";
    private static final String PARAM_QUESTION_TITLE = "question_title";
    private static final String PARAM_ANSWER_DRAFT = "answer_draft";

    @Inject
    AnswerPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.et_answer_content)
    EditText etContent;
    @InjectView(R.id.cb_answer_anonymous)
    CheckBox cbAnonymous;

    private int questionId;
    private String questionTitle;

    public static void actionStart(Context context, int questionId, String questionTitle) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(PARAM_QUESTION_ID, questionId);
        intent.putExtra(PARAM_QUESTION_TITLE, questionTitle);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, AnswerDraft draft) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(PARAM_ANSWER_DRAFT, draft);
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

        AnswerDraft draft = getIntent().getParcelableExtra(PARAM_ANSWER_DRAFT);
        if (draft != null) {
            questionId = draft.question_id;
            questionTitle = draft.question_title;
            etContent.setText(draft.content);
        } else {
            questionId = getIntent().getIntExtra(PARAM_QUESTION_ID, 0);
            questionTitle = getIntent().getStringExtra(PARAM_QUESTION_TITLE);
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finishActivity();
        }
        return super.onKeyDown(keyCode, event);
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

        final String content = etContent.getText().toString();
        final boolean anonymous = cbAnonymous.isChecked();

        if (!TextUtils.isEmpty(content) || anonymous) {
            PromptDialogFragment dialogFragment = PromptDialogFragment.newInstance(getString(R.string.save_as_draft));
            dialogFragment.setCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    AnswerDraft draft = new AnswerDraft();
                    draft.question_id = questionId;
                    draft.question_title = questionTitle;
                    draft.content = content;
                    draft.anonymous = anonymous;
                    draft.save();
                    toastMessage(getString(R.string.save_successfully));
                    finish();
                }

                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    finish();
                }
            });
            dialogFragment.show(this);
        } else {
            this.finish();
        }
//        this.finish();
    }

    @Override
    public void finishWithoutHint() {
        finish();
    }

}
