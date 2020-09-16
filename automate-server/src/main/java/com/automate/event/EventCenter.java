package com.automate.event;

import com.automate.event.handle.IEventHandler;
import com.automate.event.po.IEvent;
import com.google.common.eventbus.AsyncEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/13 22:28
 */
@Component
public class EventCenter {

    private final static AsyncEventBus eventBus;

    private final static Logger logger = LoggerFactory.getLogger(EventCenter.class);

    static {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3L, TimeUnit.SECONDS, new LinkedBlockingQueue(100));
        eventBus = new AsyncEventBus(executorService);
    }

    private EventCenter() {
    }

    ;

    @Autowired
    private List<IEventHandler> eventHandlers;

    @PostConstruct
    public void init() {
        for (IEventHandler eventHandler : eventHandlers) {
            logger.debug("注册事件处理器:{}", eventHandler.getClass().getName());
            eventBus.register(eventHandler);
        }
    }

    public static void post(IEvent event) {
        eventBus.post(event);
    }
}
