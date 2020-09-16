package com.automate.build.maven;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  单个 maven 项目
 * @author: genx
 * @date: 2019/1/31 16:49
 */
public class MavenProject {
    public enum PackagingType{
        pom,
        jar,
        war;

        public static PackagingType match(String str){
            if(pom.name().equals(str)){
                return pom;
            } else if (war.name().equals(str)){
                return war;
            } else {
                //默认是jar
                return jar;
            }
        }
    }

    private String groupId;
    private String artifactId;

    private PackagingType packagingType;

    private List<MavenProject> dependencies;

    /**
     * 在一个项目中的引用次数
     */
    private int reference;
    /**
     * 引用到的本地项目
     */
    private List<String> dependenciesList;

    public MavenProject(String groupId, String artifactId, PackagingType packagingType) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public static String buildKey(String groupId, String artifactId){
        return groupId + "/" + artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public List<MavenProject> getDependencies() {
        return dependencies;
    }

    public List<String> getDependenciesList() {
        return dependenciesList;
    }

    public void setDependenciesList(List<String> dependenciesList) {
        this.dependenciesList = dependenciesList;
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("groupId", groupId);
        json.put("artifactId", artifactId);
        if(dependencies != null && dependencies.size() > 0){
            JSONArray array = new JSONArray(dependencies.size());
            for (MavenProject mavenProject : dependencies) {
                array.add(mavenProject.toJson());
            }
            json.put("dependencies", array);
        }
        return json;
    }
}
