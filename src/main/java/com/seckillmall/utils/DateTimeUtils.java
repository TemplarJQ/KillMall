package com.seckillmall.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Calendar;

public class DateTimeUtils {

    public static final int YESTERDAY = -1;
    public static final int TODAY = 0;
    public static final int AFTER_TODAY = 1;

    public static final String LONG_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(LONG_DATE_TIME_FORMAT);

    public static final DateTimeFormatter DATE_FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final String REMOVE_MYSQL_POSTFIX = ".0";

    private static final int SECONDS_2_MILLS_RADIS = 1000;

    public static String getMidnight(LocalDateTime now) {
        return LocalDateTime.of(now.toLocalDate(), LocalTime.MIDNIGHT).format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parse(String text) {
        if (text.contains(REMOVE_MYSQL_POSTFIX)) {
            text = text.substring(0, text.indexOf(REMOVE_MYSQL_POSTFIX));
        }
        return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
    }

    public static LocalDate parser(String text) {
        return LocalDate.parse(text, DATE_FORMATTER_YYYY_MM_DD);
    }

    public static String getTime(LocalDateTime now) {
        return now.format(TIME_FORMATTER);
    }

    /**
     * 把时间戳转换为LocalDateTime
     *
     * @param timestamp 毫秒
     * @return
     */
    public static LocalDateTime convert(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public static long maxTimeOfDay(LocalDateTime now) {
        return convert(now.with(LocalTime.MAX));
    }

    public static long minTimeOfDay(LocalDateTime now) {
        return convert(now.with(LocalTime.MIN));
    }

    public static Date getDateFromFormat(String date) {
        org.joda.time.format.DateTimeFormatter format = DateTimeFormat.forPattern("yyyy.MM.dd");
        DateTime dateTime = DateTime.parse(date, format);
        return dateTime.toDate();
    }

    /**
     * 相隔几个自然天
     * @param from
     * @param to
     * @return
     */
    public static long gap(LocalDateTime from, LocalDateTime to) {
        return to.toLocalDate().toEpochDay() - from.toLocalDate().toEpochDay();
    }

    /**
     * 把LocalDateTime转换为时间戳
     *
     * @param localDateTime
     * @return 时间戳（毫秒）
     */
    public static long convert(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 除去毫秒
     *
     * @param date
     * @return
     */
    public static long getMillsInSeconds(Date date) {
        return date.getTime() / SECONDS_2_MILLS_RADIS * SECONDS_2_MILLS_RADIS;
    }

    /**
     * 获取前一天
     *
     * @return
     */
    public static Date beforeDay(int days) {
        DateTime dateTime = new DateTime();
        dateTime.minusDays(days);
        return dateTime.toDate();
    }

    /**
     * 获取后一天
     *
     * @return
     */
    public static Date afterDay(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取当前日期，格式yyyyMMdd
     *
     * @return
     */
    public static int getDate() {
        DateTime dateTime = new DateTime();
        String data = dateTime.toString("yyyyMMdd");
        return Integer.parseInt(data);
    }

    public static String getYearAndMonth(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

    public static LocalDate reformLocalDate(String yearAndMonth) {
        yearAndMonth += "01";
        return LocalDate.parse(yearAndMonth, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String localDate2Str8(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static boolean sameYearAndMonth(LocalDate a, LocalDate b) {
        return a.getYear() == b.getYear() && a.getMonthValue() == b.getMonthValue();
    }

    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String getDate(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString("yyyy.MM.dd");
    }

    public static String time2Str(LocalDateTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * LocalDate转时间戳
     * @param localDate
     * @return
     */
    public static long convert(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Date getDate (int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static boolean compare (Date date1, Date date2) {
        int result = date1.compareTo(date2);
        if (result > 0) {
            return true;
        }
        return false;
    }
}

