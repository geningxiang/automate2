package com.automate.common.utils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/9/25 11:11
 */
public class SystemUtil {
    public enum OsType {
        unkonwn,
        linux,
        windows
    }

    private static OsType osType;

    private static void init() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith(OsType.windows.name())) {
            osType = OsType.windows;
        } else if (os.startsWith(OsType.linux.name())) {
            osType = OsType.linux;
        } else {
            osType = OsType.unkonwn;
        }
    }

    public static OsType getOsType() {
        if (osType == null) {
            //这里无需考虑并发  重入并没有影响
            init();
        }
        return osType;
    }

    public static boolean isWindows(){
        return SystemUtil.getOsType() == OsType.windows;
    }

}
