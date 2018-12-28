package com.grade.unit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPagerAdapter :
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

  private List<Fragment> fragmentList = new ArrayList<>();

  public ViewPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
    super(fm);
    this.fragmentList = fragmentList;
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }

  public void setFragmentList(List<Fragment> fragmentList) {
    this.fragmentList = fragmentList;
  }
}
