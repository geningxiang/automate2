package com.github.gnx.automate.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 20:47
 */
public abstract class AbstractEvent extends ApplicationEvent {

    public AbstractEvent(Object source) {
        super(source);
    }
}
