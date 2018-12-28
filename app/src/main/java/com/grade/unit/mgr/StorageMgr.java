package com.grade.unit.mgr;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.grade.unit.model.BaseEntity;
import com.grade.unit.util.GsonUtil;
import com.grade.unit.util.IsEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * StorageMgr : 物理缓存管理
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class StorageMgr {
  public static final String LEVEL_USER = "storage_user";// 用户级别（必需登录后使用）（默认级别）
  public static final String LEVEL_GLOBAL = "storage_global";// 全局级别
  public static final String LEVEL_EXPERT = "storage_expert";// 专业级缓存
  public static final String LEVEL_NORMAL = "storage_normal";// 普通级缓存
  private static SharedPreferences storage;

  // 初始化缓存管理
  public static void init() {
    Context context = ContextMgr.getInstance();
    storage = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
  }

  /**
   * 缓存数据 t 级别 user
   */
  public static <T> void set(String key, T t) throws RuntimeException {
    set(key, t, StorageMgr.LEVEL_USER);
  }

  /**
   * 缓存数据 t 级别 level
   */
  public static <T> void set(String key, T t, String level) throws RuntimeException {
    set(key, GsonUtil.toJson(t), level);
  }

  public static void set(String key, String value, String level) throws RuntimeException {
    setStorage(getKey(key, level), value);
  }

  /**
   * 获取对应key的缓存
   */
  public static <T> T get(String key, Class<T> c) {
    return get(key, c, StorageMgr.LEVEL_USER);
  }

  /**
   * 获取对应key的缓存
   */
  public static <T> T get(String key, Class<T> c, String level) {
    String value = get(key, level);
    if (value == null) {
      return null;
    }
    return GsonUtil.parse(value, c);
  }

  /**
   * 获取对应key的缓存
   */
  public static String get(String key, String level) {
    return getStorage(getKey(key, level));
  }

  /**
   * 获取对应key的缓存
   */
  public static boolean getBoolean(String key, String level, boolean defaultValue) {
    String value = getStorage(getKey(key, level));
    if (IsEmpty.string(value))
      return defaultValue;
    return Boolean.valueOf(value);
  }

  /**
   * 获取对应key的缓存
   */
  public static <T> List<T> getList(String key, Class<T> c, String level) {
    String value = get(key, level);
    if (value == null) {
      return null;
    }
    List<T> list = new ArrayList<>();
    JsonArray array = new JsonParser().parse(value).getAsJsonArray();
    for (JsonElement elem : array) {
      list.add(GsonUtil.parse(elem.toString(), c));
    }
    return list;
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

  // 获取key
  private static String getKey(String key, String level) {
    String k = "";
    if (!IsEmpty.string(key) && !IsEmpty.string(level)) {
      BaseEntity temp = BaseMgr.getBaseEntity();
      switch (level) {
        case LEVEL_GLOBAL:
          break;
        case LEVEL_USER:
          k += temp.getUser();
          break;
        case LEVEL_EXPERT:
          k += temp.getExpert();
          break;
        case LEVEL_NORMAL:
          k += temp.getNormal();
          break;
      }
      k += key;
    }
    return k;
  }
}
