package com.github.gnx.automate.exec;

import com.github.gnx.automate.common.IMsgListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/20 22:56
 */
public class DefaultMsgListener implements IMsgListener {

    private Logger logger = LoggerFactory.getLogger(DefaultMsgListener.class);

    private IMsgListener parentMsgListener;

    private StringBuffer content = new StringBuffer(1024);

    public DefaultMsgListener(IMsgListener parentMsgListener){
        this.parentMsgListener = parentMsgListener;
    }

    public DefaultMsgListener(){

    }


    @Override
    public IMsgListener append(CharSequence csq) {
        if(StringUtils.isNotBlank(csq)) {
            logger.debug(csq.toString());
        }
        content.append(csq);
        if(parentMsgListener != null){
            parentMsgListener.append(csq);
        }
        return this;
    }


    public String getContent() {
        return content.toString();
    }
}
