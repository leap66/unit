package com.grade.unit.model;


import com.grade.unit.model.base.BEntity;

/**
 * BaseEntity : 基础实体类
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class BaseEntity extends BEntity {
  private String user;
  private String name;
  private String expert;
  private String normal;
  private String remark;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExpert() {
    return expert;
  }

  public void setExpert(String expert) {
    this.expert = expert;
  }

  public String getNormal() {
    return normal;
  }

  public void setNormal(String normal) {
    this.normal = normal;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
