package com.automate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpRequest;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/31 15:18
 */
public class Test {



    public static void main(String[] args) {
        String s = "D:\\github-workspace\\incubator-skywalking";

        File dir = new File(s);

        Collection<File> list = FileUtils.listFiles(dir, FileFilterUtils.nameFileFilter("pom.xml"), DirectoryFileFilter.INSTANCE);

        Map<String, MavenProject> map = new HashMap(128);
        for (File file : list) {
            try {
                MavenProject mavenProject = read(file);
                if(mavenProject != null){
                    map.put(mavenProject.groupId + "/" + mavenProject.artifactId, mavenProject);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        //System.out.println(map.keySet());

        for (MavenProject mavenProject : map.values()) {
            if(mavenProject.dependencies != null && mavenProject.dependencies.size() > 0) {
                mavenProject.dependenciesList = new ArrayList(mavenProject.dependencies.size());
                for (String dependency : mavenProject.dependencies) {
                    MavenProject temp = map.get(dependency);
                    if(temp != null){
                        temp.reference++;
                        mavenProject.dependenciesList.add(temp);
                    }
                }
            }
        }

        JSONArray array = new JSONArray();
        for (MavenProject value : map.values()) {
            if(value.reference == 0){
                array.add(value.toJson());
            }
        }
        //构造一个 maven引用数， 可以确定一下 相互之间的依赖， 并确定编译次序
        System.out.println(array);
    }

    private static MavenProject read(File file) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        Element projectEl = document.getRootElement();
        String packaging = projectEl.elementText("packaging");

        if(packaging == null || "jar".equals(packaging)){

            String groupId = projectEl.elementText("groupId");
            if(groupId == null){
                Element parentEL = projectEl.element("parent");
                if(parentEL != null){
                    groupId = parentEL.elementText("groupId");
                }
            }
            String artifactId = projectEl.elementText("artifactId");

            MavenProject mavenProject = new MavenProject();
            mavenProject.groupId = groupId;
            mavenProject.artifactId = artifactId;

            Element dependenciesEl = projectEl.element("dependencies");
            if(dependenciesEl != null){
                List<Element> dependencyList = dependenciesEl.elements("dependency");
                if(dependencyList.size() > 0){
                    mavenProject.dependencies = new ArrayList<>(dependencyList.size());
                    for (Element dependencyEl : dependencyList) {
                        mavenProject.dependencies.add(dependencyEl.elementText("groupId") + "/" + dependencyEl.elementText("artifactId"));
                    }
                }
            }
            return mavenProject;
        }
        return null;
    }

    private static class MavenProject{
        private String groupId;
        private String artifactId;
        private List<String> dependencies;

        //引用次数
        private int reference = 0;

        private List<MavenProject> dependenciesList;


        public JSONObject toJson(){
            JSONObject json = new JSONObject();
//            json.put("groupId", groupId);
            json.put("artifactId", artifactId);

            if(dependenciesList != null && dependenciesList.size() > 0){
                JSONArray array = new JSONArray();
                for (MavenProject mavenProject : dependenciesList) {
                    array.add(mavenProject.toJson());
                }
                json.put("dependencies", array);
            }
            return json;
        }
    }
}
