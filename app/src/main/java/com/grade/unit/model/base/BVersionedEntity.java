package com.grade.unit.model.base;

/**
 * BVersionedEntity : 带有版本号的基本继承类
 * <p>
 * </> Created by ylwei on 2018/3/30.
 */
public class BVersionedEntity extends BEntity {
  private long version;

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
}
