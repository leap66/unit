package com.grade.unit.listener;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * ActivityLifecycleListener : Application.ActivityLifecycleCallbacks
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public abstract class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {
  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {

  }

  @Override
  public void onActivityStarted(Activity activity) {

  }

  @Override
  public abstract void onActivityResumed(Activity activity);

  @Override
  public void onActivityPaused(Activity activity) {

  }

  @Override
  public void onActivityStopped(Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

  }

  @Override
  public void onActivityDestroyed(Activity activity) {

  }
}
