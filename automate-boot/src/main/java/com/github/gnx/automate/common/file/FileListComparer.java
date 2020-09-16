package com.github.gnx.automate.common.file;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/1 23:02
 */
public class FileListComparer {


    /**
     * 文件源比对
     * @param onlyDiff
     * @param fileLists
     * @return 这里返回的FileCompareResult 不包含 [来源信息]
     */
    public static FileCompareResult compare(boolean onlyDiff, List<FileInfo>... fileLists) {
        if (fileLists == null || fileLists.length == 0) {
            throw new IllegalArgumentException("文件源是必须的");
        }

        //再根据当前值 判断移动哪些队列的下标
        TreeMap<String, String[]> treeMap = new TreeMap();

        String[] temp;
        int size = fileLists.length;
        for (int i = 0; i < size; i++) {
            if(fileLists[i] != null) {
                for (FileInfo fileInfo : fileLists[i]) {
                    temp = treeMap.get(fileInfo.getPath());
                    if (temp == null) {
                        temp = new String[size + 1];
                        temp[0] = fileInfo.getPath();
                        treeMap.put(fileInfo.getPath(), temp);
                    }
                    temp[i + 1] = fileInfo.getDigest();
                }
            }
        }

        int fitCount = 0;
        int diffCount = 0;

        List<String[]> data = new ArrayList(treeMap.size());
        for (String[] value : treeMap.values()) {
            if (isFit(value)) {
                fitCount++;
                if (onlyDiff) {
                    continue;
                }
            } else {
                diffCount++;

            }
            data.add(value);
        }

        FileCompareResult result = new FileCompareResult();
        result.setSourceCount(fileLists.length);
        result.setFitCount(fitCount);
        result.setDiffCount(diffCount);
        result.setData(data);
        return result;
    }


    private static boolean isFit(String[] ss) {

        for (int i = 1; i < ss.length; i++) {
            if (ss[i] == null) {
                return false;
            } else if (i > 1 && !ss[i].equals(ss[i - 1])) {
                return false;
            }
        }
        return true;
    }

}
