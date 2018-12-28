package com.grade.unit.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.grade.unit.mgr.ContextMgr;

/**
 * DensityUtil : 手机分辨率转换工具
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public class DensityUtil {

  /**
   * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
   */
  public static int dip2px(float dpValue) {
    Context context = ContextMgr.getInstance();
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  /**
   * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
   */
  public static int px2dip(float pxValue) {
    Context context = ContextMgr.getInstance();
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  private DensityUtil() {
    throw new UnsupportedOperationException("单位工具类不能初始化对象");
  }

  /**
   * dp转px
   */
  public static int dp2px(float dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
        Resources.getSystem().getDisplayMetrics());
  }

  /**
   * sp转px
   */
  public static int sp2px(float spVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
        Resources.getSystem().getDisplayMetrics());
  }

  /**
   * px转dp
   */
  public static float px2dp(float pxVal) {
    final float scale = Resources.getSystem().getDisplayMetrics().density;
    return (pxVal / scale);
  }

  /**
   * px转sp
   */
  public static float px2sp(float pxVal) {
    return (pxVal / Resources.getSystem().getDisplayMetrics().scaledDensity);
  }

}