package com.github.gnx.automate.cache.impl;

import com.github.gnx.automate.cache.ICacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 17:07
 */
@Component
public class CacheRedisHelper implements ICacheHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void hashPutAll(String key, Map<String, String> data){
         opsForHash().putAll(key, data);
    }

    @Override
    public Map<String, String> hashEntries(String key){
        return opsForHash().entries(key);
    }


    private HashOperations<String, String, String> opsForHash(){
        return redisTemplate.opsForHash();
    }
}
