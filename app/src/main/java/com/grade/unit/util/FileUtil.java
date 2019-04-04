package com.grade.unit.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.grade.unit.mgr.UnitContext;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * FileUtil : 文件类工具类
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public class FileUtil {

  // 加载文件转化为字节
  public static byte[] loadFile(File file) throws IOException {
    int tempSize = 1000;
    byte[] buffer;
    FileInputStream fis = new FileInputStream(file);
    ByteArrayOutputStream bos = new ByteArrayOutputStream(tempSize);
    byte[] b = new byte[tempSize];
    int n;
    while ((n = fis.read(b)) != -1) {
      bos.write(b, 0, n);
    }
    fis.close();
    bos.close();
    buffer = bos.toByteArray();
    return buffer;
  }

  // 转化字节为文件
  public static void converFile(byte[] code, String fileName) {
    if (!fileName.contains(".mp3"))
      fileName = fileName + ".mp3";
    BufferedOutputStream bos = null;
    FileOutputStream fos = null;
    try {
      String filePath = UnitContext.getInstance().getCacheDir().getAbsolutePath();
      File file = new File(filePath, fileName);
      fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      bos.write(code);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (bos != null) {
        try {
          bos.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  // 获取文件 MD5验证签名
  public static String getFileMD5String(String path) throws NoSuchAlgorithmException, IOException {
    FileInputStream is = null;
    try {
      is = new FileInputStream(path);
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] buffer = new byte[2048];
      int bytes = 0;
      do {
        bytes = is.read(buffer, 0, 2048);
        if (bytes > 0)
          md.update(buffer, 0, bytes);

      } while (bytes > 0);
      byte[] md5Sum = md.digest();
      return ByteArraytoHexString(md5Sum);
    } finally {
      if (!IsEmpty.object(is))
        is.close();
    }
  }

  // 获取文件地址
  public static String getFilePath(String fileName) {
    Context context = UnitContext.getInstance();
    if (android.os.Environment.getExternalStorageState()
        .equals(android.os.Environment.MEDIA_MOUNTED)) {
      return context.getExternalCacheDir() + "/" + fileName;
    } else {
      return context.getCacheDir() + "/" + fileName;
    }
  }

  // 获取文件大小
  public static float getFileSize2KB(File file) {
    if (file != null) {
      return file.length() / 1024 + 0.0005f;
    }
    return -1;
  }

  // 安装apk
  public static void installApk(String apkPath) {
    Context context = UnitContext.getInstance();
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      Uri contentUri = FileProvider.getUriForFile(context,
          context.getPackageName() + ".fileProvider", new File(apkPath));
      intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
    } else {
      intent.setDataAndType(Uri.fromFile(new File(apkPath)),
          "application/vnd.android.package-archive");
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    context.startActivity(intent);
    android.os.Process.killProcess(android.os.Process.myPid());
  }

  private static String ByteArraytoHexString(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(bytes[i] & 0xFF);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
