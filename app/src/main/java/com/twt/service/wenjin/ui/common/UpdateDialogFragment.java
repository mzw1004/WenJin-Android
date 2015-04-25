package com.twt.service.wenjin.ui.common;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twt.service.wenjin.R;

import java.io.FileNotFoundException;

/**
 * Created by M on 2015/4/20.
 */
public class UpdateDialogFragment extends DialogFragment {

    private static final String PARAM_UPDATE_URL = "update_url";
    private static final String PARAM_DESCRIPTION = "description";

    public static UpdateDialogFragment newInstance(String url, String description) {
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_UPDATE_URL, url);
        bundle.putString(PARAM_DESCRIPTION, description);
        fragment.setArguments(bundle);
        return fragment;
    }

    class Callback extends MaterialDialog.ButtonCallback {

        @Override
        public void onPositive(MaterialDialog dialog) {
            super.onPositive(dialog);
            String url = getArguments().getString(PARAM_UPDATE_URL);
            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadManager.enqueue(request);
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String description = getArguments().getString(PARAM_DESCRIPTION);
        Dialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.update_message)
                .content(description)
                .positiveText(R.string.update)
                .negativeText(R.string.update_later)
                .callback(new Callback())
                .build();
        return dialog;
    }

    public void show(AppCompatActivity context) {
        show(context.getSupportFragmentManager(), "UPDATE");
    }
}
