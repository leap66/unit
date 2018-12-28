package com.grade.unit.util;

import android.util.Log;

/**
 * LogUtil :
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public class LogUtil {

  public static void i(String tag, String info) {
    Log.i("leap" + tag, info);
  }

  public static void e(String tag, String info) {
    Log.e("leap" + tag, info);
  }

  public static void e(String tag, String info, Throwable tr) {
    Log.e("leap" + tag, info, tr);
  }
}
