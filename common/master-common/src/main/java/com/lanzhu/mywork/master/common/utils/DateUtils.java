package com.lanzhu.mywork.master.common.utils;

import com.lanzhu.mywork.master.common.constant.CommonConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-08
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String SIMPLE_PATTERN = "yyyy-MM-dd";
    public static final String SIMPLE_PATTERN_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String SIMPLE_PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
        if (date == null) {
            return null;
        }
        int timeOffset = oldZone.getRawOffset() - newZone.getDSTSavings();
        return new Date(date.getTime() - timeOffset);
    }

    public static Calendar getNewCalender() {
        return Calendar.getInstance();
    }

    public static Date getNewDate() {
        return getNewCalender().getTime();
    }

    public static Date getNewUTCDate() {
        return changeTimeZone(getNewDate(), TimeZone.getDefault(), TimeZone.getTimeZone(CommonConstant.UTC));
    }

    public static String formatter(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatterUTC(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone(CommonConstant.UTC));
        return format.format(date);
    }

    public static Date parse(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(date);
    }

    public static Date parseUTC(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone(CommonConstant.UTC));
        return format.parse(date);
    }

    public static Date getDateBegin(Date date) {
        if (date == null) {
            return null;
        }
        return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.DATE);
    }

    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = getNewCalender();
        calendar.setLenient(false);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
