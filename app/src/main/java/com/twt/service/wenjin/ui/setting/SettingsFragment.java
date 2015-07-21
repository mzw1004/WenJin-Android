package com.twt.service.wenjin.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.BuildConfig;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.WenJinApp;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.ui.about.AboutActivity;
import com.twt.service.wenjin.ui.common.TextDialogFragment;
import com.twt.service.wenjin.ui.common.UpdateDialogFragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by M on 2015/4/17.
 */
public class SettingsFragment extends PreferenceFragment {

    SwitchPreference prefNotification;
    Preference prefAbout;
    Preference prefCheck;

    private static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);


        prefNotification = (SwitchPreference)findPreference(getString(R.string.pref_launch_notification_key));
        prefCheck = findPreference(getString(R.string.pref_check_version_key));
        prefAbout = findPreference(getString(R.string.pref_about_key));




        prefNotification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (!((SwitchPreference) preference).isChecked()) {
                    PrefUtils.setLaunchNotification(true);
                    JPushInterface.resumePush(WenJinApp.getContext());
                } else {
                    PrefUtils.setLaunchNotification(false);
                    JPushInterface.stopPush(WenJinApp.getContext());
                }
                return true;
            }

        });

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
                                String description = response.getJSONObject(ApiClient.RESP_MSG_KEY).getJSONObject("info").getString("description");
                                UpdateDialogFragment.newInstance(url, description).show((ActionBarActivity) getActivity());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        prefNotification.setChecked(PrefUtils.isLaunchNotification());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        ApiClient.getInstance().cancelRequests(getActivity(), false);
    }

}
