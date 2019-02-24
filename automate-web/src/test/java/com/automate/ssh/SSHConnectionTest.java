package com.automate.ssh;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 0:02
 */
public class SSHConnectionTest {

    public static void main(String[] args) throws Exception {
        SSHConnection s = new SSHConnection("47.100.63.232", 22,"root","Genx@linux");

        System.out.println(s.acquire());

        System.out.println(s.acquire());

        s.release();

        System.out.println(s.acquire());


        s.close();
    }

}