package com.grade.unit.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * GsonUtil : GSON工具类
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class GsonUtil {
  private static Gson instance;
  private static String dateFormat = DateUtil.DEFAULT_DATE_FORMAT;

  /**
   * 将obj对象转为json格式数据
   */
  synchronized public static String toJson(Object obj) {
    return getInstance().toJson(obj);
  }

  /**
   * 将json字符串转为 java对象
   */
  synchronized public static <T> T parse(String json, Class<T> classOfT) {
    return getInstance().fromJson(json, classOfT);
  }

  /**
   * 将json字符串转为 java列表对象
   */
  synchronized public static <T> List<T> parse(String json, Type type) {
    return getInstance().fromJson(json, type);
  }

  /**
   * 获取gson实例(排除了FINAL、TRANSIENT、STATIC)
   */
  public static Gson getInstance() {
    if (IsEmpty.object(instance))
      instance = new GsonBuilder().serializeNulls().setDateFormat(dateFormat)
          .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
          .registerTypeAdapter(Date.class, new DateConverter()).create();
    return instance;
  }

  private static class DateConverter implements JsonDeserializer<Date>, JsonSerializer<Date> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      if (IsEmpty.object(json.getAsString()))
        return null;
      try {
        return sdf.parse(json.getAsString());
      } catch (ParseException e) {
        return null;
      }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
      if (IsEmpty.object(src))
        return null;
      return new JsonPrimitive(sdf.format(src));
    }
  }
}
