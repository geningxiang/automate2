package com.github.gnx.automate.common;

import org.springframework.http.HttpStatus;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author: genx
 * @date: 2018/12/14 10:12
 */
public class ResponseEntity<T> {
    /**
     * 状态码
     */
    private int status;

    /**
     * 信息
     */
    private String msg;

    /**
     * 具体的数据
     */
    private T data;

    /**
     * 服务器时间戳
     */
    private long timestamp;


    private ResponseEntity(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }


    public static <T> ResponseEntity<T> ok(T data) {
        return ok("", data);
    }

    public static <T> ResponseEntity<T> ok(String msg, T data) {
        return new ResponseEntity(HttpStatus.OK.value(), msg, data);
    }

    public static ResponseEntity ok() {
        return new ResponseEntity(HttpStatus.OK.value(), "", null);
    }

    public static ResponseEntity of(HttpStatus status, String msg) {
        return new ResponseEntity(status.value(), msg, null);
    }

    public static <T> ResponseEntity<T> of(HttpStatus status, String msg, T data) {
        return new ResponseEntity(status.value(), msg, data);
    }

    public static <T> ResponseEntity<T> of(int status, String msg, T data) {
        return new ResponseEntity(status, msg, data);
    }


    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
