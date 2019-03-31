package com.automate.build.maven;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/31 16:51
 */
public class MavenUtilTest {

    public static void main(String[] args) {

        TreeMap<String, Map<String, Map<String, Long>>> tree = MavenUtil.repositoryTree();

        System.out.println(JSON.toJSONString(tree));
    }
}