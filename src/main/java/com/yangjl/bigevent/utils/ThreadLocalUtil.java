package com.yangjl.bigevent.utils;

import java.util.Map;

/**
 * 用于保存用户请求时的令牌解析结果
 */
public class ThreadLocalUtil {
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

     public static void set(Object value) {
        THREAD_LOCAL.set(value);
     }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static Integer getId() {
        Map<String,Object> map = (Map<String, Object>) THREAD_LOCAL.get();
        return (Integer) map.get("id");
    }

    public static String getUserName() {
        Map<String,Object> map = (Map<String, Object>) THREAD_LOCAL.get();
        return (String) map.get("username");
    }
}
