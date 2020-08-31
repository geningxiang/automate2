package com.github.gnx.automate.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/19 16:28
 */
public interface IEventPublisher {

    void publishEvent(ApplicationEvent event);

}
