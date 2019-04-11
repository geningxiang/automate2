package com.automate.common.utils;
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
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/5 20:35
 */
public class FileListMd5Util {

    private static final String BACKSLASH = "\\";

    private static final String SLASH = "/";

    public static LinkedList<PathMd5Info> list(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        LinkedList<PathMd5Info> list;
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
        //Collections.sort(list, Comparator.comparing(o -> o.path));
        return list;
    }

    private static LinkedList<PathMd5Info> listForDir(File dir) throws IOException {
        int dirPathLen = dir.getAbsolutePath().length();
        LinkedList<PathMd5Info> list = new LinkedList();
        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                File file = path.toFile();
                list.add(new PathMd5Info(path.toString().substring(dirPathLen + 1), DigestUtils.md5Hex(new FileInputStream(file)), file.length()));
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
    private static LinkedList<PathMd5Info> listForZip(File file) throws IOException {
        LinkedList<PathMd5Info> list = new LinkedList();
        ZipFile zipFile = null;
        ZipInputStream zipInputStream = null;
        try {
            zipFile = new ZipFile(file);
            zipInputStream = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {

                    list.add(new PathMd5Info(zipEntry.getName(), DigestUtils.md5Hex(zipFile.getInputStream(zipEntry)), zipEntry.getSize()));
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

    public static class PathMd5Info {
        private String path;
        private String md5;
        private long size;

        public PathMd5Info(String path, String md5, long size) {
            if (BACKSLASH.equals(File.separator)) {
                this.path = path.replace(BACKSLASH, SLASH);
            } else {
                this.path = path;
            }

            this.md5 = md5;
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public String getMd5() {
            return md5;
        }

        public long getSize() {
            return size;
        }
    }
}
