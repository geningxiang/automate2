package com.automate.exec;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 命令行的过滤器， 主要是为了安全考虑
 *
 * @author: genx
 * @date: 2019/2/2 0:07
 */
public class ExecFilter {
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
                "mvn", "maven", "cd", "ls", "dir", "ping"
        };
        for (String allow : allows) {
            ALLOW_MAP.put(allow, null);
        }

        ILLEGAL_CHARACTERS = new String[]{
                "&", "\"", "|"
        };
    }

    /**
     * 对命令行做过滤操作 主要为了安全考虑   只允许部分命令  而且需要为参数加 ""
     * @param commands
     * @return
     * @throws IllegalAccessException
     */
    public static List<String> filter(List<String> commands) throws IllegalAccessException {
        if (commands == null || commands.size() == 0) {
            return null;
        }
        List<String> list = new ArrayList<>(commands.size());
        for (String command : commands) {
            command = StringUtils.trim(command);
            validate(command);
            String fn;
            String param = null;
            int i = command.indexOf(" ");
            if (i > 0) {
                fn = command.substring(0, i);
                param = StringUtils.trim(command.substring(i + 1));
            } else {
                fn = command;
            }

            if (!ALLOW_MAP.containsKey(fn)) {
                throw new IllegalAccessException("the command 【" + fn + "】 is not allow");
            }
            StringBuilder sb = new StringBuilder();
            sb.append(fn);
            if(StringUtils.isNotEmpty(param)) {
                //TODO 安全性
//                sb.append(" \"").append(param).append("\"");
                sb.append(" ").append(param);
            }
            list.add(sb.toString());
        }
        return list;
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
        for (String illegalCharacter : ILLEGAL_CHARACTERS) {
            if (command.contains(illegalCharacter)) {
                throw new IllegalArgumentException(illegalCharacter + " is not allow");
            }
        }
    }


}
