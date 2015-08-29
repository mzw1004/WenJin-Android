package com.twt.service.wenjin.support;

/**
 * Created by RexSun on 15/8/27.
 */
public class HtmlUtils {
    public static String format(String content) {
        String stringToAdd = "width=\"100%\" ";
        StringBuilder stringBuilder = new StringBuilder(content);
        int i = 0;
        int cont = 0;
        while (i != -1) {
            i = content.indexOf("src", i + 1);
            if (i != -1) stringBuilder.insert(i + (cont * stringToAdd.length()), stringToAdd);
            cont++;
        }
        return stringBuilder.toString();
    }
}
