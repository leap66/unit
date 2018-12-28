package com.grade.unit.mgr;

/**
 * TokenMgr : Token 管理器
 * <p>
 * </> Created by ylwei on 2018/3/1.
 */
public class TokenMgr {

  // key
  private static String KEY_TOKEN = "userToken";
  private static String token;

  // 初始化
  public static void init() {
    KEY_TOKEN = ContextMgr.getInstance().getPackageName() + KEY_TOKEN;
    TokenMgr.token = StorageMgr.get(KEY_TOKEN, StorageMgr.LEVEL_GLOBAL);
  }

  // 更新
  public static void update(String token) {
    TokenMgr.token = token;
    StorageMgr.set(KEY_TOKEN, token, StorageMgr.LEVEL_GLOBAL);
  }

  // 清除
  public static void clear() {
    StorageMgr.set(KEY_TOKEN, null, StorageMgr.LEVEL_GLOBAL);
    TokenMgr.token = null;
  }

  // 获取
  public static String getUserToken() {
    return TokenMgr.token;
  }

  // 判断是否有都登录记录
  public static boolean hasUser() {
    return getUserToken() != null;
  }
}
