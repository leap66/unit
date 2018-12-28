package com.grade.unit.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * OnTextChangedListener :
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public abstract class OnTextChangedListener implements TextWatcher {

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public abstract void onTextChanged(CharSequence s, int start, int before, int count);

  @Override
  public void afterTextChanged(Editable s) {
  }
}
