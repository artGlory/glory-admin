package com.spring.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Moment {

    static {
        TimeZone china = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(china);
    }

    private GregorianCalendar gc;

    public Moment() {
        gc = new GregorianCalendar();
        gc.setFirstDayOfWeek(Calendar.MONDAY);
    }

    public Moment(GregorianCalendar gc) {
        this.gc = gc;
    }

    /**
     * 获取GregorianCalendar对象
     *
     * @return
     */
    public GregorianCalendar getGc() {
        return gc;
    }

    /**
     * 根据日期获取对象
     *
     * @param date
     * @return
     */
    public Moment fromDate(String date) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse(date);
            gc.setTime(d);
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * 从日期转换
     *
     * @param date
     * @return
     */
    public Moment fromDate(Date date) {
        gc.setTime(date);
        return this;
    }

    /**
     * 根据时间获取对象
     *
     * @param time
     * @return
     */
    public Moment fromTime(String time) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = format.parse(time);
            gc.setTime(d);
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * 根据时间获取对象
     *
     * @param time
     * @return
     */
    public Moment fromTime(Timestamp time) {
        gc.setTime(time);
        return this;
    }

    /**
     * 明天
     *
     * @return
     */
    public Moment tomorrow() {
        return add(1, "days");
    }

    /**
     * 昨天
     *
     * @return
     */
    public Moment yesterday() {
        return subtract(1, "days");
    }

    /**
     * 根据时间+format
     *
     * @param time
     * @param formatStr
     * @return
     */
    public Moment fromTime(String time, String formatStr) {
        try {
            DateFormat format = new SimpleDateFormat(formatStr);
            Date d = format.parse(time);
            gc.setTime(d);
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * 转换成普通时间 1900-01-01 12:00:00
     *
     * @return
     */
    public String toSimpleTime() {
        return year() + "-" + months() + "-" + days() + " " + hours() + ":" + minutes() + ":" + seconds();
    }

    /**
     * 转换成完整时间1900-01-01 12:00:00.000
     *
     * @return
     */
    public String toFullTime() {
        return toSimpleTime() + "." + milliseconds();
    }

    /**
     * 转成日期格式1900-01-01
     *
     * @return
     */
    public String toSimpleDate() {
        return year() + "-" + months() + "-" + days();
    }

    /**
     * 获取date对象
     *
     * @return
     */
    public Date toDate() {
        return gc.getTime();
    }

    /**
     * 转换成timestamp
     *
     * @return
     */
    public Timestamp toTimestamp() {
        return new Timestamp(gc.getTimeInMillis());
    }

    /**
     * 根据参数格式化 y年 M月 d日 H时 m分 s秒
     *
     * @param format
     * @return
     */
    public String format(String format) {
        return new SimpleDateFormat(format).format(toDate());
    }

    /**
     * 获取年份
     *
     * @return
     */
    public int year() {
        return gc.get(Calendar.YEAR);
    }

    /**
     * 设置年份
     *
     * @param year
     */
    public Moment year(int year) {
        gc.set(Calendar.YEAR, year);
        return this;
    }

    /**
     * 获取月份
     *
     * @return
     */
    public int month() {
        return gc.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取完整月份
     *
     * @return
     */
    public String months() {
        return String.format("%02d", month());
    }

    /**
     * 设置月份
     *
     * @param month
     * @return
     */
    public Moment month(int month) {
        if (month > 0 && month <= 12) {
            gc.set(Calendar.MONTH, month - 1);
        }
        return this;
    }

    /**
     * 设置月份
     *
     * @param month
     * @return
     */
    public Moment month(String month) {
        month(Integer.valueOf(month));
        return this;
    }

    /**
     * 获取天
     *
     * @return
     */
    public int day() {
        return gc.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取完整天
     *
     * @return
     */
    public String days() {
        return String.format("%02d", day());
    }

    /**
     * 设置天
     *
     * @param day
     * @return
     */
    public Moment day(int day) {
        if (day > 0 && day <= 31) {
            gc.set(Calendar.DAY_OF_MONTH, day);
        }
        return this;
    }

    /**
     * 设置天
     *
     * @param day
     * @return
     */
    public Moment day(String day) {
        day(Integer.valueOf(day));
        return this;
    }

    /**
     * 获取小时
     *
     * @return
     */
    public int hour() {
        return gc.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取小时
     *
     * @return
     */
    public String hours() {
        return String.format("%02d", hour());
    }

    /**
     * 设置小时
     *
     * @param hour
     * @return
     */
    public Moment hour(int hour) {
        if (hour >= 0 && hour < 24) {
            gc.set(Calendar.HOUR_OF_DAY, hour);
        }
        return this;
    }

    /**
     * 设置小时
     *
     * @param hour
     * @return
     */
    public Moment hour(String hour) {
        hour(Integer.valueOf(hour));
        return this;
    }

    /**
     * 获取分钟
     *
     * @return
     */
    public int minute() {
        return gc.get(Calendar.MINUTE);
    }

    /**
     * 获取分钟
     *
     * @return
     */
    public String minutes() {
        return String.format("%02d", minute());
    }

    /**
     * 设置分钟
     *
     * @param minute
     * @return
     */
    public Moment minute(int minute) {
        if (minute >= 0 && minute < 60) {
            gc.set(Calendar.MINUTE, minute);
        }
        return this;
    }

    /**
     * 设置分钟
     *
     * @param minute
     * @return
     */
    public Moment minute(String minute) {
        minute(Integer.valueOf(minute));
        return this;
    }

    /**
     * 获取秒
     *
     * @return
     */
    public int second() {
        return gc.get(Calendar.SECOND);
    }

    /**
     * 获取秒
     *
     * @return
     */
    public String seconds() {
        return String.format("%02d", second());
    }

    /**
     * 设置秒
     *
     * @param second
     * @return
     */
    public Moment second(int second) {
        if (second >= 0 && second < 60) {
            gc.set(Calendar.SECOND, second);
        }
        return this;
    }

    /**
     * 设置秒
     *
     * @param second
     * @return
     */
    public Moment second(String second) {
        second(Integer.valueOf(second));
        return this;
    }

    /**
     * 获取毫秒
     *
     * @return
     */
    public int millisecond() {
        return gc.get(Calendar.MILLISECOND);
    }

    /**
     * 获取毫秒
     *
     * @return
     */
    public String milliseconds() {
        return String.format("%03d", millisecond());
    }

    /**
     * 设置毫秒
     *
     * @param millisecond
     * @return
     */
    public Moment millisecond(int millisecond) {
        if (millisecond >= 0 && millisecond < 1000) {
            gc.set(Calendar.MILLISECOND, millisecond);
        }
        return this;
    }

    /**
     * 设置毫秒
     *
     * @param millisecond
     * @return
     */
    public Moment millisecond(String millisecond) {
        millisecond(Integer.valueOf(millisecond));
        return this;
    }

    /**
     * 获取当前日期在本月的第几周
     *
     * @return
     */
    public int weekOfMonth() {
        return gc.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取当前日期在本年的第几周
     *
     * @return
     */
    public int weekOfYear() {
        return gc.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取星期几
     *
     * @return
     */
    public int dayOfWeek() {
        int field = gc.get(Calendar.DAY_OF_WEEK);
        switch (field) {
            case Calendar.SUNDAY:
                return 7;
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            default:
                break;
        }
        return gc.getFirstDayOfWeek();
    }

    /**
     * 设置星期几
     *
     * @param week
     * @return
     */
    public Moment dayOfWeek(int week) {
        switch (week) {
            case 7:
                gc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
            case 1:
                gc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case 2:
                gc.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case 3:
                gc.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case 4:
                gc.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case 5:
                gc.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case 6:
                gc.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * 获取当前日期在本年的第几天
     *
     * @return
     */
    public int dayOfYear() {
        return gc.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 设置日期为本年的第几天
     *
     * @param day
     * @return
     */
    public Moment dayOfYear(int day) {
        gc.set(Calendar.DAY_OF_YEAR, day);
        return this;
    }

    /**
     * 获取当前日期所在季度 1月~3月 第一季度 4月~6月 第二季度 7月~9月 第三季度 10月~12月 第四季度
     *
     * @return
     */
    public int quarter() {
        int month = month();
        switch (month) {
            case 1:
            case 2:
            case 3:
                return 1;
            case 4:
            case 5:
            case 6:
                return 2;
            case 7:
            case 8:
            case 9:
                return 3;
            case 10:
            case 11:
            case 12:
                return 4;
            default:
                return 0;
        }
    }

    /**
     * 根据参数获取
     *
     * @param name
     * @return
     */
    public int get(String key) {
        switch (key) {
            case "year":
                return year();
            case "month":
                return month();
            case "day":
            case "date":
                return day();
            case "hour":
                return hour();
            case "minute":
                return minute();
            case "second":
                return second();
            case "millisecond":
                return millisecond();
            default:
                return 0;
        }
    }

    /**
     * 根据参数设置值
     *
     * @param key
     * @param value
     * @return
     */
    public Moment set(String key, int value) {
        switch (key) {
            case "year":
                year(value);
                break;
            case "month":
                month(value);
                break;
            case "day":
            case "date":
                day(value);
                break;
            case "hour":
                hour(value);
                break;
            case "minute":
                minute(value);
                break;
            case "second":
                second(value);
                break;
            case "millisecond":
                millisecond(value);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * 获取millis时间
     *
     * @return
     */
    public long millis() {
        return gc.getTimeInMillis();
    }

    /**
     * 获取其中时间最大的那个
     *
     * @param moment
     * @return
     */
    public static Moment max(final Moment... moment) {
        Moment max = null;
        if (moment != null && moment.length > 0) {
            for (Moment tmp : moment) {
                if (max == null) {
                    max = tmp;
                }
                if (max.millis() < tmp.millis()) {
                    max = tmp;
                }
            }
        }
        return max;
    }

    /**
     * 获取其中时间最小的那个
     *
     * @param moment
     * @return
     */
    public static Moment min(final Moment... moment) {
        Moment min = null;
        if (moment != null && moment.length > 0) {
            for (Moment tmp : moment) {
                if (min == null) {
                    min = tmp;
                }
                if (min.millis() > tmp.millis()) {
                    min = tmp;
                }
            }
        }
        return min;
    }

    /**
     * 增加指定key的时间
     *
     * @param number
     * @param key
     * @return
     */
    public Moment add(int amount, String key) {
        switch (key) {
            case "years":
            case "y":
                gc.add(Calendar.YEAR, amount);
                break;
            case "quarters":
            case "Q":
                gc.add(Calendar.MONTH, amount * 3);
                break;
            case "months":
            case "M":
                gc.add(Calendar.MONTH, amount);
                break;
            case "weeks":
            case "w":
                gc.add(Calendar.WEEK_OF_YEAR, amount);
                break;
            case "days":
            case "d":
                gc.add(Calendar.DAY_OF_MONTH, amount);
                break;
            case "hours":
            case "h":
                gc.add(Calendar.HOUR_OF_DAY, amount);
                break;
            case "minutes":
            case "m":
                gc.add(Calendar.MINUTE, amount);
                break;
            case "seconds":
            case "s":
                gc.add(Calendar.SECOND, amount);
                break;
            case "milliseconds":
            case "ms":
                gc.add(Calendar.MILLISECOND, amount);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * 减去指定key的时间
     *
     * @param number
     * @param key
     * @return
     */
    public Moment subtract(int amount, String key) {
        return add(-amount, key);
    }

    /**
     * 获取key的开始时间
     *
     * @param key
     * @return
     */
    public Moment startOf(String key) {
        Moment moment = new Moment((GregorianCalendar) this.gc.clone());
        switch (key) {
            case "year":
                moment.dayOfYear(1).hour(0).minute(0).second(0).millisecond(0);
                break;
            case "month":
                moment.day(1).hour(0).minute(0).second(0).millisecond(0);
                break;
            case "week":
                moment.dayOfWeek(1).hour(0).minute(0).second(0).millisecond(0);
                break;
            case "day":
                moment.hour(0).minute(0).second(0).millisecond(0);
                break;
            case "hour":
                moment.minute(0).second(0).millisecond(0);
            case "minute":
                moment.second(0).millisecond(0);
                break;
            case "second":
                moment.millisecond(0);
                break;
            default:
                break;
        }
        return moment;
    }

    /**
     * 获取key的结束时间
     *
     * @param key
     * @return
     */
    public Moment endOf(String key) {
        Moment moment = new Moment();
        switch (key) {
            case "year":
                return moment.startOf(key).add(1, "years").subtract(1, "days").hour(23).minute(59).second(59)
                        .millisecond(999);
            case "month":
                return moment.startOf(key).add(1, "months").subtract(1, "days").hour(23).minute(59).second(59)
                        .millisecond(999);
            case "week":
                return moment.startOf(key).add(1, "weeks").subtract(1, "days").hour(23).minute(59).second(59)
                        .millisecond(999);
            case "day":
                return moment.startOf(key).hour(23).minute(59).second(59).millisecond(999);
            case "hour":
                return moment.startOf(key).minute(59).second(59).millisecond(999);
            case "minute":
                return moment.startOf(key).second(59).millisecond(999);
            case "second":
                return moment.startOf(key).millisecond(999);
            default:
                break;
        }
        return moment;
    }

    /**
     * 获取时间差值
     *
     * @param moment
     * @param key
     * @return
     */
    public int difference(Moment moment, String key) {
        switch (key) {
            case "year":
                return this.year() - moment.year();
            case "month":
                return difference(moment, "year") * 12 + (this.month() - moment.month());
            case "day":
                return (int) ((this.millis() - moment.millis()) / (24 * 60 * 60 * 1000));
            case "hour":
                return (int) ((this.millis() - moment.millis()) / (60 * 60 * 1000));
            case "minute":
                return (int) ((this.millis() - moment.millis()) / (60 * 1000));
            case "second":
                return (int) ((this.millis() - moment.millis()) / (1000));
            default:
                break;
        }
        return 0;
    }

    /**
     * 大于另一个日期
     *
     * @param moment
     * @return
     */
    public boolean gt(Moment moment) {
        return (this.millis() > moment.millis()) ? true : false;
    }

    /**
     * 小于另一个日期
     *
     * @param moment
     * @return
     */
    public boolean lt(Moment moment) {
        return (this.millis() < moment.millis()) ? true : false;
    }

    /**
     * 大于或者等于另一个日期
     *
     * @param moment
     * @return
     */
    public boolean ge(Moment moment) {
        return (this.millis() >= moment.millis()) ? true : false;
    }

    /**
     * 小于或者等于另一个日期
     *
     * @param moment
     * @return
     */
    public boolean le(Moment moment) {
        return (this.millis() <= moment.millis()) ? true : false;
    }

    /**
     * 在两个日期之间
     *
     * @param sMoment
     * @param eMoment
     * @return
     */
    public boolean between(Moment sMoment, Moment eMoment) {
        return (this.ge(sMoment) && this.le(eMoment)) ? true : false;
    }

    /**
     * 列出两个日期之间的日期
     *
     * @param sMoment
     * @param eMoment
     * @return
     */
    public static String[] listRangeDate(Moment sMoment, Moment eMoment) {
        List<String> list = new ArrayList<String>();
        if (sMoment.le(eMoment)) {
            list.add(sMoment.toSimpleDate());
            int days = eMoment.difference(sMoment, "day");
            for (int i = 0; i < days - 1; i++) {
                list.add(sMoment.add(1, "days").toSimpleDate());
            }
        }
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static String[] listRangeDate(String sDate, String eDate) {
        Moment sMoment = new Moment().fromDate(sDate);
        Moment eMoment = new Moment().fromDate(eDate);
        return listRangeDate(sMoment, eMoment);
    }

    public static String[] listRangeDate(Date sDate, Date eDate) {
        Moment sMoment = new Moment().fromDate(sDate);
        Moment eMoment = new Moment().fromDate(eDate);
        return listRangeDate(sMoment, eMoment);
    }

    @Override
    public Moment clone() {
        return new Moment((GregorianCalendar) this.gc.clone());
    }
}