package com.grade.unit.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.grade.unit.mgr.ContextMgr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ImageUtil : 图片工具类
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class ImageUtil {

  /**
   * 由View 转成Bitmap
   */
  public static Bitmap view2Bitmap(View view) {
    if (view == null) {
      return null;
    }
    view.setDrawingCacheEnabled(true);
    Bitmap screenshot = view.getDrawingCache();
    // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
    screenshot = screenshot.createBitmap(screenshot);
    view.setDrawingCacheEnabled(false);
    return screenshot;
  }

  /**
   * 保存图片到本地
   */
  public static File saveBitmap(Bitmap bm, String picName) throws FileNotFoundException {
    File file;
    if (isHaveSDCard()) {
      file = Environment.getExternalStorageDirectory();
    } else {
      file = Environment.getDataDirectory();
    }
    file = new File(file.getPath() + "/pisces/data/");
    if (!file.isDirectory()) {
      file.delete();
      file.mkdirs();
    }
    if (!file.exists()) {
      file.mkdirs();
    }
    writeBitmap(file.getPath(), picName, bm);
    return file;
  }

  /**
   * 保存图片 到本地
   */
  private static void writeBitmap(String path, String name, Bitmap bitmap)
      throws FileNotFoundException {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    File _file = new File(path + "/" + name);
    if (_file.exists()) {
      _file.delete();
    }
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(_file);
      if (name != null && !"".equals(name)) {
        int index = name.lastIndexOf(".");
        if (index != -1 && (index + 1) < name.length()) {
          String extension = name.substring(index + 1).toLowerCase();
          if ("png".equals(extension)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
          } else if ("jpg".equals(extension) || "jpeg".equals(extension)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
          }
        }
      }
      ContextMgr.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
          Uri.fromFile(new File(path + "/" + name))));
    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    // // 把文件插入到系统图库
    // try {
    // MediaStore.Images.Media.insertImage(this.getContentResolver(),
    // file.getAbsolutePath(),
    // name, null);
    // } catch (FileNotFoundException e) {
    // e.printStackTrace();
    // }
    // // 通知图库更新
    // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
    // Uri.parse("file://" + "/sdcard/namecard/")));
  }

  /**
   * 是否有sd卡
   */
  private static boolean isHaveSDCard() {
    String sdState = android.os.Environment.getExternalStorageState();
    return Environment.MEDIA_MOUNTED.equals(sdState);
  }
}
