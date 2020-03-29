package com.github.gnx.automate.event.impl;

import com.github.gnx.automate.event.AbstractEvent;
import com.github.gnx.automate.event.IEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 20:48
 */
@Component
public class EventPublisher implements IEventPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void publishEvent(AbstractEvent event) {
        applicationContext.publishEvent(event);
    }
}
