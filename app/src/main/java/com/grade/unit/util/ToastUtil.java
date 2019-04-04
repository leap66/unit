package com.grade.unit.util;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.grade.unit.R;
import com.grade.unit.databinding.ItemToastBinding;
import com.grade.unit.mgr.UnitContext;

/**
 * Toast 格式化工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class ToastUtil {
  private static Toast toastSingle;

  public static void showHint(int stringResId) {
    String s = UnitContext.getInstance().getResources().getString(stringResId);
    showHint(s);
  }

  public static void showHint(String text) {
    showToast(text, 0);
  }

  public static void showFailure(int stringResId) {
    String text = UnitContext.getInstance().getResources().getString(stringResId);
    showFailure(text);
  }

  public static void showFailure(String text) {
    showToast(text, R.mipmap.ic_toast_warning);
  }

  public static void showSuccess(int stringResId) {
    String text = UnitContext.getInstance().getResources().getString(stringResId);
    showSuccess(text);
  }

  public static void showSuccess(String text) {
    showToast(text, R.mipmap.ic_toast_success);
  }

  private static void showToast(String text, int imageResource) {
    Context context = UnitContext.getInstance();
    ItemToastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
        R.layout.item_toast, null, false);
    Toast toast = new Toast(context);
    toast.setDuration(Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    if (0 == imageResource) {
      binding.imageIv.setVisibility(View.GONE);
    }
    binding.imageIv.setImageResource(imageResource);
    binding.titleTv.setText(text);
    binding.titleTv.setMaxWidth(Resources.getSystem().getDisplayMetrics().widthPixels * 3 / 5);
    toast.setView(binding.getRoot());
    if (!IsEmpty.object(toastSingle)) {
      toastSingle.cancel();
    }
    toast.show();
    toastSingle = toast;
  }
}
