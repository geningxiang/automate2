package com.automate.task.background;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:16
 */
public interface ITask {

    default String[] getLocks(){
        return null;
    }

    void invoke() throws Exception;
}
