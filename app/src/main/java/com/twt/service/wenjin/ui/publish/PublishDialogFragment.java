package com.twt.service.wenjin.ui.publish;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.LogHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by M on 2015/4/1.
 */
public class PublishDialogFragment extends DialogFragment {

    private static final String LOG_TAG = PublishDialogFragment.class.getSimpleName();

    @InjectView(R.id.et_publish_title)
    EditText etTitle;
    @InjectView(R.id.et_publish_content)
    EditText etContent;
    @InjectView(R.id.tag_group_publish)
    TagGroup tagGroup;

    private MaterialDialog mDialog;

    private PublishCallback mCallback;

    public static interface PublishCallback {
    }

    private final MaterialDialog.ButtonCallback mButtonCallback = new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(MaterialDialog dialog) {
            super.onPositive(dialog);

            String title = etTitle.getText().toString();
            String content = etContent.getText().toString();
            String[] tags = tagGroup.getTags();
            if (tags.length > 0) {
                String topics = tags[0];
                for (int i = 1; i < tags.length; i++) {
                    topics += ",";
                    topics += tags[i];
                }
            }
            if (TextUtils.isEmpty(title)) {
                etTitle.setError("not empty");
            }
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog dialog =  new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_publish_question, true)
                .positiveText(R.string.bt_publish)
                .negativeText(R.string.bt_cancel)
                .callback(mButtonCallback)
                .autoDismiss(false)
                .build();
        View view = dialog.getCustomView();
        ButterKnife.inject(this, view);
        return dialog;
    }

    public void show(ActionBarActivity context) {
        show(context.getSupportFragmentManager(), "PUBLISH_QUESTION");
    }

}
