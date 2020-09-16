package com.github.gnx.automate.cache;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 17:07
 */
public interface ICacheHelper {

    void hashPutAll(String key, Map<String, String> data);

    Map<String, String> hashEntries(String key);
}
