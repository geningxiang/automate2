package com.github.gnx.automate.field.req;

import com.github.gnx.automate.contants.CompileType;
import com.github.gnx.automate.contants.ProjectType;
import com.github.gnx.automate.contants.VcsType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 19:46
 */
public class ReqProjectCreateField {

    @NotNull(message = "请选择项目类型")
    private ProjectType type;

    @NotBlank(message = "请输入项目名称")
    private String name;

    private String description;

    @NotNull(message = "请选择vcs类型")
    private VcsType vcsType;

    @NotBlank(message = "请输入版本控制地址")
    private String vcsUrl;

    private String vcsUserName;

    private String vcsPassWord;

    @NotNull(message = "请选择编译类型")
    private CompileType compileType;

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VcsType getVcsType() {
        return vcsType;
    }

    public void setVcsType(VcsType vcsType) {
        this.vcsType = vcsType;
    }

    public String getVcsUrl() {
        return vcsUrl;
    }

    public void setVcsUrl(String vcsUrl) {
        this.vcsUrl = vcsUrl;
    }

    public String getVcsUserName() {
        return vcsUserName;
    }

    public void setVcsUserName(String vcsUserName) {
        this.vcsUserName = vcsUserName;
    }

    public String getVcsPassWord() {
        return vcsPassWord;
    }

    public void setVcsPassWord(String vcsPassWord) {
        this.vcsPassWord = vcsPassWord;
    }

    public CompileType getCompileType() {
        return compileType;
    }

    public void setCompileType(CompileType compileType) {
        this.compileType = compileType;
    }
}
