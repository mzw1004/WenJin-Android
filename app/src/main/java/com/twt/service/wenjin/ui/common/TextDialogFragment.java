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
 * Created by M on 2015/4/11.
 */
public class TextDialogFragment extends DialogFragment {

    private static final String PARAM_TEXT = "text";

    public static TextDialogFragment newInstance(String text) {
        TextDialogFragment fragment = new TextDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setTextColor(ResourceHelper.getColor(R.color.color_text_primary));
        textView.setText(getArguments().getString(PARAM_TEXT));
        return new MaterialDialog.Builder(getActivity())
                .customView(textView, true)
                .positiveText(android.R.string.ok)
                .build();
    }

    public void show(AppCompatActivity context) {
        show(context.getSupportFragmentManager(), "TEXT_DISPLAY");
    }
}
