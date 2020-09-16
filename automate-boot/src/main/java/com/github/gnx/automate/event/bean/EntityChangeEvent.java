package com.github.gnx.automate.event.bean;

import org.springframework.context.ApplicationEvent;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/31 21:31
 */
public class EntityChangeEvent extends ApplicationEvent {

    private final Object data;

    public EntityChangeEvent(Object data) {
        super(data);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

}
