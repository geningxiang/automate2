package com.genx.auotmate.entity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 服务器
 *
 * @author: genx
 * @date: 2018/12/17 23:45
 */
public class ServerEntity {

    private Integer id;

    private String intranetIp;

    private String outIp;

    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntranetIp() {
        return intranetIp;
    }

    public void setIntranetIp(String intranetIp) {
        this.intranetIp = intranetIp;
    }

    public String getOutIp() {
        return outIp;
    }

    public void setOutIp(String outIp) {
        this.outIp = outIp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
