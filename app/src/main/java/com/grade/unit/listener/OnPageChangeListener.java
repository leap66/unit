package com.grade.unit.listener;

import android.support.v4.view.ViewPager;

/**
 * OnPageChangeListener : ViewPager滑动监听
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public abstract class OnPageChangeListener implements ViewPager.OnPageChangeListener {

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override
  public abstract void onPageSelected(int position);

  @Override
  public void onPageScrollStateChanged(int state) {
  }

}
