package com.grade.unit.mgr;

import android.content.Context;
import android.content.SharedPreferences;

import com.grade.unit.util.GsonUtil;

/**
 * StorageMgr : 物理缓存管理
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class BaseStorageMgr {
  private static SharedPreferences storage;

  // 初始化缓存管理
  public static void init() {
    Context context = ContextMgr.getInstance();
    storage = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
  }

  // 缓存数据 t 级别 user
  public static <T> void set(String key, T t) throws RuntimeException {
    setStorage(key, GsonUtil.toJson(t));
  }

  // 获取对应key的缓存
  public static String get(String key) {
    return getStorage(key);
  }

  // 获取对应key的缓存
  public static <T> T get(String key, Class<T> c) {
    String value = get(key);
    if (value == null) {
      return null;
    }
    return GsonUtil.parse(value, c);
  }

  // 设置缓存信息
  private static void setStorage(String key, String value) {
    SharedPreferences.Editor editor = storage.edit();
    editor.putString(key, value);
    editor.apply(); // 先提交内存，再异步提交硬盘
    // editor.commit(); //同步提交内存及硬盘
  }

  // 获取缓存信息
  private static String getStorage(String key) {
    return storage.getString(key, null);
  }
}
