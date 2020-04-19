package com.github.gnx.automate.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.github.gnx.automate.field.PathSha256Info;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/6/7 19:28
 */
public class FileListSha256Util {

    public static String parseToFileList(List<PathSha256Info> pathSha256InfoList) {
        JSONArray array = new JSONArray(pathSha256InfoList.size());
        for (PathSha256Info pathSha256Info : pathSha256InfoList) {
            array.add(new String[]{pathSha256Info.getPath(), pathSha256Info.getSha256()});
        }
        return array.toJSONString();
    }

    public static String parseToFileListByArray(List<String[]> pathSha256InfoList) {
        Collections.sort(pathSha256InfoList, Comparator.comparing(o -> o[0]));
        return JSONArray.toJSONString(pathSha256InfoList);
    }


    public static ArrayList<PathSha256Info> list(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        ArrayList<PathSha256Info> list;
        if (file.isDirectory()) {
            list = listForDir(file);
        } else {
            String fileName = file.getName().toLowerCase();
            if (fileName.endsWith("zip") || fileName.endsWith("war") || fileName.endsWith("jar") || fileName.endsWith("apk")) {
                list = listForZip(file);
            } else {
                throw new IOException("暂不支持该文件类型");
            }
        }
        Collections.sort(list, Comparator.comparing(PathSha256Info::getPath));
        return list;
    }

    private static ArrayList<PathSha256Info> listForDir(File dir) throws IOException {
        int dirPathLen = dir.getAbsolutePath().length();
        ArrayList<PathSha256Info> list = new ArrayList(1024);
        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                File file = path.toFile();
                list.add(new PathSha256Info(path.toString().substring(dirPathLen + 1), DigestUtils.sha256Hex(new FileInputStream(file)), file.length()));
                return FileVisitResult.CONTINUE;
            }
        });
        return list;
    }

    /**
     * 读取 zip
     * !!路径中不能存在中文
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static ArrayList<PathSha256Info> listForZip(File file) throws IOException {
        ArrayList<PathSha256Info> list = new ArrayList(1024);
        ZipFile zipFile = null;
        ZipInputStream zipInputStream = null;
        try {
            zipFile = new ZipFile(file);
            zipInputStream = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    list.add(new PathSha256Info(zipEntry.getName(), DigestUtils.sha256Hex(zipFile.getInputStream(zipEntry)), zipEntry.getSize()));
                }
            }
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
            if (zipInputStream != null) {
                zipInputStream.close();
            }
        }
        Collections.sort(list, Comparator.comparing(PathSha256Info::getPath));
        return list;
    }


}
