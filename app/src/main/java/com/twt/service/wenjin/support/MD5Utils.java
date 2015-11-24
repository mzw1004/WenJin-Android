package com.twt.service.wenjin.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by M on 2015/4/22.
 */
public class MD5Utils {

    public static String createAttachKey() {
        String attachKey;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String s = date + " " + PrefUtils.getPrefUid();
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(s.getBytes());
            attachKey = bytesToHexString(mdInst.digest());
        } catch (NoSuchAlgorithmException e) {
            attachKey = String.valueOf(s.hashCode());
        }
        return attachKey;
    }

    public static String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
