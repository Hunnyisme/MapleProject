package com.hunny.reijiproject.common;

/**
 * ThreadLocal的一个封装类,本项目用于元对象字段填充控制器获取当前管理员id
 * @author QiuZhengJie
 * @date 2022/5/10
 */
public class BaseContext {
    private static ThreadLocal<Long>  threadLocal=new ThreadLocal<>();

    public static void setCurentId(Long id)
    {
        threadLocal.set(id);
    }
    public  static long getCurrentId()
    {

        return  threadLocal.get();
    }

}
