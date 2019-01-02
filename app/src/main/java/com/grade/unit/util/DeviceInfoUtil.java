package com.grade.unit.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.grade.unit.mgr.UnitContext;

import org.json.JSONException;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

/**
 * DeviceInfoUtil : 设备基础信息获取
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public class DeviceInfoUtil {

  private static final String PREFS_FILE = "gank_device_id.xml";
  private static final String PREFS_DEVICE_ID = "gank_device_id";
  private static final String UN_PERMISSION = "无权限";
  private static String uuid;

  /**
   * 获取屏幕宽度
   */
  public static int getScreenWidth() {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    return metrics.widthPixels;
  }

  /**
   * 获取屏幕高度
   */
  public static int getScreenHeight() {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    return metrics.heightPixels;
  }

  /**
   * 获取国际移动用户识别码
   */
  @SuppressLint("HardwareIds")
  public static String getImei() {
    Context context = UnitContext.getInstance();
    if (ContextCompat.checkSelfPermission(context,
        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
      return UN_PERMISSION;
    return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
  }

  /**
   * 获取移动设备国际身份码
   */
  @SuppressLint("HardwareIds")
  public static String getImsi() {
    Context context = UnitContext.getInstance();
    if (ContextCompat.checkSelfPermission(context,
        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
      return UN_PERMISSION;
    return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
        .getSubscriberId();
  }

  /**
   * 获取wifiMac地址
   */
  public static String getWifiMac() {
    String str = "";
    String macSerial = "";
    try {
      Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
      InputStreamReader ir = new InputStreamReader(pp.getInputStream());
      LineNumberReader input = new LineNumberReader(ir);

      for (; null != str; ) {
        str = input.readLine();
        if (str != null) {
          macSerial = str.trim();// 去空格
          break;
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    if ("".equals(macSerial)) {
      try {
        return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
      } catch (Exception e) {
        e.printStackTrace();

      }

    }
    return macSerial;
  }

  @SuppressLint("WifiManagerPotentialLeak")
  private static WifiInfo getWifiInfo() {
    Context context = UnitContext.getInstance().getApplicationContext();
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = null;
    if (null != wifiManager) {
      info = wifiManager.getConnectionInfo();
    }
    return info;
  }

  /**
   * 获取唯一标识码
   */
  @SuppressLint({
      "HardwareIds", "ApplySharedPref", "MissingPermission"})
  public synchronized static String getUDID() {
    Context mContext = UnitContext.getInstance();
    if (uuid == null) {
      final SharedPreferences prefs = mContext.getApplicationContext()
          .getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
      final String id = prefs.getString(PREFS_DEVICE_ID, null);

      if (id != null) {
        // Use the ids previously computed and stored in the prefs file
        uuid = id;
      } else {

        final String androidId = Settings.Secure.getString(mContext.getContentResolver(),
            Settings.Secure.ANDROID_ID);
        // Use the Android ID unless it's broken, in which case fallback on
        // deviceId,
        // unless it's not available, then fallback on a random number which
        // we store
        // to a prefs file
        try {
          if (!"9774d56d682e549c".equals(androidId)) {
            uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
          } else {
            final String deviceId = ((TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString()
                : UUID.randomUUID().toString();
          }
        } catch (UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }

        // Write the value out to the prefs file
        prefs.edit().putString(PREFS_DEVICE_ID, uuid).commit();
      }
    }
    return uuid;
  }

  /**
   * 获取路由mac地址
   */
  public static String getRouterMac() {
    String bssid = null;
    WifiInfo wifiInfo = getWifiInfo();
    if (wifiInfo != null) {
      bssid = wifiInfo.getBSSID();
    }
    return bssid;
  }

  /**
   * 获取基站信息
   */
  @SuppressLint("MissingPermission")
  public static JsonElement getCellInfo() throws JSONException {
    Context context = UnitContext.getInstance();
    if (ContextCompat.checkSelfPermission(context,
        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
      return null;
    TelephonyManager mTelephonyManager = (TelephonyManager) context
        .getSystemService(Context.TELEPHONY_SERVICE);
    String operator = mTelephonyManager.getNetworkOperator();
    if (TextUtils.isEmpty(operator) || operator.equals("null"))
      return null;
    int mcc = Integer.parseInt(operator.substring(0, 3));
    int mnc = Integer.parseInt(operator.substring(3));
    int lac = -1;
    int cellId = -1;
    if (mTelephonyManager.getCellLocation() instanceof GsmCellLocation) {
      GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
      lac = location.getLac();
      cellId = location.getCid();
    } else if (mTelephonyManager.getCellLocation() instanceof CdmaCellLocation) {
      CdmaCellLocation location = (CdmaCellLocation) mTelephonyManager.getCellLocation();
      cellId = location.getBaseStationId();
    }
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("cid", cellId);
    jsonObject.addProperty("mcc", mcc);
    jsonObject.addProperty("mnc", mnc);
    if (lac != -1) {
      jsonObject.addProperty("lac", lac);
    }
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(jsonObject);
    JsonObject jsonObject1 = new JsonObject();
    jsonObject1.add("cells", jsonArray);
    return jsonObject1;
  }

  /**
   * 获取屏幕分辨率
   */
  public static String getScreenResolution() {
    Context context = UnitContext.getInstance();
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    // WindowManager windowManager;
    // try {
    // windowManager = ((Activity) context).getWindowManager();
    // } catch (ClassCastException e) {
    // e.printStackTrace();
    // return 0 + "*" + 0;
    // }
    // Display display = windowManager.getDefaultDisplay();
    // Point point = new Point();
    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
    // display.getRealSize(point);
    // else
    // display.getSize(point);
    return dm.heightPixels + "*" + dm.widthPixels;
  }

  /**
   * 获取屏幕类型 1- 横屏 0- 竖屏
   */
  public static String getScreenType() {
    Context context = UnitContext.getInstance();
    Configuration mConfiguration = context.getResources().getConfiguration();
    int ori = mConfiguration.orientation;
    if (ori == Configuration.ORIENTATION_LANDSCAPE) {
      return "1";
    } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
      return "0";
    }
    return "0";
  }

  /**
   * 获取系统版本
   */
  public static String getOsVersion() {
    return "Android " + Build.VERSION.RELEASE;
  }

  /**
   * 获取运营商
   */
  @SuppressLint("HardwareIds")
  public static String getOperators() {
    Context context = UnitContext.getInstance();
    if (ContextCompat.checkSelfPermission(context,
        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
      return UN_PERMISSION;
    String OperatorsName = "未知";
    String IMSI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
        .getSubscriberId();
    if (IMSI == null)
      return "未插卡";
    if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
      OperatorsName = "中国移动";
    } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
      OperatorsName = "中国联通";
    } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
      OperatorsName = "中国电信";
    }
    return OperatorsName;
  }

  /**
   * 获取当前网络状态
   */
  public static String getNetworkType() {
    Context context = UnitContext.getInstance();
    String strNetworkType = "无网络";
    NetworkInfo networkInfo = ((ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
        strNetworkType = "WIFI";
      } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
        String _strSubTypeName = networkInfo.getSubtypeName();
        LogUtil.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);
        // TD-SCDMA networkType is 17
        int networkType = networkInfo.getSubtype();
        switch (networkType) {
          case TelephonyManager.NETWORK_TYPE_GPRS:
          case TelephonyManager.NETWORK_TYPE_EDGE:
          case TelephonyManager.NETWORK_TYPE_CDMA:
          case TelephonyManager.NETWORK_TYPE_1xRTT:
          case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by 11
            strNetworkType = "2G";
            break;
          case TelephonyManager.NETWORK_TYPE_UMTS:
          case TelephonyManager.NETWORK_TYPE_EVDO_0:
          case TelephonyManager.NETWORK_TYPE_EVDO_A:
          case TelephonyManager.NETWORK_TYPE_HSDPA:
          case TelephonyManager.NETWORK_TYPE_HSUPA:
          case TelephonyManager.NETWORK_TYPE_HSPA:
          case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by 14
          case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by 12
          case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by 15
            strNetworkType = "3G";
            break;
          case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by 13
            strNetworkType = "4G";
            break;
          default:
            strNetworkType = _strSubTypeName;
            break;
        }
        LogUtil.e("cocos2d-x", "Network getSubtype : " + Integer.valueOf(networkType).toString());
      }
    }
    LogUtil.e("cocos2d-x", "Network Type : " + strNetworkType);
    return strNetworkType;
  }

  /**
   * 获取sdk版本
   */
  public static String getSdkVersion() {
    return Build.VERSION.SDK_INT + "";
  }

  /**
   * 获取设备信息
   */
  public static String getDeviceInfo() {
    return Build.BRAND + " " + Build.MODEL;
  }

  /**
   * 获取当前GMT时区
   */
  @SuppressLint("SimpleDateFormat")
  public static Date getGMT() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date dateTmp = new Date();
    String dateStrTmp = dateFormat.format(dateTmp);
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return dateFormat1.parse(dateStrTmp);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return new Date();
  }

  /**
   * 获取当前系统语言格式
   */
  public static String getSystemLanguage() {
    Context mContext = UnitContext.getInstance();
    Locale locale = mContext.getResources().getConfiguration().locale;
    String language = locale.getLanguage();
    String country = locale.getCountry();
    String lc = language + "_" + country;
    return lc;
  }

  /**
   * 获取设备IP
   */
  public static String getDeviceIP() {
    try {
      for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI
          .hasMoreElements(); ) {
        NetworkInterface netI = enNetI.nextElement();
        for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr
            .hasMoreElements(); ) {
          InetAddress inetAddress = enumIpAddr.nextElement();
          if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
            return inetAddress.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 判断手机是否有root权限
   */
  public static boolean isRoot() {
    try {
      return !((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists()));
    } catch (Exception e) {
      return false;
    }
  }

  private static String loadFileAsString(String fileName) throws Exception {
    FileReader reader = new FileReader(fileName);
    String text = loadReaderAsString(reader);
    reader.close();
    return text;
  }

  private static String loadReaderAsString(Reader reader) throws Exception {
    StringBuilder builder = new StringBuilder();
    char[] buffer = new char[4096];
    int readLength = reader.read(buffer);
    while (readLength >= 0) {
      builder.append(buffer, 0, readLength);
      readLength = reader.read(buffer);
    }
    return builder.toString();
  }

  /**
   * 获取本机mac地址
   */
  public static String getMacAddr() {
    try {
      List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface nif : all) {
        if (!nif.getName().equalsIgnoreCase("wlan0"))
          continue;

        byte[] macBytes = nif.getHardwareAddress();
        if (macBytes == null) {
          return "";
        }

        StringBuilder res1 = new StringBuilder();
        for (byte b : macBytes) {
          res1.append(String.format("%02X:", b));
        }

        if (res1.length() > 0) {
          res1.deleteCharAt(res1.length() - 1);
        }
        return res1.toString();
      }
    } catch (Exception ignored) {
    }
    return "02:00:00:00:00:00";
  }

  /**
   * 获取屏幕尺寸
   */
  public static String getPhoneSize() {
    Context context = UnitContext.getInstance();
    DecimalFormat df = new DecimalFormat("######0.0");
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    int width = dm.widthPixels;
    int height = dm.heightPixels;
    double x = Math.pow(width, 2);
    double y = Math.pow(height, 2);
    double diagonal = Math.sqrt(x + y);
    int dens = dm.densityDpi;
    double screenInches = diagonal / (double) dens;
    return df.format(screenInches);
  }

  @SuppressLint("PrivateApi")
  public static String getCPUType() {
    String properties = "android.os.SystemProperties";
    String key = "ro.product.cpu.abi";
    try {
      Class<?> clazz = Class.forName(properties);
      Method get = clazz.getMethod("get", String.class, String.class);
      return (String) (get.invoke(clazz, key, ""));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}
