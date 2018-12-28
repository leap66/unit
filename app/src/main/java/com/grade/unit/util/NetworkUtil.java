package com.grade.unit.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

import com.grade.unit.mgr.ContextMgr;

/**
 * NetworkUtil : 网络连接判断工具
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public class NetworkUtil {

  public static boolean isConnected() {
    Context context = ContextMgr.getInstance();
    ConnectivityManager connMgr = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.isConnected());
  }

  public static boolean isWifiConnected() {
    return isConnected(ConnectivityManager.TYPE_WIFI);
  }

  public static boolean isMobileConnected() {
    return isConnected(ConnectivityManager.TYPE_MOBILE);
  }

  private static boolean isConnected(int type) {
    Context context = ContextMgr.getInstance();
    ConnectivityManager connMgr = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      NetworkInfo networkInfo = connMgr.getNetworkInfo(type);
      return networkInfo != null && networkInfo.isConnected();
    } else {
      return isConnected(connMgr, type);
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private static boolean isConnected(@NonNull ConnectivityManager connMgr, int type) {
    Network[] networks = connMgr.getAllNetworks();
    NetworkInfo networkInfo;
    for (Network mNetwork : networks) {
      networkInfo = connMgr.getNetworkInfo(mNetwork);
      if (networkInfo != null && networkInfo.getType() == type && networkInfo.isConnected()) {
        return true;
      }
    }
    return false;
  }
}
