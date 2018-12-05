package com.ly.common.util;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Calendar;

/**
 * @auther Administrator liyang
 * @create 2018/10/26 1:33
 */
public class CalendarUtils {

    /**
     * 昨天
     * @return
     */
    public static Calendar getYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) -1);
        String tomorrow = DateFormatUtils.ISO_DATE_FORMAT.format(calendar);

        return calendar;
    }

    /**
     * 明天
     * @return
     */
    public static Calendar getTomorrow(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        String tomorrow = DateFormatUtils.ISO_DATE_FORMAT.format(calendar);

        return calendar;

    }

    /**
     * 加几天
     * @param calendar
     * @param day
     * @return
     */
    public static Calendar getUpDayByMonth(Calendar calendar,int day){
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
        String tomorrow = DateFormatUtils.ISO_DATE_FORMAT.format(calendar);
        return calendar;

    }

    /**
     * 减去几天
     * @param calendar
     * @param day
     * @return
     */
    public static Calendar getDownDayByMonth(Calendar calendar,int day){
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) -day);
        String tomorrow = DateFormatUtils.ISO_DATE_FORMAT.format(calendar);
        return calendar;
    }
}
