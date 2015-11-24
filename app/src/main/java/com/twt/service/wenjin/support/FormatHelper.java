package com.twt.service.wenjin.support;

import com.twt.service.wenjin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by M on 2015/3/24.
 */
public class FormatHelper {

    public static String getTimeFromNow(long date) {

        Calendar calendar = Calendar.getInstance();

        int years = calendar.get(Calendar.YEAR);
        int months = calendar.get(Calendar.MONTH);
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        calendar.setTimeInMillis(date * 1000);

        years -= calendar.get(Calendar.YEAR);
        months -= calendar.get(Calendar.MONTH);
        days -= calendar.get(Calendar.DAY_OF_MONTH);
        hours -= calendar.get(Calendar.HOUR_OF_DAY);
        minutes -= calendar.get(Calendar.MINUTE);
        seconds -= calendar.get(Calendar.SECOND);

        if (years > 0) {
            return years + " " + ResourceHelper.getString(R.string.years_ago);
        } else if (months > 0) {
            return months + " " + ResourceHelper.getString(R.string.months_ago);
        } else if (days > 0) {
            return days + " " + ResourceHelper.getString(R.string.days_ago);
        } else if (hours > 0) {
            return hours + " " + ResourceHelper.getString(R.string.hours_ago);
        } else if (minutes > 0) {
            return minutes + " " + ResourceHelper.getString(R.string.minutes_ago);
        } else if (seconds > 0) {
            return seconds + " " + ResourceHelper.getString(R.string.seconds_ago);
        } else {
            return ResourceHelper.getString(R.string.just_now);
        }
    }

    public static String formatAddDate(long addtime) {
        Date date = new Date(addtime * 1000);
        return ResourceHelper.getString(R.string.add_in) + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static String formatAddDateWithoutAddinString(long addtime) {
        Date date = new Date(addtime * 1000);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static String formatCommentReply(String username, String content) {
        return ResourceHelper.getString(R.string.reply) + " " + username + ": " + content;
    }

    public static String formatCommentAtUser(String username) {
        return "@" + username + ":";
    }

    public static String formatHomeHtmlStr(String html) {
        return html.replaceAll("<img src=.*?>", ResourceHelper.getString(R.string.pic));
    }

    public static String formatQuestionLink(int questionId) {
        return "http://wenjin.twtstudio.com/?/question/" + questionId;
    }

    public static String formatArticleLink(int articleId) {
        return "http://wenjin.twtstudio.com/?/api/article/article/&id=" + articleId;
    }

}
