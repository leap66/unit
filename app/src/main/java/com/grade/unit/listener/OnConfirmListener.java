package com.grade.unit.listener;

/**
 * OnConfirmListener : 确定事件监听
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public interface OnConfirmListener<V, T> {
  void onConfirm(V view, T data);
}
