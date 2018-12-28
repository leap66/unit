package com.grade.unit.util;

import java.util.Collection;

/**
 * IsEmpty : 判空格式化工具
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class IsEmpty {

  public static boolean list(Collection object) {
    return null == object || object.size() == 0;
  }

  public static boolean string(String object) {
    return null == object || "".equals(object);
  }

  public static boolean object(Object object) {
    return null == object;
  }
}
