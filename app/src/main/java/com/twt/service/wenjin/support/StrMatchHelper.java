package com.twt.service.wenjin.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Green on 15-6-7.
 */
public class StrMatchHelper {

    public static boolean isDigtial0Chinese0English(String s){
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
