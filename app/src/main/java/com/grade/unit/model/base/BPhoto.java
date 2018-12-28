package com.grade.unit.model.base;

/**
 * BPhoto :
 * <p>
 * </> Created by ylwei on 2018/6/13.
 */
public class BPhoto extends BEntity {
  private String url;
  private String name;
  private String path;
  private boolean temp;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public boolean isTemp() {
    return temp;
  }

  public void setTemp(boolean temp) {
    this.temp = temp;
  }
}
