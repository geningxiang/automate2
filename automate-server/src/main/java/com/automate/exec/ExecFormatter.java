package com.automate.exec;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 命令行的过滤器， 主要是为了安全考虑
 *
 * @author: genx
 * @date: 2019/2/2 0:07
 */
public class ExecFormatter {
    /**
     * 允许的 命令集
     */
    private static final Map<String, Integer> ALLOW_MAP = new HashMap<>(64);

    /**
     * 非法字符
     */
    private static final String[] ILLEGAL_CHARACTERS;

    static {
        //TODO 从数据库读取
        String[] allows = new String[]{
                "mvn", "maven"
                , "cd", "ls", "dir", "ping", "echo", "ps"
        };
        for (String allow : allows) {
            ALLOW_MAP.put(allow, null);
        }

        ILLEGAL_CHARACTERS = new String[]{
//                "&", "\"", "|"
        };
    }

    public static String format(List<String> commands) throws IllegalAccessException {
        List<String> list = new ArrayList<>(commands.size());
        for (String command : commands) {
            if (StringUtils.isNotBlank(command)) {
                String[] ss = command.split("\r|\n|\r\n");
                for (String s : ss) {
                    s = StringUtils.trim(s);
                    if (StringUtils.isEmpty(s) || s.startsWith("#")) {
                        //这一行是注释的
                        continue;
                    }
                    validate(s);
                    list.add(s);
                }
            }
        }
        if (list.size() == 0) {
            throw new IllegalArgumentException("command is blank");
        }
        return StringUtils.join(list, " && ");
    }



    /**
     * 验证单行命令的正确性
     *
     * @param command
     * @return
     */
    private static void validate(String command) {
        if (StringUtils.isBlank(command)) {
            throw new IllegalArgumentException("command is blank");
        }

        String fn;
        int i = command.indexOf(" ");
        if (i > 0) {
            fn = command.substring(0, i);
        } else {
            fn = command;
        }
        //是否允许 TODO 暂时是没有判断白名单
//        if (!ALLOW_MAP.containsKey(fn)) {
//            throw new IllegalAccessException("the command 【" + fn + "】 is not allow");
//        }

        for (String illegalCharacter : ILLEGAL_CHARACTERS) {
            if (command.contains(illegalCharacter)) {
                throw new IllegalArgumentException(illegalCharacter + " is not allow");
            }
        }
    }


}
