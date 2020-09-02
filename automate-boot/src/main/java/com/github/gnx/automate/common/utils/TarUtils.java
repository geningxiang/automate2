package com.github.gnx.automate.common.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2020/4/23 21:01
 */
public class TarUtils {
    private static final int BUFFER_SIZE = 1024 * 100;


    /**
     * 打包tar
     * @param dir
     * @param targetDir
     * @param fileName 不带后缀
     * @param skipHiddenDir
     * @throws IOException
     */
    public static File tar(File dir, File targetDir, String fileName, boolean skipHiddenDir) throws IOException {
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        File file = new File(targetDir.getAbsolutePath() + File.separator + fileName + ".tar");

        //必须先删一次
        file.deleteOnExit();


        try (TarArchiveOutputStream taos = new TarArchiveOutputStream(new FileOutputStream(file))) {
            //允许长名  不设置的话 路径长度不能超过100
            taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);

            String dirPath = dir.getAbsolutePath() + File.separator;

            final List<File> fileList = new ArrayList(1024);

            Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (skipHiddenDir && path.toAbsolutePath().toString().contains(File.separator + ".")) {
                        //带点的 认为是隐藏文件夹
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    fileList.add(path.toFile());
                    return FileVisitResult.CONTINUE;
                }
            });

            for (File fileItem : fileList) {
                if (fileItem.getAbsolutePath().equals(file.getAbsolutePath())) {
                    continue;
                }
                TarArchiveEntry tarEn = new TarArchiveEntry(fileItem, fileItem.getAbsolutePath().replace(dirPath, ""));
                taos.putArchiveEntry(tarEn);
                IOUtils.copy(new FileInputStream(fileItem), taos);
                taos.closeArchiveEntry();
            }
        }
        return file;
    }

    public static void unTar(File tarFile, File targetDir) throws IOException {
        try (TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(tarFile))) {
            TarArchiveEntry tae;
            while ((tae = tais.getNextTarEntry()) != null) {

                String path = targetDir.getAbsolutePath() + File.separator + tae.getName();
                File file = new File(path);
                if(tae.isDirectory()){
                    if(!file.exists()) {
                        file.mkdirs();
                    }
                } else {
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    try (OutputStream out = new FileOutputStream(file)) {
                        IOUtils.copy(tais, out);
                    }
                }

            }
        }

    }

    public static File gz(File sourceFile, File targetDir, String fileName) throws IOException {
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        File file = new File(targetDir.getAbsolutePath() + File.separator + fileName + ".tar.gz");
        file.deleteOnExit();
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile), BUFFER_SIZE);
                GZIPOutputStream gzp = new GZIPOutputStream(new FileOutputStream(file))) {
            int count;
            byte[] data = new byte[BUFFER_SIZE];
            while ((count = bis.read(data, 0, BUFFER_SIZE)) != -1) {
                gzp.write(data, 0, count);
            }
        }
        return file;
    }

    public static File tarAndGz(File dir, File targetDir, String fileName, boolean skipHiddenDir) throws IOException {
        File tarFile = tar(dir, targetDir, fileName, skipHiddenDir);
        File tarGzFile = gz(tarFile, targetDir, fileName);
        tarFile.deleteOnExit();
        return tarGzFile;

    }


}