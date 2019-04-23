package com.automate.entity;

import com.alibaba.fastjson.JSONObject;
import com.automate.common.SystemConfig;
import com.automate.contants.CompileType;
import com.automate.contants.ProjectType;
import com.automate.contants.VcsType;
import com.automate.vcs.IVCSRepository;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/23 23:03
 */
@Entity
@Table(name = "CA2_PROJECT")
public class ProjectEntity implements IVCSRepository {

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
    private Byte type;
    private String name;
    private String remark;
    private int vcsType;
    private String vcsUrl;
    private String vcsUserName;
    private String vcsPassWord;
    private int compileType;
    private Timestamp createTime;
    private Integer userId;
    private int status;

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
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public void setType(ProjectType type) {
        this.type = (byte) type.ordinal();
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
    public int getVcsType() {
        return vcsType;
    }

    public void setVcsType(int vcsType) {
        this.vcsType = vcsType;
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

    @Basic
    @Column(name = "VCS_USER_NAME", nullable = true, length = 32)
    public String getVcsUserName() {
        return vcsUserName;
    }

    public void setVcsUserName(String vcsUserName) {
        this.vcsUserName = vcsUserName;
    }

    @Basic
    @Column(name = "VCS_PASS_WORD", nullable = true, length = 32)
    public String getVcsPassWord() {
        return vcsPassWord;
    }

    public void setVcsPassWord(String vcsPassWord) {
        this.vcsPassWord = vcsPassWord;
    }

    @Basic
    @Column(name = "COMPILE_TYPE", nullable = true)
    public int getCompileType() {
        return compileType;
    }

    public void setCompileType(int compileType) {
        this.compileType = compileType;
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
    @Column(name = "USER_ID", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status.ordinal();
    }

    public JSONObject toJson() {
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

    @Transient
    @Override
    public String getUserName() {
        return this.vcsUserName;
    }

    @Transient
    @Override
    public String getPassWord() {
        return this.vcsPassWord;
    }


}
