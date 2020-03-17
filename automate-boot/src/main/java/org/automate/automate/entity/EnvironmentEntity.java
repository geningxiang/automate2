package org.automate.automate.entity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  环境、容器
 * @author: genx
 * @date: 2019/1/2 22:39
 */
public class EnvironmentEntity {
    private Integer id;

    /**
     * 服务器ID {@link ServerEntity}
     */
    private Integer serverId;

    /**
     * 容器类型 {@link com.automate.ssh.environment.EnvironmentType}
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 主路径
     */
    private String home;

    /**
     * 主端口  如果有的话
     */
    private Integer port;

    private String version;

    private String config;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
