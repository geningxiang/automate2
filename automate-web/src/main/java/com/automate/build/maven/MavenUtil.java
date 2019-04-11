package com.automate.build.maven;

import com.automate.common.SystemConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/31 16:48
 */
public class MavenUtil {

    public static TreeMap<String, Map<String, Map<String, Long>>> repositoryTree() throws IOException {
        TreeMap<String, Map<String, Map<String, Long>>> map = new TreeMap();
        String mavenRepository = SystemConfig.getMavenRepositoryDir();
        File dir = new File(mavenRepository);
        int startIndex = dir.getAbsolutePath().length() + 1;

        //jar、pom

        /*
        Collection<File> list = FileUtils.listFiles(dir, new String[]{"jar"}, true);
        使用 Files.walkFileTree 拍遍历 速度更快
         */

        Path dirPath = dir.toPath();
        List<File> list = new LinkedList();
        Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
                if (file.toString().endsWith(".jar")) {
                    list.add(file.toFile());
                }
                return FileVisitResult.CONTINUE;
            }
        });

        for (File file : list) {
            appendMavenProjectToMap(startIndex, file, map);
        }

        return map;
    }


    private static void appendMavenProjectToMap(int startIndex, File file, TreeMap<String, Map<String, Map<String, Long>>> map) {
        String s = file.getAbsolutePath().substring(startIndex);
        String[] ss;
        if ("\\".equals(File.separator)) {
            ss = s.split("\\\\");
        } else {
            ss = s.split(File.separator);
        }
        if (ss.length > 3) {
            String groupId = StringUtils.join(Arrays.copyOfRange(ss, 0, ss.length - 3), ".");

            if (groupId.startsWith("org.apache.maven")) {
                // 忽略 org.apache.maven 下的所有
                return;
            }

            String artifactId = ss[ss.length - 3];
            String version = ss[ss.length - 2];

            Map<String, Map<String, Long>> artifactMap = map.get(groupId);
            Map<String, Long> versionMap = null;

            if (artifactMap == null) {
                artifactMap = new HashMap(8);
                map.put(groupId, artifactMap);
            } else {
                versionMap = artifactMap.get(artifactId);
            }

            if (versionMap == null) {
                versionMap = new HashMap(16);
                artifactMap.put(artifactId, versionMap);
            }

            versionMap.put(version, file.lastModified());

        }

    }


}
