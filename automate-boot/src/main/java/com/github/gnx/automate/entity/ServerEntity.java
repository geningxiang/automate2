package com.github.gnx.automate.entity;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 服务器
 *
 * @author: genx
 * @date: 2018/12/17 23:45
 */
@Entity
@Table(name = "CA2_SERVER")
public class ServerEntity {

    /**
     * 服务器类型
     */
    public enum Type {
        /**
         * linux服务器
         */
        LINUX,
        /**
         * windows服务器（暂不支持）
         */
        WINDOWS
    }

    /**
     * 环境
     */
    public enum Environment {
        /**
         * 测试环境
         */
        TEST,
        /**
         * 正式环境
         */
        FORMAL

    }

    /**
     * 状态
     */
    public enum Status {
        /**
         * 删除的
         */
        DELETED,
        /**
         * 正常的
         */
        NORMAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /**
     * @see Type
     */
    @Column(name = "TYPE")
    private Integer type;

    /**
     * 名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 内网IP
     */
    @Column(name = "INSIDE_IP")
    private String insideIp;

    /**
     * 外网IP
     */
    @Column(name = "OUTSIDE_IP")
    private String outsideIp;

    /**
     * ssh IP
     */
    @Column(name = "SSH_HOST")
    private String sshHost;
    /**
     * ssh 端口
     */

    @Column(name = "SSH_PORT")
    private Integer sshPort;

    /**
     * ssh 用户
     */
    @Column(name = "SSH_USER")
    private transient String sshUser;

    /**
     * ssh 密码
     */
    @Column(name = "SSH_PWD")
    private transient String sshPwd;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 环境
     */
    @Column(name = "ENVIRONMENT")
    private Integer environment;

    /**
     * 状态
     */
    @Column(name = "STATUS")
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInsideIp() {
        return insideIp;
    }

    public void setInsideIp(String insideIp) {
        this.insideIp = insideIp;
    }

    public String getOutsideIp() {
        return outsideIp;
    }

    public void setOutsideIp(String outsideIp) {
        this.outsideIp = outsideIp;
    }

    public String getSshHost() {
        return sshHost;
    }

    public void setSshHost(String sshHost) {
        this.sshHost = sshHost;
    }

    public Integer getSshPort() {
        return sshPort;
    }

    public void setSshPort(Integer sshPort) {
        this.sshPort = sshPort;
    }

    public String getSshUser() {
        return sshUser;
    }

    public void setSshUser(String sshUser) {
        this.sshUser = sshUser;
    }

    public String getSshPwd() {
        return sshPwd;
    }

    public void setSshPwd(String sshPwd) {
        this.sshPwd = sshPwd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnvironment() {
        return environment;
    }

    public void setEnvironment(Integer environment) {
        this.environment = environment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
