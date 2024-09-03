package com.yangjl.bigevent.utils;

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
}
