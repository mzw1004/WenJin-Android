package com.twt.service.wenjin.support;

import com.twt.service.wenjin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by M on 2015/3/24.
 */
public class DateHelper {

    public static String getTimeFromNow(long date) {
        Date postDate = new Date(date * 1000);
        Date nowData = new Date(Calendar.getInstance().getTimeInMillis());

        int years = nowData.getYear() - postDate.getYear();
        int months = nowData.getMonth() - postDate.getMonth();
        int days = nowData.getDay() - postDate.getDay();
        int hours = nowData.getHours() - postDate.getHours();
        int minutes = nowData.getMinutes() - postDate.getMinutes();
        int seconds = nowData.getSeconds() - postDate.getSeconds();

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
        } else {
            return seconds + " " + ResourceHelper.getString(R.string.seconds_ago);
        }
    }

    public static String formatAddDate(long addtime) {
        Date date = new Date(addtime * 1000);
        return ResourceHelper.getString(R.string.add_in) + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }
}
