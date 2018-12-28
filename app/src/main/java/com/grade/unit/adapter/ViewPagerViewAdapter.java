package com.grade.unit.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPagerViewAdapter :
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public class ViewPagerViewAdapter extends PagerAdapter {

  private List<View> viewList = new ArrayList<>();

  public void setViewList(List<View> viewList) {
    this.viewList = viewList;
  }

  @Override
  public int getCount() {
    return viewList.size();
  }

  @Override
  public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
    return arg0 == arg1;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView(viewList.get(position));
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    container.addView(viewList.get(position));
    return viewList.get(position);
  }
}
