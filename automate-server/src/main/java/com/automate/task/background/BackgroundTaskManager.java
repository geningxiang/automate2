package com.automate.task.background;

import com.automate.common.SystemConfig;
import com.automate.common.thread.GlobalThreadPoolManager;
import com.automate.common.utils.EnvironmentPathUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 后台任务管理器
 *
 * @author: genx
 * @date: 2019/2/2 8:51
 */
@Component
@Scope("singleton")
public class BackgroundTaskManager implements Runnable {
    private static BackgroundTaskManager instance;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 后台任务最大并行数
     */
    private final int coreSize;

    /**
     * 用于执行并行任务的线程池
     */
    private final BackgroundThreadPool backgroundThreadPool;

    /**
     * 等待执行的队列
     */
    private final ArrayBlockingQueue<AbstractBackgroundTask> waitingQueue = new ArrayBlockingQueue(BackgroundContants.MAX_WAIT_SIZE);

    private IBackgroundMonitor backgroundTaskMonitor;

    private BackgroundTaskManager() {
        synchronized (BackgroundTaskManager.class) {
            if (instance != null) {
                throw new RuntimeException("BackgroundTaskManager is singleton, can not be instantiation many");
            }
            int coreSize = NumberUtils.toInt(SystemConfig.getProperty(BackgroundContants.KEY_TASK_BACKGROUND_MAXSIZE));
            if (coreSize <= 0) {
                coreSize = BackgroundContants.DEFAULT_CORE_SIZE;
            }
            this.coreSize = coreSize;
            logger.info("当前后台任务最大并行度: {}", coreSize);
            backgroundThreadPool = new BackgroundThreadPool(this.coreSize);

            instance = this;

            //全局线程管理来运行
            GlobalThreadPoolManager.getInstance().execute(this);
        }
    }

    /**
     * 提交后台任务
     *
     * @param r
     * @return
     */
    public boolean execute(AbstractBackgroundTask r) {
        if (r == null) {
            throw new NullPointerException();
        }
        boolean result = this.waitingQueue.offer(r);
        if (this.backgroundTaskMonitor != null) {
            this.backgroundTaskMonitor.onWait(r.getUniqueId(), r.getName(), this.waitingQueue.size());
        }
        return result;
    }

    /**
     * 获取正在执行的后台任务
     *
     * @return
     */
    public Collection<AbstractBackgroundTask> runningList() {
        return this.backgroundThreadPool.currentMap.values();
    }

    /**
     * 获取正在执行的后台任务数
     *
     * @return
     */
    public int runningSize() {
        return this.backgroundThreadPool.currentMap.size();
    }

    /**
     * 获取正在等待的后台任务
     *
     * @return
     */
    public List<AbstractBackgroundTask> waitingList() {
        return Lists.newArrayList(this.waitingQueue.iterator());
    }

    /**
     * 获取正在等待的任务数
     *
     * @return
     */
    public int waitingSize() {
        return this.waitingQueue.size();
    }

    @Override
    public void run() {
        while (true) {
            try {
                AbstractBackgroundTask abstractBackgroundTask = waitingQueue.take();
                postExecuteToThreadPool(abstractBackgroundTask);
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                logger.error("后台任务执行失败", e);
            }
        }
    }

    /**
     * 线程池 采用 AbortPolicy 拒绝策略，  被拒绝后等待1秒再重发
     *
     * @param abstractBackgroundTask
     * @throws InterruptedException
     */
    private void postExecuteToThreadPool(AbstractBackgroundTask abstractBackgroundTask) throws InterruptedException {
        while (!abstractBackgroundTask.isCancel()) {
            try {
                backgroundThreadPool.execute(abstractBackgroundTask);
                return;
            } catch (RejectedExecutionException e) {
                //线程池的并行度满了
                //TODO 如果有阻塞策略就更好了， 暂时先 sleep 1秒好了  以后再改
                Thread.sleep(1000);
            }
        }
    }

    public void setBackgroundTaskMonitor(IBackgroundMonitor backgroundTaskMonitor) {
        this.backgroundTaskMonitor = backgroundTaskMonitor;
    }

    private class BackgroundThreadPool extends ThreadPoolExecutor {
        private final Map<Long, AbstractBackgroundTask> currentMap;

        private BackgroundThreadPool(int maximumPoolSize) {
            super(0,
                    maximumPoolSize,
                    5L,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<>(),
                    new BackgroundTaskManager.CustomThreadFactory()
//                    new ThreadPoolExecutor.AbortPolicy()
            );

            currentMap = new ConcurrentHashMap<>(maximumPoolSize * 2);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            if (r instanceof AbstractBackgroundTask) {
                ((AbstractBackgroundTask) r).setStartTime(System.currentTimeMillis());
                currentMap.put(((AbstractBackgroundTask) r).getUniqueId(), (AbstractBackgroundTask) r);
                if (backgroundTaskMonitor != null) {
                    backgroundTaskMonitor.onStart(
                            ((AbstractBackgroundTask) r).getUniqueId(),
                            ((AbstractBackgroundTask) r).getName(),
                            this.currentMap.size(),
                            waitingQueue.size());
                }

                if (((AbstractBackgroundTask) r).getLocks() != null) {
                    try {
                        //申请锁
                        BackgroundLock.acquire((AbstractBackgroundTask) r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ((AbstractBackgroundTask) r).updateStatus(BackgroundStatus.running);
            }

            EnvironmentPathUtil.remove();
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            try {
                if (r instanceof AbstractBackgroundTask) {
                    ((AbstractBackgroundTask) r).setEndTime(System.currentTimeMillis());
                    this.currentMap.remove(((AbstractBackgroundTask) r).getUniqueId());

                    if (((AbstractBackgroundTask) r).getLocks() != null) {
                        //释放锁
                        BackgroundLock.release((AbstractBackgroundTask) r);
                    }

                    if (backgroundTaskMonitor != null) {
                        backgroundTaskMonitor.onComplete(
                                ((AbstractBackgroundTask) r).getUniqueId(),
                                ((AbstractBackgroundTask) r).getName(),
                                t == null ? BackgroundStatus.success.name() : BackgroundStatus.error.name(),
                                this.currentMap.size(),
                                waitingQueue.size());
                    }
                }

                EnvironmentPathUtil.remove();
            } finally {
                super.afterExecute(r, t);
            }
        }

        @Override
        public void execute(Runnable command) {
            if (command == null) {
                throw new NullPointerException();
            }
            //只允许 自定义的 AbstractBackgroundTask
            if (command instanceof AbstractBackgroundTask) {
                super.execute(command);
            } else {
                throw new IllegalArgumentException("this is allow for AbstractBackgroundTask");
            }
        }

    }

    private class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("BackgroundTask-Thread-" + count.addAndGet(1));
            return t;
        }
    }
}
