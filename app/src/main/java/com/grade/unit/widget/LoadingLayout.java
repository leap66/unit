package com.grade.unit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.grade.unit.R;

import java.util.HashMap;
import java.util.Map;

/**
 * LoadingLayout : 加载layout
 * <p>
 * </> Created by ylwei on 2018/3/30.
 */
public class LoadingLayout extends FrameLayout {
  private final Integer LOADING = 0;
  private final Integer CONTENT = 1;
  private AnimationDrawable animationDrawable;
  private Integer currentIndex = 2;
  private boolean lock = true;
  private View loadingView;

  @SuppressLint("UseSparseArrays")
  protected Map<Integer, View> viewMap = new HashMap<>();

  public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    initView();
    show(CONTENT);
  }

  @Override
  protected void onDetachedFromWindow() {
    animationDrawable.stop();
    super.onDetachedFromWindow();
  }

  /**
   * 初始化View
   */
  private void initView() {
    loadingView = View.inflate(getContext(), R.layout.dialog_base_loading, null);
    ImageView imageView = loadingView.findViewById(R.id.base_loading);
    animationDrawable = (AnimationDrawable) imageView.getDrawable();
    animationDrawable.start();
    loadingView.setClickable(lock);
    viewMap.put(LOADING, loadingView);
    addView(viewMap.get(LOADING));
  }

  // 启动加载
  public void startLoading() {
    show(LOADING);
  }

  // 停止加载
  public void stopLoading() {
    show(CONTENT);
  }

  /**
   * 展示某一种View
   */
  private void show(@NonNull Integer state) {
    if (currentIndex.equals(state))
      return;
    viewMap.get(LOADING).setVisibility(LOADING.equals(state) ? VISIBLE : GONE);
    currentIndex = state;
  }

  public void setLock(boolean lock) {
    this.lock = lock;
    if (loadingView != null)
      loadingView.setClickable(lock);
  }
}
