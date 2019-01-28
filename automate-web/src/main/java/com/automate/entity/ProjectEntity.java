package com.automate.entity;

import com.automate.common.SystemConfig;
import com.automate.vcs.ICVSRepository;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:21
 */
@Entity
@Table(name = "CA2_PROJECT")
public class ProjectEntity implements ICVSRepository {
    public enum Type{
        /**
         * java项目
         */
        JAVA,
        /**
         * web项目， 前端项目
         */
        WEB,
        /**
         * 安卓项目
         */
        ANDROID
    }

    /**
     * 版本控制类型
     */
    public enum VersionType{
        GIT
    }

    /**
     * 编译方式
     */
    public enum CompileType{
        MAVEN,
        GRADLE
    }

    /**
     * 状态
     */
    public enum Status{
        /**
         * 未激活
         */
        NOT_ACTIVE,
        /**
         * 已激活
         */
        ACTIVATE
    }

    private Integer id;
    /**
     * 项目类型
     * @see Type
     */
    private Byte type;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * 备注
     */
    private String remark;
    /**
     * 版本控制类型
     * @see VersionType
     */
    private Byte versionType;
    /**
     * 版本控制地址
     */
    private String versionUrl;
    /**
     * 版本控制用户名
     */
    private String versionUser;
    /**
     * 版本控制密码
     * TODO 应该做加密处理
     */
    private String versionPwd;
    /**
     * 编译类型
     * @see CompileType
     */
    private Byte compileType;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 相关依赖
     */
    private String relyConfig;
    /**
     * 环境配置
     */
    private String envConfig;
    /**
     * 状态
     * @see Status
     */
    private Byte status;


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TYPE", nullable = true)
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public void setType(Type type) {
        this.type = (byte)type.ordinal();
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ICON", nullable = true, length = 256)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "VERSION_TYPE", nullable = true)
    public Byte getVersionType() {
        return versionType;
    }

    public void setVersionType(Byte versionType) {
        this.versionType = versionType;
    }

    public void setVersionType(VersionType versionType) {
        this.versionType = (byte)versionType.ordinal();
    }

    @Basic
    @Column(name = "VERSION_URL", nullable = true, length = 256)
    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    @Basic
    @Column(name = "VERSION_USER", nullable = true, length = 64)
    public String getVersionUser() {
        return versionUser;
    }

    public void setVersionUser(String versionUser) {
        this.versionUser = versionUser;
    }

    @Basic
    @Column(name = "VERSION_PWD", nullable = true, length = 64)
    public String getVersionPwd() {
        return versionPwd;
    }

    public void setVersionPwd(String versionPwd) {
        this.versionPwd = versionPwd;
    }

    @Basic
    @Column(name = "COMPILE_TYPE", nullable = true)
    public Byte getCompileType() {
        return compileType;
    }

    public void setCompileType(Byte compileType) {
        this.compileType = compileType;
    }

    public void setCompileType(CompileType compileType) {
        this.compileType = (byte)compileType.ordinal();
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "RELY_CONFIG", nullable = true, length = 1024)
    public String getRelyConfig() {
        return relyConfig;
    }

    public void setRelyConfig(String relyConfig) {
        this.relyConfig = relyConfig;
    }

    @Basic
    @Column(name = "ENV_CONFIG", nullable = true, length = 1024)
    public String getEnvConfig() {
        return envConfig;
    }

    public void setEnvConfig(String envConfig) {
        this.envConfig = envConfig;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = (byte)status.ordinal();
    }

    @Transient
    @Override
    public String getLocalDir() {
        return SystemConfig.getProjectSourceCodeDir(this);
    }

    @Transient
    @Override
    public String getRemoteUrl() {
        return this.versionUrl;
    }

    @Transient
    @Override
    public String getUserName() {
        return this.versionUser;
    }

    @Transient
    @Override
    public String getPassWord() {
        return this.versionPwd;
    }

}
