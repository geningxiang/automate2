package com.automate.entity;

import com.alibaba.fastjson.JSONObject;
import com.automate.ssh.ISSHClient;

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
public class ServerEntity implements ISSHClient {

    /**
     * 服务器类型
     */
    public enum Type{
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
    public enum Environment{
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
    public enum Status{
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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    private String sshUser;

    /**
     * ssh 密码
     */
    @Column(name = "SSH_PWD")
    private String sshPwd;

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

    @Override
    public String getSshHost() {
        return sshHost;
    }

    public void setSshHost(String sshHost) {
        this.sshHost = sshHost;
    }

    @Override
    public Integer getSshPort() {
        return sshPort;
    }

    public void setSshPort(Integer sshPort) {
        this.sshPort = sshPort;
    }

    @Override
    public String getSshUser() {
        return sshUser;
    }

    public void setSshUser(String sshUser) {
        this.sshUser = sshUser;
    }

    @Override
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

    public JSONObject toJson(){
        JSONObject data = new JSONObject();
        data.put("id", this.id);
        data.put("type", this.type);
        data.put("name", this.name);
        data.put("insideIp", this.insideIp);
        data.put("outsideIp", this.outsideIp);
        data.put("remark", this.remark);
        data.put("environment", this.environment);
        data.put("status", this.status);
        return data;
    }
}
