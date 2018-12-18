package com.automate.common;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/14 10:12
 */
public class ResponseEntity<T> {
    private int status;
    private String msg;
    private T data;
    private long timestamp;

    private ResponseEntity(ResponseStatus status, String msg, T data){
        this.status = status.value();
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResponseEntity<T> ok(T data) {
        return new ResponseEntity(ResponseStatus.OK,"", data);
    }

    public static <T> ResponseEntity<T> of(ResponseStatus status, String msg, T data) {
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
