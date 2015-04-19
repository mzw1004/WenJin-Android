package com.twt.service.wenjin.ui.setting;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.twt.service.wenjin.BuildConfig;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.ui.about.AboutActivity;

/**
 * Created by M on 2015/4/17.
 */
public class SettingsFragment extends PreferenceFragment {

    Preference prefAbout;
    Preference prefCheck;

    private static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);

        prefAbout = findPreference(getString(R.string.pref_about_key));
        prefCheck = findPreference(getString(R.string.pref_check_version_key));

        prefAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return true;
            }
        });

        prefCheck.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
//                DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//                Uri uri = Uri.parse("https://cdn1.evernote.com/android/yinxiang/com.evernote.yinxiang.AM_702.allArch.528.apk");
//                DownloadManager.Request request = new DownloadManager.Request(uri);
//                downloadManager.enqueue(request);
                return false;
            }
        });
    }
}
