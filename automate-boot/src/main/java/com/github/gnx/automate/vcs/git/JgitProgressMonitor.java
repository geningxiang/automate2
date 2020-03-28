package com.github.gnx.automate.vcs.git;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
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

    private String step;
    private int stepTotal;
    private int stepProgress;

    private double lastPrintRate;

    @Override
    public void start(int i) {
        logger.info(String.valueOf(i));
    }

    /**
     * 这个进度监控器是分步的
     *  doUpdate 方法是针对当前步骤的 进度+i
     *  比如 clone
     *  1. remote: Counting objects,0
     *  2. remote: Compressing objects,2809
     *  3. Receiving objects,7393
     *  4. Resolving deltas,3521
     *  5. Checking out files,568
     * @param step  步骤名称
     * @param stepTotal     总数
     */
    @Override
    public void beginTask(String step, int stepTotal) {
        this.step = step;
        this.stepTotal = stepTotal;
        this.stepProgress = 0;
        this.lastPrintRate = 0;
    }

    @Override
    public void update(int i) {
        stepProgress += i;
        if(stepTotal > 0) {
            double rate = stepProgress * 100d / stepTotal;
            if(rate - this.lastPrintRate > 1){
                logger.info("进度 {} : {}/{}\t{}%", step, stepProgress, stepTotal, BigDecimal.valueOf(rate).setScale(2, BigDecimal.ROUND_HALF_UP));
                this.lastPrintRate = rate;
            }
        }

    }

    @Override
    public void endTask() {
        logger.info("{} 已完成", this.step);
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
