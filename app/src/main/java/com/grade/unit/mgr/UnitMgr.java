package com.grade.unit.mgr;

import android.content.Context;

/**
 * UnitMgr : Base组件管理类
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class UnitMgr {

  public static void init(Context context) {
    // 初始化Context
    UnitContext.init(context);
    // 初始化缓存组件
    UnitStorage.init();
  }
}
