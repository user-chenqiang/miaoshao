package com.miaoshaproject.service;

/**
 * @ClassName CacheService
 * @Description TODO
 * @Author xbt
 * @Date 2021/4/12
 * @Version 1.0
 **/
//封装本地缓存操作类
public interface CacheService {
    //存方法
    void setCommonCache(String key,Object value);

    //取方法
    Object getFromCommonCache(String key);
}
