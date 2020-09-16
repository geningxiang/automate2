package com.github.gnx.automate.exec.ssh;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.common.file.FileInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/14 22:37
 */
public class SSHUtil {
    private static Logger logger = LoggerFactory.getLogger(SSHUtil.class);

    /**
     * 返回 linux 指定目录下所有文件的 sha256列表
     * @param s
     * @param dir
     * @return 返回结果已排序
     * @throws Exception
     */
    public static List<FileInfo> sha256sum(SSHConnection s, final String dir) throws Exception {
        List<FileInfo> list = new ArrayList(1024);
        String targetDir = dir;
        if (!targetDir.endsWith("/")) {
            targetDir += "/";
        }
        String cmd = "cd " + targetDir + " && find ./ -type f -print0 | xargs -0 sha256sum";
        logger.debug("[sha256sum]{}", cmd);
        int exitValue = s.exec(cmd, new IMsgListener() {
            @Override
            public IMsgListener append(CharSequence line) {
                if (StringUtils.isNotBlank(line)) {
                    String[] ss = line.toString().split("  ");
                    if (ss.length == 2 && ss[1].length() > 2) {
                        //空目录是  -
                        list.add(new FileInfo(ss[1].substring(2), ss[0]));
                    }
                }
                return this;
            }
        });
        if (exitValue == 0) {
            //path 正序
            Collections.sort(list, Comparator.comparing(o -> o.getPath()));
            return list;
        } else {
            throw new RuntimeException("获取文件列表sha256失败, exit code: " + exitValue);
        }
    }
}
