package com.automate.task.background.impl;

import com.automate.exec.ExecCommand;
import com.automate.task.background.ITask;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/3 9:59
 */
public class MavenTask extends ExecTask {


    public enum Lifecycle{
        clean,
        validate,
        compile,
        test,
        /**
         * 打包   package 是关键字
         */
        pack,
        verify,
        install,
        site,
        deploy
    }


    @Override
    public ExecCommand getExecCommand() throws IllegalAccessException {
        StringBuilder cmd = new StringBuilder("mvn");
        String shortcut = StringUtils.trimToEmpty(this.shortcut);

        //3个命令需要特殊处理
        if(this.clean || Lifecycle.clean.name().equals(shortcut)){
            cmd.append(" clean");
        }

        if(Lifecycle.pack.name().equals(shortcut) || "package".equals(shortcut)){
            cmd.append(" package");
        } else {
            if(Lifecycle.test.name().equals(shortcut)){
                this.testSkip = false;
            }

            for (Lifecycle l : Lifecycle.values()) {
                if(l.name().equals(shortcut)){
                    cmd.append(" ").append(shortcut);
                }
            }
        }

        if(this.testSkip){
            cmd.append("  -DskipTests=true");
        }
        if(StringUtils.isNotEmpty(this.custom)){
            cmd.append(" ").append(this.custom);
        }

        ExecCommand execCommand = new ExecCommand(cmd.toString());
        return execCommand;
    }

    private boolean clean = true;
    private boolean testSkip = true;

    /**
     * 快捷方式
     * 与 Lifecycle对应
     * @see Lifecycle
     */
    private String shortcut;

    /**
     * 自定义的内容
     * 比如   sonar代码审查
     * sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=00996135a7d5148b7fab335d4dba233c3dcb7afc
     */
    private String custom;


}
