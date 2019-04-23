package com.automate.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/24 23:38
 */
@Entity
@Table(name = "ca2_container_type", schema = "automate", catalog = "")
public class ContainerTypeEntity {
    private int id;
    private String name;
    private String version;
    private String scriptInstall;
    private String scriptStart;
    private String scriptStop;
    private String scriptFind;
    private String fileConfig;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "VERSION", nullable = true, length = 16)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "SCRIPT_INSTALL", nullable = true, length = -1)
    public String getScriptInstall() {
        return scriptInstall;
    }

    public void setScriptInstall(String scriptInstall) {
        this.scriptInstall = scriptInstall;
    }

    @Basic
    @Column(name = "SCRIPT_START", nullable = true, length = -1)
    public String getScriptStart() {
        return scriptStart;
    }

    public void setScriptStart(String scriptStart) {
        this.scriptStart = scriptStart;
    }

    @Basic
    @Column(name = "SCRIPT_STOP", nullable = true, length = -1)
    public String getScriptStop() {
        return scriptStop;
    }

    public void setScriptStop(String scriptStop) {
        this.scriptStop = scriptStop;
    }

    @Basic
    @Column(name = "SCRIPT_FIND", nullable = true, length = -1)
    public String getScriptFind() {
        return scriptFind;
    }

    public void setScriptFind(String scriptFind) {
        this.scriptFind = scriptFind;
    }

    @Basic
    @Column(name = "FILE_CONFIG", nullable = true, length = -1)
    public String getFileConfig() {
        return fileConfig;
    }

    public void setFileConfig(String fileConfig) {
        this.fileConfig = fileConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContainerTypeEntity that = (ContainerTypeEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(version, that.version) &&
                Objects.equals(scriptInstall, that.scriptInstall) &&
                Objects.equals(scriptStart, that.scriptStart) &&
                Objects.equals(scriptStop, that.scriptStop) &&
                Objects.equals(scriptFind, that.scriptFind) &&
                Objects.equals(fileConfig, that.fileConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, version, scriptInstall, scriptStart, scriptStop, scriptFind, fileConfig);
    }
}
