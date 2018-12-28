package com.grade.unit.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * KeyBoardUtil : 键盘显示工具
 * <p>
 * </> Created by ylwei on 2018/3/26.
 */
public class KeyBoardUtil {

  // 延时显示软键盘
  public static void showSoftKey(final EditText inputField) {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      public void run() {
        KeyBoardUtil.keyShow(inputField, true);
      }
    }, 500);
  }

  /**
   * 显示或取消软键盘
   *
   * @param view   上下文
   * @param isShow 是否显示软键盘
   */
  public static void keyShow(View view, boolean isShow) {
    InputMethodManager imm = (InputMethodManager) view.getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    if (isShow) {
      // 强制显示软键盘
      imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    } else {
      // 强制取消软键盘
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }
}
