package com.github.gnx.automate.field.req;

import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/2 13:23
 */
public class FileCompareField {

    /**
     * 类型
     * 1 产物
     * 2 容器
     */
    private int type;

    /**
     * 来源ID
     * type == 1 {@link ProductEntity#getId()}
     * type == 2 {@link ContainerEntity#getId()}
     */
    private int sourceId;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
