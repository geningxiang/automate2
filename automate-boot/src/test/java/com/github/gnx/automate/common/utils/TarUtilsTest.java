package com.github.gnx.automate.common.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
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
 * @author genx
 * @date 2020/4/23 21:02
 */
class TarUtilsTest {


    private static final int BUFFER_SIZE = 1024 * 100;


    public static void main(String[] args) throws IOException {

        File dir = new File("E:/automate-data/sourcecode/1");

        FileOutputStream fos = new FileOutputStream("E:/automate-data/sourcecode/a.tar");
        TarArchiveOutputStream taos = new TarArchiveOutputStream(fos);

        taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);

        List<Path> list = new ArrayList();


        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

                if (path.toAbsolutePath().toString().contains(File.separator + ".")) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                list.add(path);
                return FileVisitResult.CONTINUE;
            }
        });


        String dirPath = dir.getParentFile().getAbsolutePath();


        for (Path path : list) {
//            System.out.println(path);
            if (path.toAbsolutePath().toString().startsWith(dirPath)) {
                String p = path.toAbsolutePath().toString().substring(dirPath.length() + 1);

                System.out.println(p);

                TarArchiveEntry tarEn = new TarArchiveEntry(path.toFile(), p);
                taos.putArchiveEntry(tarEn);
                IOUtils.copy(new FileInputStream(path.toFile()), taos);
                taos.closeArchiveEntry();
            }

        }

        taos.close();


        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("E:/automate-data/sourcecode/a.tar"), BUFFER_SIZE)) {
            try (FileOutputStream fileOutputStream = new FileOutputStream("E:/automate-data/sourcecode/a.tar.gz")) {
                try (GZIPOutputStream gzp = new GZIPOutputStream(fileOutputStream)) {
                    int count;
                    byte[] data = new byte[BUFFER_SIZE];
                    while ((count = bis.read(data, 0, BUFFER_SIZE)) != -1) {
                        gzp.write(data, 0, count);
                    }
                }
            }
        }


    }

}