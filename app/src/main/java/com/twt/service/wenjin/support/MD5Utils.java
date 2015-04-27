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
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String date = dateFormat.format(Calendar.getInstance().getTime());
            String s = date + " " + PrefUtils.getPrefUid();

            mdInst.update(s.getBytes());
            byte bs[] = mdInst.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte b : bs) {
                i = b;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
