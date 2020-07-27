package com.github.gnx.automate;

import com.github.gnx.automate.exec.ExecWorker;
import com.github.gnx.automate.exec.IExecConnection;
import com.github.gnx.automate.exec.IExecTemplate;
import com.github.gnx.automate.exec.DefaultMsgListener;
import com.github.gnx.automate.exec.local.LocalExecTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/6/23 16:42
 */
public class Mp4Test {

    public static void main(String[] args) throws Exception {

        IExecTemplate execTemplate = new LocalExecTemplate();

        execTemplate.execute(new ExecWorker<Object>() {
            @Override
            public Object doWork(IExecConnection execConnection) throws Exception {

                StringBuilder cmd = new StringBuilder();

                cmd.append("cd E:/tool/ffmpeg-4.2.3-win64-static/bin/;");

                cmd.append(" ffmpeg -i \"G:\\期货视频\\2020-06-19 09-19-39.mp4\" -codec copy -bsf:v h264_mp4toannexb \"G:\\期货视频\\2020-06-19 09-19-39.ts\"");


                System.out.println(cmd);

                execConnection.exec(cmd.toString(), new DefaultMsgListener());

                return null;
            }
        });

    }

}
