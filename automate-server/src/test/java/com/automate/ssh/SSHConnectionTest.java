package com.automate.ssh;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 0:02
 */
public class SSHConnectionTest {

    public static void main(String[] args) throws Exception {
        SSHConnection s = new SSHConnection("47.100.63.232", 22, "root", "Genx@linux");

        System.out.println("连接成功啦");


        System.out.println(s.acquire());

        System.out.println(s.acquire());

        s.release();

        System.out.println(s.acquire());

        for (int i = 0; i < 1000; i++) {
            System.out.println(s.isConnected());
            Thread.sleep(1000);
        }



        System.out.println("close");
        s.close();
    }

}