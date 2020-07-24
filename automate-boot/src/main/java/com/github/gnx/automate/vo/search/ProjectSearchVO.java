package com.github.gnx.automate.vo.search;

import com.github.gnx.automate.common.jpa.IJpaSearch;
import com.github.gnx.automate.entity.ProjectEntity;

import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/6/28 23:55
 */
public class ProjectSearchVO implements IJpaSearch {

    private Integer id;

    private Integer type;

    private String name;

    private Integer vcsType;

    private Date createTimeStart;

    private Date createTimeEnd;

    private Integer userId;

    private Set<Integer> statusIn;

    @Override
    public Class rootClass() {
        return ProjectEntity.class;
    }

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

    public Integer getVcsType() {
        return vcsType;
    }

    public void setVcsType(Integer vcsType) {
        this.vcsType = vcsType;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<Integer> getStatusIn() {
        return statusIn;
    }

    public void setStatusIn(Set<Integer> statusIn) {
        this.statusIn = statusIn;
    }
}
