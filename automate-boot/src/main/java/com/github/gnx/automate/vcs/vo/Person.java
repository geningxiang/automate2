package com.github.gnx.automate.vcs.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 版本控制系统的 人员
 * @author: genx
 * @date: 2019/1/26 22:56
 */
public class Person implements Serializable {
    private String name;
    private String emailAddress;

    public Person(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
