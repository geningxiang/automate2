package com.github.gnx.automate.field.req;

/**
 * Created with IntelliJ IDEA.
 * Description: 修改用户对象
 * @author genx
 * @date 2020/3/17 22:33
 */
public class ReqUserField {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码 MD5
     */
    private String passWord;
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮件地址
     */
    private String email;

    /**
     * 允许添加服务器资源
     */
    private Boolean allowCreateServer;

    /**
     * 允许添加项目
     */
    private Boolean allowCreateProject;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAllowCreateServer() {
        return allowCreateServer;
    }

    public void setAllowCreateServer(Boolean allowCreateServer) {
        this.allowCreateServer = allowCreateServer;
    }

    public Boolean getAllowCreateProject() {
        return allowCreateProject;
    }

    public void setAllowCreateProject(Boolean allowCreateProject) {
        this.allowCreateProject = allowCreateProject;
    }
}
