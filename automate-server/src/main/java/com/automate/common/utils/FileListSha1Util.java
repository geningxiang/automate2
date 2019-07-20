package com.automate.common.utils;

import com.automate.vo.PathSha1Info;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/6/7 19:28
 */
public class FileListSha1Util {




    public static LinkedList<PathSha1Info> list(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        LinkedList<PathSha1Info> list;
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
        Collections.sort(list, Comparator.comparing(PathSha1Info::getPath));
        return list;
    }

    private static LinkedList<PathSha1Info> listForDir(File dir) throws IOException {
        int dirPathLen = dir.getAbsolutePath().length();
        LinkedList<PathSha1Info> list = new LinkedList();
        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                File file = path.toFile();
                list.add(new PathSha1Info(path.toString().substring(dirPathLen + 1), DigestUtils.sha1Hex(new FileInputStream(file)), file.length()));
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
    private static LinkedList<PathSha1Info> listForZip(File file) throws IOException {
        LinkedList<PathSha1Info> list = new LinkedList();
        ZipFile zipFile = null;
        ZipInputStream zipInputStream = null;
        try {
            zipFile = new ZipFile(file);
            zipInputStream = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    list.add(new PathSha1Info(zipEntry.getName(), DigestUtils.sha1Hex(zipFile.getInputStream(zipEntry)), zipEntry.getSize()));
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
        return list;
    }


}
