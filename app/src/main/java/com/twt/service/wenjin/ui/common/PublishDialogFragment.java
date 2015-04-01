package com.twt.service.wenjin.ui.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twt.service.wenjin.R;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by M on 2015/4/1.
 */
public class PublishDialogFragment extends DialogFragment {

    private PublishCallback mCallback;

    public static interface PublishCallback {
    }

    private final MaterialDialog.ButtonCallback mButtonCallback = new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(MaterialDialog dialog) {
            super.onPositive(dialog);
            String title = ((EditText) dialog.findViewById(R.id.et_publish_title)).getText().toString();
            String content = ((EditText) dialog.findViewById(R.id.et_publish_content)).getText().toString();
            String[] tags = ((TagGroup) dialog.findViewById(R.id.tag_group_publish)).getTags();
            String topics = tags[0];
            for (int i = 1; i < tags.length; i++) {
                topics += ",";
                topics += tags[i];
            }
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_publish_question, true)
                .positiveText(R.string.bt_publish)
                .negativeText(R.string.bt_cancel)
                .build();
    }

    public void show(ActionBarActivity context) {
        show(context.getSupportFragmentManager(), "PUBLISH_QUESTION");
    }

}
