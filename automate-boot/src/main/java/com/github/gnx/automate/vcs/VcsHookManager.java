package com.github.gnx.automate.vcs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/30 23:55
 */
@Component
public class VcsHookManager {

    @Autowired
    private IVcsHookHandler[] vcsHookHandlers;

    public void doHandle(HttpServletRequest request) {


    }

}
