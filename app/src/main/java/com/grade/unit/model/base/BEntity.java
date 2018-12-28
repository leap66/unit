package com.grade.unit.model.base;

import java.io.Serializable;

/**
 * BEntity : 基础继承类
 * <p>
 * </> Created by ylwei on 2018/3/30.
 */
public class BEntity implements Serializable {
  private String id; // 唯一标示ID

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
