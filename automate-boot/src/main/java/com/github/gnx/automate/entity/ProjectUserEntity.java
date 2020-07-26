package com.github.gnx.automate.entity;

import com.github.gnx.automate.contants.ProjectUserLevel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2020/7/24 21:22
 */
@Entity
@Table(name = "ca2_project_user")
public class ProjectUserEntity {

    private int id;

    /**
     * 项目ID
     * 索引字段  INDEX `INDEX_PROJECT_USER_1` (`PROJECT_ID` ASC)
     */
    private int projectId;

    /**
     * 用户ID
     * 索引字段  INDEX `INDEX_PROJECT_USER_2` (`USER_ID` ASC)
     */
    private int userId;

    /**
     * 用户的权限等级
     * @see com.github.gnx.automate.contants.ProjectUserLevel
     */
    private short level;

    /**
     * 备注
     */
    private String remark;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PROJECT_ID", nullable = false)
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "USER_ID", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "LEVEL", nullable = false)
    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public void setLevel(ProjectUserLevel level) {
        this.level = (short) level.ordinal();
    }


    @Basic
    @Column(name = "REMARK", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
