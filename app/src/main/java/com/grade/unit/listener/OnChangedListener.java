package com.grade.unit.listener;

/**
 * OnChangedListener : 数据变化监听
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public interface OnChangedListener<T> {
  void onChange(T oldValue, T newValue);
}
