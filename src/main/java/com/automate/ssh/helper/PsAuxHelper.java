package com.automate.ssh.helper;

import com.automate.common.CmdResult;
import com.automate.contants.SSHContants;
import com.automate.entity.ServerEntity;
import com.automate.ssh.ISSHProxy;
import com.automate.ssh.SSHProxyImpl;
import com.automate.ssh.vo.LinuxProcessVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/19 20:57
 */
public class PsAuxHelper {

    public static List<LinuxProcessVO> psAux(Optional<ServerEntity> sshClient) throws Exception {
        ISSHProxy isshProxy = new SSHProxyImpl(sshClient.get());
        //ww 代表不截断
        CmdResult sshResult = isshProxy.execCommand("ps aux ww | grep -v grep | grep -E \"" + SSHContants.PS_AUX_GREP + "\"");
        List<LinuxProcessVO> list = new ArrayList(64);
        if (sshResult.isSuccess()) {
            LinuxProcessVO processVO;
            for (String s : sshResult.getResult()) {
                processVO = parseToLinuxProcessVO(s);
                if (processVO != null) {
                    list.add(processVO);
                }
            }
        }
        isshProxy.close();
        return list;
    }

    private static LinuxProcessVO parseToLinuxProcessVO(String s) {
        //USER        PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
        String[] ss = s.split("\\s+");
        if (ss.length >= 11) {
            String[] tt = Arrays.copyOfRange(ss, 10, ss.length);
            System.out.println(StringUtils.join(tt, " "));
            LinuxProcessVO linuxProcessVO = new LinuxProcessVO();
            linuxProcessVO.setUser(ss[0]);
            linuxProcessVO.setPid(NumberUtils.toInt(ss[1]));
            linuxProcessVO.setCpuRate(NumberUtils.toFloat(ss[2]) / 100);
            linuxProcessVO.setMemRate(NumberUtils.toFloat(ss[3]) / 100);
            linuxProcessVO.setVsz(NumberUtils.toInt(ss[4]));
            linuxProcessVO.setRss(NumberUtils.toInt(ss[5]));
            linuxProcessVO.setTty(ss[6]);
            linuxProcessVO.setStat(ss[7]);
            linuxProcessVO.setTime(parseToMinute(ss[9]));
            linuxProcessVO.setCommand(ss[10]);
            linuxProcessVO.setArgs(StringUtils.join(Arrays.copyOfRange(ss, 11, ss.length), " "));
            return linuxProcessVO;
        }
        return null;
    }


    /**
     * 132:23 格式转 分钟
     *
     * @param time
     * @return
     */
    private static int parseToMinute(String time) {
        int minute = -1;
        if (StringUtils.isNoneEmpty(time)) {
            String[] ss = time.split(":");
            if (ss.length == 2) {
                minute = NumberUtils.toInt(ss[0]) * 60 + NumberUtils.toInt(ss[1]);
            }
        }
        return minute;
    }
}
