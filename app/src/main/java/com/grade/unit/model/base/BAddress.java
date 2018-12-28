package com.grade.unit.model.base;

import android.support.annotation.NonNull;

import com.grade.unit.util.IsEmpty;

/**
 * BAddress : 地址实体类
 * <p>
 * </> Created by ylwei on 2018/3/30.
 */
public class BAddress extends BStandardEntity {
  private BUcn province;// 省
  private BUcn city;// 市
  private BUcn district;// 区
  private BUcn street;// 街道
  private String detail;// 街道门牌信息
  private String remark;// 备注
  private String zipcode;// 邮政编码
  private String contacts;// 联系人
  private String mobile;// 手机号码

  public BUcn getProvince() {
    return province;
  }

  public void setProvince(BUcn province) {
    this.province = province;
  }

  public BUcn getCity() {
    return city;
  }

  public void setCity(BUcn city) {
    this.city = city;
  }

  public BUcn getDistrict() {
    return district;
  }

  public void setDistrict(BUcn district) {
    this.district = district;
  }

  public BUcn getStreet() {
    return street;
  }

  public void setStreet(BUcn street) {
    this.street = street;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getContacts() {
    return contacts;
  }

  public void setContacts(String contacts) {
    this.contacts = contacts;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  @NonNull
  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    if (!IsEmpty.object(province) && !IsEmpty.object(province.getName()))
      buffer.append(province.getName());
    if (!IsEmpty.object(city) && !IsEmpty.object(city.getName()))
      buffer.append(" ").append(city.getName());
    if (!IsEmpty.object(district) && !IsEmpty.object(district.getName()))
      buffer.append(" ").append(district.getName());
    if (!IsEmpty.object(street) && !IsEmpty.object(street.getName()))
      buffer.append(" ").append(street.getName());
    return buffer.toString();
  }
}
