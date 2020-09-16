package com.github.gnx.automate.exec.local;

import com.github.gnx.automate.exec.AbstractExecTemplate;
import com.github.gnx.automate.exec.IExecConnection;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/23 20:22
 */
public class LocalExecTemplate extends AbstractExecTemplate {

    @Override
    public IExecConnection createConnection() throws Exception {
        return new LocalExecConnection();
    }
}
