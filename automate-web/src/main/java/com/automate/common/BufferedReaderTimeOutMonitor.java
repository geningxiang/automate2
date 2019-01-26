package com.automate.common;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * BufferedReader 读取的 超时监控器
 * @author: genx
 * @date: 2018/6/21 23:32
 */
public class BufferedReaderTimeOutMonitor extends Thread{
    private long timeOut;
    private BufferedReader br;

    /**
     * 是否是超时原因退出
     */
    private boolean overTime = true;

    public BufferedReaderTimeOutMonitor(BufferedReader br, long timeOut){
        this.br = br;
        this.timeOut = timeOut;
    }

    @Override
    public void run(){
        try {

            synchronized (this) {
                wait(timeOut);
            }
            br.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doNotify(){
        synchronized (this) {
            this.notify();
        }
        overTime = false;
    }

    public boolean isOverTime() {
        return overTime;
    }
}
