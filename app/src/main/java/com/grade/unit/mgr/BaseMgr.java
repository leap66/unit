package com.grade.unit.mgr;

import android.content.Context;

import com.grade.unit.model.BaseEntity;

/**
 * BaseMgr : Base组件管理类
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class BaseMgr {
  private static String Key_Entity;
  private static BaseEntity entity;

  public static void init(Context context) {
    // 初始化Context
    ContextMgr.init(context);
    // 初始化缓存组件
    StorageMgr.init();
    Key_Entity = context.getPackageName() + ".BaseEntity";
    entity = StorageMgr.get(Key_Entity, BaseEntity.class, StorageMgr.LEVEL_GLOBAL);
    if (entity == null)
      entity = new BaseEntity();
  }

  public static void updateEntity(BaseEntity baseEntity) {
    entity = baseEntity;
    StorageMgr.set(Key_Entity, entity, StorageMgr.LEVEL_GLOBAL);
  }

  public static BaseEntity getBaseEntity() {
    return entity;
  }
}
