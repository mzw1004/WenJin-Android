package com.twt.service.wenjin.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.BuildConfig;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.ui.about.AboutActivity;
import com.twt.service.wenjin.ui.common.TextDialogFragment;
import com.twt.service.wenjin.ui.common.UpdateDialogFragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

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
                ApiClient.checkNewVersion(BuildConfig.VERSION_CODE + "", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String isNew = response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONObject("info").getString("is_new");
                            if (isNew.equals("1")) {
                                String url = response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONObject("info").getString("url");
                                UpdateDialogFragment.newInstance(url).show((ActionBarActivity) getActivity());
                            } else {
                                TextDialogFragment.newInstance(getString(R.string.no_new_version)).show((ActionBarActivity) getActivity());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                return true;
            }
        });
    }
}
