package com.automate.event;

import com.automate.event.po.SourceCodePushEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/13 22:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class EventCenterTest {


    @Test
    public void test() throws InterruptedException {
        SourceCodePushEvent event = new SourceCodePushEvent(1, "master", "ec58318d01eadd8c7f16854024b1bcdb694ea107");

        EventCenter.post(event);

        Thread.sleep(50000);
    }
}