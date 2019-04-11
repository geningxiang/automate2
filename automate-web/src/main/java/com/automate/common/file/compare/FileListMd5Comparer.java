package com.automate.common.file.compare;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/11 22:38
 */
public class FileListMd5Comparer {

    public static LinkedList<FileMd5ComparerResult> compare(ArrayList<String[]>... fileLists) {
        LinkedList<FileMd5ComparerResult> results = new LinkedList();

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

            FileMd5ComparerResult fileMd5ComparerResult = new FileMd5ComparerResult(count);
            for (Integer i : minIndexList) {
                ss = fileLists[i].get(indexs[i]);
                fileMd5ComparerResult.setPath(ss[0]);
                fileMd5ComparerResult.setMd5(i, ss[1]);
                indexs[i]++;
            }
            if (minIndexList.size() == count) {
                //判断是否全部一致
                fileMd5ComparerResult.setUniformity(isUniformity(fileMd5ComparerResult.getMd5s()));
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


    static class FileMd5ComparerResult {
        /**
         * 路径
         */
        private String path;

        /**
         * md5数组
         */
        private String[] md5s;

        /**
         * 是否全部一致
         */
        private boolean uniformity = false;

        public FileMd5ComparerResult(int count) {
            this.md5s = new String[count];
        }

        public String getPath() {
            return path;
        }

        public String[] getMd5s() {
            return md5s;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setMd5(int index, String md5) {
            this.md5s[index] = md5;
        }

        public boolean isUniformity() {
            return uniformity;
        }

        public void setUniformity(boolean uniformity) {
            this.uniformity = uniformity;
        }
    }

}
