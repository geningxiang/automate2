package com.automate.common.file.compare;

import com.automate.vo.FileComparerResult;
import com.automate.vo.PathSha1Info;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/11 22:38
 */
public class FileListComparer {

    public static Collection<FileComparerResult> compareByMap(ArrayList<String[]>... fileLists) {
        if (fileLists == null || fileLists.length == 0) {
            return new ArrayList(0);
        }
        HashMap<String, FileComparerResult> map = new HashMap(2048);
        int count = fileLists.length;
        ArrayList<String[]> fileList;
        FileComparerResult fileMd5ComparerResult;
        for (int i = 0; i < fileLists.length; i++) {
            fileList = fileLists[i];
            for (String[] ss : fileList) {
                fileMd5ComparerResult = map.get(ss[0]);
                if (fileMd5ComparerResult == null) {
                    fileMd5ComparerResult = new FileComparerResult(count);
                    fileMd5ComparerResult.setPath(ss[0]);
                    map.put(ss[0], fileMd5ComparerResult);
                }
                fileMd5ComparerResult.setCode(i, ss[1]);
            }
        }
        return map.values();

    }

    public static LinkedList<FileComparerResult> compare(List<PathSha1Info>... fileLists) {
        ArrayList<String[]>[] data = new ArrayList[fileLists.length];
        for (int i = 0; i < fileLists.length; i++) {

            List<PathSha1Info> fileList = fileLists[i];
            ArrayList list = new ArrayList(fileList.size());
            for (PathSha1Info pathSha1Info : fileList) {
                list.add(new String[]{pathSha1Info.getPath(), pathSha1Info.getSha1()});
            }
            data[i] = list;
        }
        return compare(data);

    }

    public static LinkedList<FileComparerResult> compare(ArrayList<String[]>... fileLists) {
        LinkedList<FileComparerResult> results = new LinkedList();

        if (fileLists == null || fileLists.length == 0) {
            return results;
        }
        int count = fileLists.length;

        for (List<String[]> fileList : fileLists) {
            //先全部排序一遍
            Collections.sort(fileList, Comparator.comparing(o -> o[0]));
        }

        //再根据当前值 判断移动哪些队列的下标

        int[] indexs = new int[count];

        String[] ss;
        String path;
        List<Integer> minIndexList;
        while (true) {
            path = null;
            minIndexList = new ArrayList(count);
            for (int i = 0; i < count; i++) {
                if (indexs[i] < fileLists[i].size()) {
                    ss = fileLists[i].get(indexs[i]);

                    if (path == null || ss[0].compareTo(path) < 0) {
                        minIndexList.clear();
                        minIndexList.add(i);
                        path = ss[0];
                    } else if (path.equals(ss[0])) {
                        minIndexList.add(i);
                    }
                }
            }
            if (minIndexList.size() == 0) {
                break;
            }

            FileComparerResult fileMd5ComparerResult = new FileComparerResult(count);
            for (Integer i : minIndexList) {
                ss = fileLists[i].get(indexs[i]);
                fileMd5ComparerResult.setPath(ss[0]);
                fileMd5ComparerResult.setCode(i, ss[1]);
                indexs[i]++;
            }
            if (minIndexList.size() == count) {
                //判断是否全部一致
                fileMd5ComparerResult.setUniformity(isUniformity(fileMd5ComparerResult.getCodes()));
            }
            results.add(fileMd5ComparerResult);

        }
        return results;
    }

    private static boolean isUniformity(String[] ss) {

        for (int i = 0; i < ss.length; i++) {
            if (ss[i] == null) {
                return false;
            } else if (i > 0 && !ss[i].equals(ss[i - 1])) {
                return false;
            }
        }
        return true;
    }


}
