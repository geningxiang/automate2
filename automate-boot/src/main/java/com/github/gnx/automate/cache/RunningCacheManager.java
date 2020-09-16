package com.github.gnx.automate.cache;

import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.AssemblyLineTaskLogEntity;
import com.github.gnx.automate.vo.IRunningLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/2 20:50
 */
public class RunningCacheManager {

    private static Map<String, IRunningLog> cache = new ConcurrentHashMap(256);


    /**
     * 添加到缓存  !!记得一定要手动释放
     * @param runningLog
     */
    public static void add(IRunningLog runningLog) {
        if (runningLog != null) {
            cache.put(key(runningLog.getClass(), runningLog.getId()), runningLog);
        }
    }

    public static void release(IRunningLog runningLog) {
        cache.remove(key(runningLog.getClass(), runningLog.getId()));
    }

    public static AssemblyLineLogEntity getAssemblyLineLogEntity(int id) {
        IRunningLog data = cache.get(key(AssemblyLineLogEntity.class, id));
        if (data != null && data instanceof AssemblyLineLogEntity) {
            return (AssemblyLineLogEntity) data;
        } else {
            return null;
        }
    }

    public static AssemblyLineTaskLogEntity getAssemblyLineTaskLogEntity(int id) {
        IRunningLog data = cache.get(key(AssemblyLineTaskLogEntity.class, id));
        if (data != null && data instanceof AssemblyLineTaskLogEntity) {
            return (AssemblyLineTaskLogEntity) data;
        } else {
            return null;
        }
    }

    public static String getUnread(String key, int readed) {
        IRunningLog data = cache.get(key);
        if (data == null) {
            //消息缓存对象已经执行完毕， 不存在与缓存中
            return null;
        }
        if (data.content() != null && data.content().length() > readed) {
            //有新的内容
            return data.content().substring(readed);
        } else {
            //没有新的内容
            return "";
        }

    }

    private static String key(Class cls, int id) {
        return cls.getSimpleName() + "_" + id;
    }


}
