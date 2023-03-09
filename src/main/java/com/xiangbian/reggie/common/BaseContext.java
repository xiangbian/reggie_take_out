package com.xiangbian.reggie.common;

/**
 * 基于TheadLocal封装工具类，用于保存和获取当前登录用户id
 * 线程隔离，每个线程各自保存数据
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
