package com.github.gnx.automate.common.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

    /**
     * 转换成 字符串格式
     * @param pathSha256InfoList
     * @return "[[path, sha256]...]"
     */
    public static String parseToString(List<FileInfo> pathSha256InfoList) {
        JSONArray array = new JSONArray(pathSha256InfoList.size());
        for (FileInfo pathSha256Info : pathSha256InfoList) {
            array.add(new String[]{pathSha256Info.getPath(), pathSha256Info.getDigest()});
        }
        return array.toJSONString();
    }

    public static List<FileInfo> parseToList(String fileListStr) {
        JSONArray array = JSON.parseArray(fileListStr);
        List<FileInfo> list = new ArrayList(array.size());
        JSONArray item;
        for (int i = 0; i < array.size(); i++) {
            item = array.getJSONArray(i);
            list.add(new FileInfo(item.getString(0), item.getString(1)));
        }
        return list;
    }


//    public static String parseToFileListByArray(List<FileInfo> pathSha256InfoList) {
//        Collections.sort(pathSha256InfoList, Comparator.comparing(o -> o.getPath()));
//        return JSONArray.toJSONString(pathSha256InfoList);
//    }


    public static ArrayList<FileInfo> list(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        ArrayList<FileInfo> list;
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
        Collections.sort(list, Comparator.comparing(FileInfo::getPath));
        return list;
    }

    private static ArrayList<FileInfo> listForDir(File dir) throws IOException {
        int dirPathLen = dir.getAbsolutePath().length();
        ArrayList<FileInfo> list = new ArrayList(1024);
        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                File file = path.toFile();
                list.add(new FileInfo(path.toString().substring(dirPathLen + 1), DigestUtils.sha256Hex(new FileInputStream(file))));
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
    private static ArrayList<FileInfo> listForZip(File file) throws IOException {
        ArrayList<FileInfo> list = new ArrayList(1024);
        ZipFile zipFile = null;
        ZipInputStream zipInputStream = null;
        try {
            zipFile = new ZipFile(file);
            zipInputStream = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    list.add(new FileInfo(zipEntry.getName(), DigestUtils.sha256Hex(zipFile.getInputStream(zipEntry))));
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
        Collections.sort(list, Comparator.comparing(FileInfo::getPath));
        return list;
    }


}
