package com.grade.unit.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.grade.unit.R;
import com.grade.unit.databinding.DialogBaseLoadingBinding;

/**
 * LoadingDialog : 加载框
 * <p>
 * </> Created by ylwei on 2018/3/28.
 */
public class LoadingDialog extends Dialog {
  private DialogBaseLoadingBinding binding;
  private AnimationDrawable animationDrawable;

  public LoadingDialog(@NonNull Context context) {
    super(context, R.style.style_dialog);
    binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
        R.layout.dialog_base_loading, null, false);
    animationDrawable = (AnimationDrawable) binding.baseLoading.getDrawable();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    setCancelable(false);
    setCanceledOnTouchOutside(false);
  }

  @Override
  public void show() {
    animationDrawable.start();
    super.show();
  }

  @Override
  public void dismiss() {
    animationDrawable.stop();
    super.dismiss();
  }
}
