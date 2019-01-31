package com.automate.guava;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

public class TestMultipleEvents {

    @Test
    public void testMultipleEvents() throws Exception {  
       
        EventBus eventBus = new EventBus("test");
        MultipleListener multiListener = new MultipleListener();  
       
        eventBus.register(multiListener);  
       
        eventBus.post(new Integer(100));
        eventBus.post(new Integer(200));  
        eventBus.post(new Integer(300));  
        eventBus.post(new Long(800)); 
        eventBus.post(new Long(800990));  
        eventBus.post(new Long(800882934));  
       
        System.out.println("LastInteger:"+multiListener.getLastInteger());
        System.out.println("LastLong:"+multiListener.getLastLong());
    }   
}