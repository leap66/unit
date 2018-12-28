package com.grade.unit.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * DateUtil : 日期格式化工具
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class DateUtil {
  public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static String DEFAULT_DATE_FORMAT_1 = "yyyy-MM-dd";
  public static String DEFAULT_DATE_FORMAT_2 = "HH:mm";
  public static String DEFAULT_DATE_FORMAT_3 = "EEEE";
  public static String DEFAULT_DATE_FORMAT_4 = "yyyy年M月d日";

  public static String format(Date date, String format) {
    if (IsEmpty.object(date))
      return "";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
    return sdf.format(date);
  }

  public static Date parse(String date, String format) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
      return sdf.parse(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // 获取某一天的开始时间或结束时间(最大为当前时间)
  public static Date getStandardDate(Date date, boolean isStart) {
    if (IsEmpty.object(date))
      date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    Date start = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.MILLISECOND, -1);
    Date end = calendar.getTime();
    if (end.getTime() > new Date().getTime()) {
      end = new Date();
    }
    if (isStart) {
      return start;
    } else {
      return end;
    }
  }
}
