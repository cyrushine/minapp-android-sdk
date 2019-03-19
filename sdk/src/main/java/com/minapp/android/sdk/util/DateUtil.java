package com.minapp.android.sdk.util;

import android.support.annotation.Nullable;
import android.util.Log;
import com.minapp.android.sdk.Const;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DateUtil {

    private static final Pattern DB_DATETIME_REGEXP =
            Pattern.compile("[+-]?(\\d{4})-?(\\d{2})-?(\\d{2})T(\\d{2}):?(\\d{2}):?([0-9.]{2,})[Z+](\\d{2}:?\\d{2}?)?");

    private static final String DB_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX";

    /**
     * 知晓云数据库 Date 类型字段返回 ISO8601 格式的日期字符串，例如："2018-09-01T18:31:02.631000+08:00"
     * 这里把它转换为 {@link Calendar}
     * @param str
     * @return
     */
    public static @Nullable Calendar parseDBDateString(String str) {
        return parseDBDateStringByRegExp(str);
    }

    /**
     * 使用正则表达式来解析 ISO8601 格式的日期时间字符串
     * @param str
     * @return
     */
    private static @Nullable Calendar parseDBDateStringByRegExp(String str) {
        try {
            Matcher matcher = DB_DATETIME_REGEXP.matcher(str);
            if (matcher.matches()) {
                Integer year = Integer.valueOf(matcher.group(1));
                Integer month = Integer.valueOf(matcher.group(2));
                Integer day = Integer.valueOf(matcher.group(3));
                Integer hour = Integer.valueOf(matcher.group(4));
                Integer minute = Integer.valueOf(matcher.group(5));
                Integer second = Double.valueOf(matcher.group(6)).intValue();
                Integer timeZoneOffset = Integer.valueOf(matcher.group(7).substring(0, 2));

                String[] timeZoneIds = TimeZone.getAvailableIDs( 1000 * 60 * 60 * timeZoneOffset);
                TimeZone timeZone = timeZoneIds != null && timeZoneIds.length > 0 ? TimeZone.getTimeZone(timeZoneIds[0]) : TimeZone.getDefault();

                Calendar calendar = Calendar.getInstance(timeZone);
                calendar.set(year, month, day, hour, minute, second);
                return calendar;
            }
        } catch (Exception e) {
            Log.e(Const.TAG, e.getMessage(), e);
        }
        return null;
    }

    /**
     * 使用 {@link SimpleDateFormat} 来解析 ISO8601 格式的日期时间字符串
     * @param str
     * @return
     */
    private static @Nullable Calendar parseDBDateStringBySDF(String str) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat(DB_DATETIME_FORMAT).parse(str));
            return calendar;
        } catch (Exception e) {
            Log.e(Const.TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param calendar
     * @return
     * @see #parseDBDateString(String)
     */
    public static @Nullable String formatDBDateString(Calendar calendar) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DB_DATETIME_FORMAT);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            Log.e(Const.TAG, e.getMessage(), e);
            return null;
        }
    }

}
