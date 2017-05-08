package com.example.aedvance.finalcoins.util;


import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 一个烦死了的时间计算
 * <p>
 * Created by 鑫 on 2015/8/20.
 */
public class TimeUtil {
    /**
     * 时间long转datetime格式
     *
     * @param l long类型时间
     * @return String
     */
    public static String longToDateTime(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(l);
        return formatter.format(date);
    }


    /**
     * datetime格式转时间long
     *
     * @return long类型时间
     */
    public static long dateTimeToLong(String datetime) {
        if (TextUtils.isEmpty(datetime)) return 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date d = formatter.parse(datetime);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String toYMDStr(String datetime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date d = formatter.parse(datetime);
            return formatter2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前系统时间
     *
     * @return String
     */
    public static String getNowTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }

    public static String getNowTimeYMD() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
    }

    /**
     * 获取当前时间是星期几
     *
     * @return int [0~7]:星期日~星期六
     */
    public static int dayForWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取当前时间是星期几
     *
     * @param date string
     * @return 0~7
     */
    public static int dayForWeek(String date) {
        long dateLong = dateTimeToLong(date);
        return dayForWeek(dateLong);//0~7:星期日~星期六
    }

    /**
     * 获取当前时间是星期几
     *
     * @param l long
     * @return 0~7
     */
    public static int dayForWeek(long l) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(l);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;//0~7:星期日~星期六
    }

    /**
     * 获得当天零点时间
     *
     * @return
     */
    public static long getTodayZeroLong() {
        long oneDay = 24 * 60 * 60 * 1000; // 每天的毫秒数
        long now = System.currentTimeMillis();// 从1970-1-1 8点开始的毫秒数
        return now - ((now + 8 * 60 * 60 * 1000) % oneDay);
    }

    /**
     * 获得当天零点时间
     *
     * @return
     */
    public static long getTodayZeroLong(String date) {
        long oneDay = 24 * 60 * 60 * 1000; // 每天的毫秒数
        long now = dateTimeToLong(date);// 从1970-1-1 8点开始的毫秒数
        return now - ((now + 8 * 60 * 60 * 1000) % oneDay);
    }

    /**
     * long类型转00:00:00类型
     *
     * @param hardTime long
     * @return string
     */
    public static String getHardTime(long hardTime) {
        long hour = hardTime / 60 / 60 / 1000;
        long min = hardTime / 60 / 1000 % 60;
        long sec = hardTime / 1000 % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, sec);
    }


    public static Calendar datetime2Calendar(String datetime) {
        long time = dateTimeToLong(datetime);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    /**
     * 当前时间是否是星期一的八点
     *
     * @return
     */
    public static boolean isMon8Clock() {
        long now = System.currentTimeMillis();
        long zero = getTodayZeroLong();
        long eightHours = 11 * 60 * 60 * 1000;
        return dayForWeek() == 1 && now - zero >= eightHours && now - zero <= eightHours + 1000 * 60;
    }


    public static String getConstellationByDatetime(String date) {
        try {
            final String[] constellationArr = {"魔羯座", "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

            if (TextUtils.isEmpty(date) || date.equals("未设置")) {
                return "未知星座";
            }
            Calendar calendar = datetime2Calendar(date);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            final int[] constellationEdgeDay = {20, 18, 20, 20, 20, 21, 22, 22, 22, 22, 21, 21};
            int month = m;
            int day = d;
            if (day <= constellationEdgeDay[month - 1]) {
                month = month - 1;
            }
            if (month >= 0) {
                return constellationArr[month];
            }
            //default to return 魔羯
            return constellationArr[11];
        }catch (Exception e){
            e.printStackTrace();
            return "未知设置";
        }
    }


}
