package com.grade.unit.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;

/**
 * CommonUtil :
 * <p>
 * </> Created by ylwei on 2018/11/23.
 */
public class CommonUtil {

  // 获取屏幕宽度
  public static int getScreenWidth() {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    return metrics.widthPixels;
  }

  // 获取屏幕高度
  public static int getScreenHeight() {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    return metrics.heightPixels;
  }

  // 跳至拨号界面
  public static boolean dial(Context context, String phoneNumber) {
    try {
      context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
