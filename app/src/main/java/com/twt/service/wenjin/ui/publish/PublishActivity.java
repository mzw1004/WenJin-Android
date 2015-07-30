package com.twt.service.wenjin.ui.publish;

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
import me.gujun.android.taggroup.TagGroup;

public class PublishActivity extends BaseActivity implements PublishView {

    private static final String LOG_TAG = PublishActivity.class.getSimpleName();

    @Inject
    PublishPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.et_publish_title)
    EditText etTitle;
    @InjectView(R.id.et_publish_content)
    EditText etContent;
    @InjectView(R.id.tag_group_publish)
    TagGroup tagGroup;
    @InjectView(R.id.cb_publish_anonymous)
    CheckBox cbAnonymous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        return Arrays.<Object>asList(new PublishModule(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                this.finish();
                finishActivity();
                break;
            case R.id.action_publish:
                item.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        item.setEnabled(true);
                    }
                }, 2000);
                mPresenter.actionPublish(
                        etTitle.getText().toString(),
                        etContent.getText().toString(),
                        tagGroup.getTags(),
                        cbAnonymous.isChecked()
                );
                break;
            case R.id.action_insert_photo:
                new SelectPhotoDialogFragment().show(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onSelectPhotoResult(SelectPhotoResultEvent event) {
        if (event.getPhotoFilePath() != null) {
//            LogHelper.v(LOG_TAG, "select photo result");
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
//        final String title = etTitle.getText().toString();
//        String topics = "";
//        if (tagGroup.getTags().length > 0) {
//            topics = tagGroup.getTags()[0];
//            for (int i = 1; i < tagGroup.getTags().length; i++) {
//                topics += ",";
//                topics += tagGroup.getTags()[i];
//            }
//        }
//        final String content = etContent.getText().toString();
//        final boolean anonymous = cbAnonymous.isChecked();
//
//        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(topics) || !TextUtils.isEmpty(content) || anonymous) {
//            PromptDialogFragment dialogFragment = PromptDialogFragment.newInstance("Are you sure?");
//            final String finalTopics = topics;
//            dialogFragment.setCallback(new MaterialDialog.ButtonCallback() {
//                @Override
//                public void onPositive(MaterialDialog dialog) {
//                    super.onPositive(dialog);
//                    QuestionDraft draft = new QuestionDraft();
//                    draft.title = title;
//                    draft.topics = finalTopics;
//                    draft.content = content;
//                    draft.anonymous = anonymous;
//                    draft.save();
//                    toastMessage("save successfully");
//                    finish();
//                }
//
//                @Override
//                public void onNegative(MaterialDialog dialog) {
//                    super.onNegative(dialog);
//                    finish();
//                }
//            });
//            dialogFragment.show(this);
//        } else {
//        this.finish();
//        }
        finish();
    }

}
