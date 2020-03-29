package com.github.gnx.automate.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 环境变量    ThreadLocal
 *
 * @author: genx
 * @date: 2019/2/4 22:58
 */
public class EnvironmentPathUtil {

    private static ThreadLocal<Map<String, String>> TEMP = ThreadLocal.withInitial(() -> new HashMap<>(64));


    public static Map<String, String> get() {
        return TEMP.get();
    }

    public static void put(String key, String value) {
        if (key != null) {
            TEMP.get().put(key, value);
        }
    }

    public static void putAll(Map<String, String> map) {
        if (map != null) {
            TEMP.get().putAll(map);
        }
    }

    public static void remove() {
        TEMP.remove();
    }
}
