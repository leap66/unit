package com.grade.unit.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * StringUtil : 字符串格式化工具类
 * <p>
 * </> Created by ylwei on 2018/5/2.
 */
public class StringUtil {

  // 生成随数
  public static int getRandomNum(int max) {
    Random random = new Random();
    return random.nextInt(max);
  }

  // 生成随机汉字
  public static String getRandomStr(int length) {
    StringBuilder builder = new StringBuilder();
    String str = "";
    int topPos, lowPos;
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      topPos = (176 + Math.abs(random.nextInt(39)));
      lowPos = (161 + Math.abs(random.nextInt(93)));
      byte[] b = new byte[2];
      b[0] = (Integer.valueOf(topPos)).byteValue();
      b[1] = (Integer.valueOf(lowPos)).byteValue();
      try {
        str = new String(b, "GBK");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      builder.append(str.charAt(0));
    }
    return builder.toString();
  }
}
