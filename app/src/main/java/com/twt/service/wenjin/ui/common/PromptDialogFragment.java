package com.twt.service.wenjin.ui.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.ResourceHelper;

/**
 * Created by M on 2015/5/15.
 */
public class PromptDialogFragment extends DialogFragment {

    private static final String PARAM_PROMPT = "prompt";

    private MaterialDialog.ButtonCallback callback;

    public static PromptDialogFragment newInstance(String prompt) {
        PromptDialogFragment fragment = new PromptDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_PROMPT, prompt);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCallback(MaterialDialog.ButtonCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setTextColor(ResourceHelper.getColor(R.color.color_text_primary));
        textView.setText(getArguments().getString(PARAM_PROMPT));
        return new MaterialDialog.Builder(getActivity())
                .customView(textView, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .callback(callback)
                .build();
    }

    public void show(AppCompatActivity context) {
        show(context.getSupportFragmentManager(), "PROMPT_DISPLAY");
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
