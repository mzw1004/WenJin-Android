package com.twt.service.wenjin.support;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.twt.service.wenjin.WenJinApp;
import com.twt.service.wenjin.bean.UserInfo;

/**
 * Created by M on 2015/3/25.
 */
public class PrefUtils {

    private static final String PREF_UID = "uid";

    private static final String PREF_USERNAME = "user_name";

    private static final String PREF_AVATAR_FILE = "avatar_file";

    private static final String PREF_IS_LOGIN = "is_login";

    public static SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(WenJinApp.getContext());
    }

    public static void setDefaultPrefUserInfo(UserInfo userInfo) {
        getDefaultSharedPreferences().edit()
                .putInt(PREF_UID, userInfo.uid)
                .putString(PREF_USERNAME, userInfo.nick_name)
                .putString(PREF_AVATAR_FILE, userInfo.avatar_file)
                .apply();
    }

    public static int getPrefUid() {
        return getDefaultSharedPreferences().getInt(PREF_UID, 0);
    }

    public static String getPrefUsername() {
        return getDefaultSharedPreferences().getString(PREF_USERNAME, "not log in");
    }

    public static String getPrefAvatarFile() {
        return getDefaultSharedPreferences().getString(PREF_AVATAR_FILE, null);
    }

    public static void setLogin(boolean isLogin) {
        getDefaultSharedPreferences().edit().putBoolean(PREF_IS_LOGIN, isLogin).apply();
    }

    public static boolean isLogin() {
        return getDefaultSharedPreferences().getBoolean(PREF_IS_LOGIN, false);
    }

}
