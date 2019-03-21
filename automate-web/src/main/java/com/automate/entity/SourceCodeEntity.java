package com.automate.entity;

import com.alibaba.fastjson.JSONObject;
import com.automate.common.SystemConfig;
import com.automate.vcs.IVCSRepository;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/31 19:45
 */
@Entity
@Table(name = "CA2_SOURCE_CODE")
public class SourceCodeEntity implements IVCSRepository {
    public enum Type {
        /**
         * java项目
         */
        JAVA,
    }

    /**
     * 版本控制类型
     */
    public enum VcsType {
        GIT,
        SVN
    }

    /**
     * 编译方式
     */
    public enum CompileType {
        MAVEN,
        GRADLE
    }

    /**
     * 状态
     */
    public enum Status {
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
     * 类型
     *
     * @see Type
     */
    private int type;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本控制类型
     *
     * @see VcsType
     */
    private int vcsType;

    /**
     * 版本控制地址
     */
    private String vcsUrl;

    /**
     * 版本控制用户名
     */
    private String userName;

    /**
     * 版本控制密码
     * TODO 应该做加密处理
     */
    private String passWord;

    /**
     * 编译类型
     *
     * @see CompileType
     */
    private int compileType;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 归属人
     *
     * @see AdminUserEntity
     */
    private Integer adminId;

    /**
     * 状态
     *
     * @see Status
     */
    private Integer status;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "TYPE", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type != null ? type : 0;
    }

    public void setType(Type type) {
        this.type = type.ordinal();
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
    @Column(name = "REMARK", nullable = true, length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    @Basic
    @Column(name = "VCS_TYPE", nullable = true)
    public Integer getVcsType() {
        return vcsType;
    }

    public void setVcsType(Integer vcsType) {
        this.vcsType = vcsType != null ? vcsType : 0;
    }

    public void setVcsType(VcsType vcsType) {
        this.vcsType = vcsType.ordinal();
    }

    @Basic
    @Column(name = "VCS_URL", nullable = true, length = 256)
    public String getVcsUrl() {
        return vcsUrl;
    }

    public void setVcsUrl(String vcsUrl) {
        this.vcsUrl = vcsUrl;
    }

    @Override
    @Basic
    @Column(name = "USER_NAME", nullable = true, length = 32)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    @Basic
    @Column(name = "PASS_WORD", nullable = true, length = 32)
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Basic
    @Column(name = "COMPILE_TYPE", nullable = true)
    public Integer getCompileType() {
        return compileType;
    }

    public void setCompileType(Integer compileType) {
        this.compileType = compileType != null ? compileType : 0;
    }

    public void setCompileType(CompileType compileType) {
        this.compileType = compileType.ordinal();
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
    @Column(name = "ADMIN_ID", nullable = true)
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status.ordinal();
    }

    @Transient
    @Override
    public String getLocalDir() {
        return SystemConfig.getSourceCodeDir(this);
    }

    @Transient
    @Override
    public String getRemoteUrl() {
        return this.vcsUrl;
    }


    public JSONObject toJson(){
        JSONObject data = new JSONObject();
        data.put("id", this.id);
        data.put("type", this.type);
        data.put("name", this.name);
        data.put("remark", this.remark);
        data.put("vcsType", this.vcsType);
        data.put("vcsUrl", this.vcsUrl);
        data.put("compileType", this.compileType);
        data.put("createTime", createTime != null ? FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(createTime) : "");
        data.put("status", this.status);
        return data;
    }
}
