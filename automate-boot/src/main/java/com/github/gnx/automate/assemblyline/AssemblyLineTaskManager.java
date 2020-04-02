package com.github.gnx.automate.assemblyline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/29 13:18
 */
@Component
public class AssemblyLineTaskManager implements IAssemblyLineRunnableListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final int corePoolSize = 10;

    private final int queueMaxSize = 1000;

    private final ArrayBlockingQueue waitingQueue = new ArrayBlockingQueue(queueMaxSize);
    private final ConcurrentLinkedQueue<AssemblyLineRunnable> runningQueue = new ConcurrentLinkedQueue();

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, corePoolSize, 5L, TimeUnit.SECONDS, waitingQueue, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public void execute(int assemblyLineLogId) {
        AssemblyLineRunnable assemblyLineRunnable = new AssemblyLineRunnable(assemblyLineLogId, this);
        threadPoolExecutor.execute(assemblyLineRunnable);
    }

    @Override
    public void onStart(AssemblyLineRunnable assemblyLineRunnable) {
        logger.debug("task on start : {}", assemblyLineRunnable.getAssemblyLineLogId());
        runningQueue.add(assemblyLineRunnable);
    }

    @Override
    public void onFinished(AssemblyLineRunnable assemblyLineRunnable) {
        logger.debug("task on finished : {}", assemblyLineRunnable.getAssemblyLineLogId());
        runningQueue.remove(assemblyLineRunnable);
    }

    public ArrayBlockingQueue getWaitingQueue() {
        return waitingQueue;
    }

    public ConcurrentLinkedQueue<AssemblyLineRunnable> getRunningQueue() {
        return runningQueue;
    }
}
