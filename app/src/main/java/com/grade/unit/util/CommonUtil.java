package com.grade.unit.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * CommonUtil :
 * <p>
 * </> Created by ylwei on 2018/11/23.
 */
public class CommonUtil {

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
