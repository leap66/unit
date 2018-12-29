package com.grade.unit.mgr;

import android.content.Context;

/**
 * BaseMgr : Base组件管理类
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class BaseMgr {

  public static void init(Context context) {
    // 初始化Context
    ContextMgr.init(context);
    // 初始化缓存组件
    BaseStorageMgr.init();
  }
}
