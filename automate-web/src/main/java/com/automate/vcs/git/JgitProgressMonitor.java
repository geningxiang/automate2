package com.automate.vcs.git;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 23:46
 */
public class JgitProgressMonitor implements ProgressMonitor {
    private static final Logger logger = LoggerFactory.getLogger(JgitProgressMonitor.class);

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void start(int i) {
        logger.info(String.valueOf(i));
    }

    /**
     * 这个进度监控器是分步的
     *  update 方法是针对当前步骤的 进度+i
     *  比如 clone
     *  1. remote: Counting objects,0
     *  2. remote: Compressing objects,2809
     *  3. Receiving objects,7393
     *  4. Resolving deltas,3521
     *  5. Checking out files,568
     * @param step  步骤名称
     * @param i     总数
     */
    @Override
    public void beginTask(String step, int i) {
        logger.info("{},{}", step, i);
        count.set(0);
    }

    @Override
    public void update(int i) {
        logger.info(String.valueOf(count.addAndGet(i)));
    }

    @Override
    public void endTask() {
        logger.info("endTask");
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
