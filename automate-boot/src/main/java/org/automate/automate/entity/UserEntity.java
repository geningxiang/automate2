package org.automate.automate.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 23:17
 */
@Entity
@Table(name = "CA2_USER")
public class UserEntity {

    /**
     * 管理员状态
     */
    public enum Status {
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
    private transient String passWord;
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 级别
     */
    private int level;

    /**
     * 状态
     */
    private int status;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮件地址
     */
    private String email;

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
    public int getLevel() {
        return level;
    }

    /**
     * 设置等级 现在只有2级
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
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


    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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
