package com.grade.unit.callback;

/**
 * ServiceCallback :
 * <p>
 * </> Created by ylwei on 2018/5/18.
 */
public interface ServiceCallback {

  void failure(String errorMsg);

  void success(String jsonStr);
}
