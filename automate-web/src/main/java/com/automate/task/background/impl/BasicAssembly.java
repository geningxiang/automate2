package com.automate.task.background.impl;

import com.automate.task.background.AbstractBackgroundAssembly;
import com.automate.task.background.ITask;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:19
 */
public class BasicAssembly extends AbstractBackgroundAssembly {

    private String name;
    private ITask[] tasks;

    private BasicAssembly(String name, ITask[] tasks, Set<String> locks){
        super(locks);
        this.name = name;
        this.tasks = tasks;
    }

    public static BasicAssembly create(String name, ITask[] tasks){
        Set<String> locks = new HashSet<>(16);
        for (ITask task : tasks) {
            if(task.getLocks() != null){
                for (String lock : task.getLocks()) {
                    locks.add(lock);
                }
            }
        }
        return new BasicAssembly(name, tasks, locks);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void run() {
        if(tasks != null){
            try{
                for (ITask task : tasks) {
                    task.invoke();
                }
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                //TODO 终止处理
            }

        }
    }
}
