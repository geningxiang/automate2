package com.automate.event.handle;

import com.alibaba.fastjson.JSON;
import com.automate.event.po.SourceCodePullEvent;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/13 22:32
 */
@Component
public class SourceCodeHandler implements IEventHandler {

    @Subscribe
    public void lister(SourceCodePullEvent event) {
        System.out.println(JSON.toJSONString(event));
    }


}
