package com.automate.controller.other;

import com.automate.common.annotation.AllowNoLogin;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecHelper;
import com.automate.exec.IExecStreamMonitor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 22:46
 */
@RestController
@RequestMapping("/cmd")
public class CmdController {

    private static Logger logger = LoggerFactory.getLogger(CmdController.class);

    @AllowNoLogin
    @RequestMapping("/exec")
    public String exec(String command, HttpServletRequest request){

        List<String> list = new ArrayList();
        if (StringUtils.isNotEmpty(command)) {
            String[] ss = command.split("\n|&&");
            for (String s : ss) {
                if (StringUtils.isNotBlank(s)) {
                    list.add(StringUtils.trim(s));
                }
            }
        }

        if (list.size() > 0) {
            final StringBuffer sb = new StringBuffer(1024);
            ExecCommand execCommand = null;
            try {
                execCommand = new ExecCommand(list, new IExecStreamMonitor() {
                    @Override
                    public void onStart(String command) {
                        sb.append(command).append(System.lineSeparator());
                    }

                    @Override
                    public void onMsg(String line) {
                        logger.debug(line);
                        sb.append(line).append(System.lineSeparator());
                    }

                    @Override
                    public void onEnd(int exitValue) {
                        sb.append("finished ").append(exitValue).append(System.lineSeparator());
                    }
                });
            } catch (IllegalAccessException e) {
                return e.getMessage();
            }
            ExecHelper.exec(execCommand);
            return sb.toString();
        }
        return "nothing is do";
    }

    public static void main(String[] args) {
        String command = "ping www.baidu.com && ping www.fcaimao.com \n ping m.weixin.com";
        String[] ss = command.split("\n|&&");

        for (String s : ss) {
            System.out.println(s);
        }
    }
}
