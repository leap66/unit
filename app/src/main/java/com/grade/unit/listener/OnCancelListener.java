package com.grade.unit.listener;

/**
 * OnCancelListener : 取消事件监听
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public interface OnCancelListener<V, T> {
  void onCancel(V view, T data);
}
