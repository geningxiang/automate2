package com.github.gnx.automate.common.exception;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/25 21:30
 */
public class AlreadyExistsException extends RuntimeException {

    private final Object source;

    public AlreadyExistsException(Object data){
        super();
        this.source = data;
    }

    public Object getSource() {
        return source;
    }
}
