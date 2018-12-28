package com.grade.unit.util;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;

import com.grade.unit.mgr.ContextMgr;

import java.io.File;

/**
 * ShareUtil :
 * <p>
 * </> Created by ylwei on 2018/11/21.
 */
public class ShareUtil {
  public static final int Type_Text = 666;
  public static final int Type_Image = 667;

  public static void share(int type, String value) {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    switch (type) {
      case Type_Text:
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "文本分享");
        shareIntent.putExtra(Intent.EXTRA_TEXT, value);
        break;
      case Type_Image:
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "图片分享");
        File file = new File(Environment.getExternalStorageDirectory() + value);
        Uri uri = Uri.fromFile(file);
        shareIntent.setType(getMimeType(file.getAbsolutePath()));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        break;
    }
    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    ContextMgr.getInstance().startActivity(shareIntent);
  }

  // 根据文件后缀名获得对应的MIME类型。
  private static String getMimeType(String filePath) {
    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    String mime = "text/plain";
    if (filePath != null) {
      try {
        mmr.setDataSource(filePath);
        mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return mime;
  }
}
