package com.automate.entity;

import com.automate.contants.AdminUserContants;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 23:17
 */
@Entity
@Table(name = "CA2_ADMIN_USER")
public class AdminUserEntity {

    /**
     * 管理员状态
     */
    public enum Status{
        /**
         * 注销的
         */
        CANCEL,
        /**
         * 激活的
         */
        ACTIVATE
    }

    private Integer id;
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
    private Byte level;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 最后修改时间
     */
    private Timestamp updateTime;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮件地址
     */
    private String email;

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
    @Column(name = "USER_NAME", nullable = true, length = 32)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "PASS_WORD", nullable = true, length = 16)
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Basic
    @Column(name = "REAL_NAME", nullable = true, length = 32)
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Basic
    @Column(name = "LEVEL", nullable = true)
    public Byte getLevel() {
        return level;
    }

    /**
     * 设置等级 现在只有2级
     * @see AdminUserContants#ADMIN_USER_LEVEL_ROOT
     * @see AdminUserContants#ADMIN_USER_LEVEL_NORMAL
     * @param level
     */
    public void setLevel(Byte level) {
        this.level = level;
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


    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "MOBILE", nullable = true, length = 16)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
