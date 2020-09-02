package com.github.gnx.automate.cache;

import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.AssemblyLineTaskLogEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/2 20:50
 */
public class RunningCacheManager {

    private static Map<String, Object> cache = new ConcurrentHashMap(256);


    /**
     * 添加到缓存  !!记得一定要手动释放
     * @param id
     * @param data
     */
    public static void add(int id, Object data) {
        if (data != null) {
            cache.put(key(data.getClass(), id), data);
        }
    }

    public static void release(int id, Class cls) {
        cache.remove(key(cls, id));
    }

    public static AssemblyLineLogEntity getAssemblyLineLogEntity(int id){
        Object data = cache.get(key(AssemblyLineLogEntity.class,id));
        if(data != null && data instanceof AssemblyLineLogEntity){
            return (AssemblyLineLogEntity) data;
        } else {
            return null;
        }
    }

    public static AssemblyLineTaskLogEntity getAssemblyLineTaskLogEntity(int id){
        Object data = cache.get(key(AssemblyLineTaskLogEntity.class, id));
        if(data != null && data instanceof AssemblyLineTaskLogEntity){
            return (AssemblyLineTaskLogEntity)data;
        } else {
            return null;
        }
    }

    private static String key(Class cls, int id){
        return cls.getSimpleName() + "_" + id;
    }


}
