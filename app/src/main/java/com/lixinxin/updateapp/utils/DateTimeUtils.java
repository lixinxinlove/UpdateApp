package com.lixinxin.updateapp.utils;

import android.text.TextUtils;

import com.eventmosh.evente.entity.EventWeekEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateTimeUtils {
    public static final int DATETIME_FIELD_REFERSH = 20;
    public static final String HH_mm = "HH:mm";
    public static final String MM_dd = "MM-dd";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String MM_Yue_dd_Ri = "MM月dd日";
    public static final String MM_Yue_dd_Ri_HH_mm = "MM月dd日HH:mm";
    public static final String MM_yy = "MM/yy";
    public static final String M_Yue_d_Ri = "M月d日";
    public static final long ONE_DAY = 86400000L;
    public static final long ONE_HOUR = 3600000L;
    public static final long ONE_MINUTE = 60000L;
    public static final long ONE_SECOND = 1000L;
    private static final String[] PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyyMMdd"};
    public static final String dd_MM = "dd/MM";
    public static boolean hasServerTime = false;
    public static long tslgapm = 0L;
    public static String tss;
    private static String[] weekdays = {"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private static String[] weekdays1 = {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyy_MM = "yyyy-MM";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yy_MM_dd_HH_mm = "yy/MM/dd HH:mm";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_Nian_MM_Yue_dd_Ri = "yyyy年MM月dd日";


    public static final String yMd = "yyyy/MM/dd";
    public static final String MMdd = "MM/dd";

    public static void cleanCalendarTime(Calendar paramCalendar) {
        paramCalendar.set(11, 0);
        paramCalendar.set(12, 0);
        paramCalendar.set(13, 0);
        paramCalendar.set(14, 0);
    }

    private static String fixDateString(String paramString) {
        if (TextUtils.isEmpty(paramString))
            return paramString;
        String[] arrayOfString = paramString.split("[年月日]");
        if (arrayOfString.length == 1)
            arrayOfString = paramString.split("-");
        for (int i = 0; i < 3; i++) {
            if (arrayOfString[i].length() != 1)
                continue;
            arrayOfString[i] = ("0" + arrayOfString[i]);
        }
        return arrayOfString[0] + "-" + arrayOfString[1] + "-" + arrayOfString[2];
    }

    public static <T> Calendar getCalendar(T paramT) {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.setLenient(false);
        if (paramT == null)
            return null;
        if ((paramT instanceof Calendar)) {
            localCalendar1.setTimeInMillis(((Calendar) paramT).getTimeInMillis());
            return localCalendar1;
        }
        if ((paramT instanceof Date)) {
            localCalendar1.setTime((Date) paramT);
            return localCalendar1;
        }
        if ((paramT instanceof Long)) {
            localCalendar1.setTimeInMillis(((Long) paramT).longValue());
            return localCalendar1;
        }
        if ((paramT instanceof String)) {
            String str = (String) paramT;
            try {
                if (Pattern.compile("\\d{4}年\\d{1,2}月\\d{1,2}日").matcher(str).find()) {
                    str = fixDateString(str);
                    return getCalendarByPattern(str, "yyyy-MM-dd");
                }
                Calendar localCalendar2 = getCalendarByPatterns(str, PATTERNS);
                return localCalendar2;
            } catch (Exception localException) {
                try {
                    localCalendar1.setTimeInMillis(Long.valueOf(str).longValue());
                    return localCalendar1;
                } catch (NumberFormatException localNumberFormatException) {
                    throw new IllegalArgumentException(localNumberFormatException);
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public static <T> Calendar getCalendar(T paramT, Calendar paramCalendar) {
        if (paramT != null)
            try {
                Calendar localCalendar = getCalendar(paramT);
                return localCalendar;
            } catch (Exception localException) {
            }
        return (Calendar) paramCalendar.clone();
    }

    public static Calendar getCalendarByPattern(String paramString1, String paramString2) {
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2, Locale.US);
            localSimpleDateFormat.setLenient(false);
            Date localDate = localSimpleDateFormat.parse(paramString1);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setLenient(false);
            localCalendar.setTimeInMillis(localDate.getTime());
            return localCalendar;
        } catch (Exception localException) {
            throw new IllegalArgumentException(localException);
        }

    }

    public static Calendar getCalendarByPatterns(String paramString, String[] paramArrayOfString) {
        int i = paramArrayOfString.length;
        int j = 0;
        while (j < i) {
            String str = paramArrayOfString[j];
            try {
                Calendar localCalendar = getCalendarByPattern(paramString, str);
                return localCalendar;
            } catch (Exception localException) {
                j++;
            }
        }
        throw new IllegalArgumentException();
    }

    public static Calendar getCurrentDateTime() {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setLenient(false);
        if (hasServerTime)
            localCalendar.setTimeInMillis(localCalendar.getTimeInMillis() + tslgapm);
        return localCalendar;
    }

    public static Calendar getDateAdd(Calendar paramCalendar, int paramInt) {
        if (paramCalendar == null)
            return null;
        Calendar localCalendar = (Calendar) paramCalendar.clone();
        localCalendar.add(5, paramInt);
        return localCalendar;
    }

    public static <T> int getIntervalDays(T paramT1, T paramT2) {
        Calendar localCalendar1 = getCalendar(paramT1);
        Calendar localCalendar2 = getCalendar(paramT2);
        cleanCalendarTime(localCalendar1);
        cleanCalendarTime(localCalendar2);
        return (int) getIntervalTimes(localCalendar1, localCalendar2, 86400000L);
    }

    public static int getIntervalDays(String paramString1, String paramString2, String paramString3) {
        if ((paramString1 == null) || (paramString2 == null))
            return 0;
        return getIntervalDays(getCalendarByPattern(paramString1, paramString3),
                getCalendarByPattern(paramString2, paramString3));
    }

    public static long getIntervalTimes(Calendar paramCalendar1, Calendar paramCalendar2, long paramLong) {
        if ((paramCalendar1 == null) || (paramCalendar2 == null))
            return 0L;
        return Math.abs(paramCalendar1.getTimeInMillis() - paramCalendar2.getTimeInMillis()) / paramLong;
    }

    public static Calendar getLoginServerDate() {
        return getCalendar(tss);
    }

    public static String getWeekDayFromCalendar(Calendar paramCalendar) {
        if (paramCalendar == null)
            throw new IllegalArgumentException();
        return weekdays[paramCalendar.get(7)];
    }

    public static String getWeekDayFromCalendar1(Calendar paramCalendar) {
        if (paramCalendar == null)
            throw new IllegalArgumentException();
        return weekdays1[paramCalendar.get(7)];
    }

    public static boolean isLeapyear(String paramString) {
        Calendar localCalendar = getCalendar(paramString);
        if (localCalendar != null) {
            int i = localCalendar.get(1);
            return (i % 4 == 0) && ((i % 100 != 0) || (i % 400 == 0));
        }
        return false;
    }

    public static boolean isRefersh(long paramLong) {
        return isRefersh(1200000L, paramLong);
    }

    public static boolean isRefersh(long paramLong1, long paramLong2) {
        return new Date().getTime() - paramLong2 >= paramLong1;
    }

    public static String printCalendarByPattern(Calendar paramCalendar, String paramString) {
        if ((paramCalendar == null) || (paramString == null))
            return null;
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString, Locale.US);
        localSimpleDateFormat.setLenient(false);
        return localSimpleDateFormat.format(paramCalendar.getTime());
    }

    /**
     * 时间戳
     *
     * @return
     */
    public static long getTimeStamp() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * 获得指定时间的时间戳
     *
     * @param dateString
     * @param format     yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static long getTime(String dateString, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date;
        try {
            date = df.parse(dateString);
            long time = date.getTime() / 1000;
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 两个时间之间的天数 ps 毫秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(long date1, long date2) {
        if (date1 == 0)
            return 0;
        if (date2 == 0)
            return 0;
        // 转换为标准时间

        long day = (date2 - date1) / (24 * 60 * 60 * 1000);
        return day;
    }

    // 获取当天时间
    public String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    /**
     * 若干天以后的时间戳
     *
     * @param nowTime
     * @param day
     * @return
     */
    public static long calculationDate(long nowTime, int day) {
        return nowTime + day * ONE_DAY;
    }

    /**
     * 精确到秒
     *
     * @param nowTime
     * @param day
     * @return
     */
    public static long calculationDateTemp(long nowTime, int day) {
        return nowTime + day * ONE_DAY / 1000;
    }

    public static Calendar getToadyCalendar() {
        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(getTimeStamp());
        calendar.setTime(startDate);
        return calendar;
    }

    /**
     * 根据时间戳格式化日期
     *
     * @param time
     * @return
     */
    public static String timeForDate(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获得当天整点的时间戳
     *
     * @return
     */
    public static long getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() / 1000) - 60 * 60 * 24;
    }

    public static String getWeekDayFromCalendar1(long paramCalendar) {
        Calendar c = Calendar.getInstance();
        Date date = new Date(paramCalendar);
        c.setTime(date);

        if (c == null)
            throw new IllegalArgumentException();
        return weekdays1[c.get(7)];
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String getFormatTime(long time) {

        Date date = new Date(time);
        long day = getDays(time, System.currentTimeMillis());

        if (day == 0) {
            if (getTimesmorning() - time < 0) {
                return timeForDate(time, HH_mm);
            }
            return "昨天";

        } else if (day == 1) {
            return "昨天";
        } else if (day == 2) {
            return getWeekDayFromCalendar1(time);
        } else if (day == 3) {
            return getWeekDayFromCalendar1(time);
        } else if (day == 4) {
            return getWeekDayFromCalendar1(time);
        } else if (day == 5) {
            return getWeekDayFromCalendar1(time);
        } else if (day == 6) {
            return getWeekDayFromCalendar1(time);
        } else if (day == 30) {
            return "一个月前";
        } else {
            return timeForDate(time, MM_Yue_dd_Ri);
        }
    }


    //获得当天0点时间
    public static long getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }


    //获得当天24点时间
    public static long getTimenight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 根据年份获得周对象
     *
     * @param year
     * @return
     */
    public static List<EventWeekEntity> getWeekOfYear(int year) {
        int weekNum = getMaxWeekNumOfYear(year);
        SimpleDateFormat ssdf = new SimpleDateFormat("M月d");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<EventWeekEntity> list = new ArrayList<>();
        for (int i = 1; i <= weekNum; i++) {
            EventWeekEntity week = new EventWeekEntity();
            String firstDay = ssdf.format(getFirstDayOfWeek(year, i));
            String lastDay = ssdf.format(getLastDayOfWeek(year, i));
            week.setYear(year);
            week.setWeekOfYear(i);
            week.setFirstDayOfWeek(sdf.format(getFirstDayOfWeek(year, i)));
            week.setLastDayOfWeek(sdf.format(getLastDayOfWeek(year, i)));
            week.setShowLab("第" + i + "周(" + firstDay + "-" + lastDay + ")");
            list.add(week);
        }

        Calendar calender = Calendar.getInstance();
        int mYear = calender.get(Calendar.YEAR);
        if (year == mYear) {
            int week = calender.get(Calendar.WEEK_OF_YEAR);

            for (int i = list.size() - 1; i >= week; i--) {

                list.remove(i);
            }

        }

        return list;
    }


    /**
     * 获取当前时间所在年的最大周数
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    /*
     *获取当前时间所在年的周数
      */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /*
    *获取某年的第几周的开始日期
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }


    /*
     *获取当前时间所在周的开始日期
      */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }


    // 获取某年的第几周的结束日期
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }


    /*
    *获取当前时间所在周的结束日期
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }


    public static int getMonthSum(Calendar calendar) {

        Calendar calendar2 = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int month2 = calendar2.get(Calendar.MONTH);

        int year = calendar.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);

        int sum = (year2 - year) * 12 + month2 - month;
        return sum;
    }


    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String StrToDateFormat(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String string = format1.format(date);
        return string;
    }


    /**
     * 格式化日期
     *
     * @param str
     * @param format
     * @return
     */
    public static String stringDateFormat(String str, String format) {

        SimpleDateFormat format0 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = format0.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat format1 = new SimpleDateFormat(format);
        String string = format1.format(date);
        return string;
    }


    /**
     * 获取当前时间是第几周
     *
     * @param strDate
     * @return
     */
    public static int getWeekOfYear(String strDate) {
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = format0.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        int week = calender.get(Calendar.WEEK_OF_YEAR);

        return week;
    }

    /**
     * 获取当前时间是第
     *
     * @param strDate
     * @return
     */
    public static String getWeekOfMonth(String strDate) {

        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        SimpleDateFormat format0 = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = format0.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        int week = calender.get(Calendar.DAY_OF_WEEK) - 1;

        return weeks[week];

    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTiem() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


}