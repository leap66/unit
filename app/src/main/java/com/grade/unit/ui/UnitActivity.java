package com.grade.unit.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.grade.unit.util.IsEmpty;
import com.grade.unit.util.KeyBoardUtil;
import com.grade.unit.widget.LoadingLayout;

/**
 * UnitActivity :
 * <p>
 * </> Created by ylwei on 2018/3/26.
 */
public abstract class UnitActivity extends AppCompatActivity {
  private boolean keyboardAutoHide = true;
  protected Context context;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    initComponent();
    createEventHandlers();
    loadData();
  }

  // 初始化界面控件
  protected abstract void initComponent();

  // 初次加载数据
  protected abstract void loadData();

  // 界面事件响应
  protected void createEventHandlers() {
  }

  // 设置软键盘是否自动隐藏
  protected void setKeyboardAutoHide(boolean b) {
    this.keyboardAutoHide = b;
  }

  // 点击空白处隐藏软键盘
  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      View v = getCurrentFocus();
      if (isEdt(v, ev) && keyboardAutoHide)
        KeyBoardUtil.keyShow(v, false);
      return super.dispatchTouchEvent(ev);
    }
    if (getWindow().superDispatchTouchEvent(ev))
      return getWindow().superDispatchTouchEvent(ev);
    return onTouchEvent(ev);
  }

  // 判断当前焦点是否是输入框
  private boolean isEdt(View v, MotionEvent event) {
    if (v instanceof EditText) {
      int[] leftTop = {
          0, 0};
      v.getLocationInWindow(leftTop);
      int left = leftTop[0];
      int top = leftTop[1];
      int bottom = top + v.getHeight();
      int right = left + v.getWidth();
      return !(event.getX() > left && event.getX() < right && event.getY() > top
          && event.getY() < bottom);
    }
    return false;
  }

  protected LoadingLayout getLoadingLayout() {
    return null;
  }

  protected void startLoading() {
    if (!IsEmpty.object(getLoadingLayout()))
      getLoadingLayout().startLoading();
  }

  protected void stopLoading() {
    if (!IsEmpty.object(getLoadingLayout()))
      getLoadingLayout().stopLoading();
  }
}
