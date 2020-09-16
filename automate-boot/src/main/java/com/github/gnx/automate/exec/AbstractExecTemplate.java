package com.github.gnx.automate.exec;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 22:33
 */
public abstract class AbstractExecTemplate implements IExecTemplate {

    @Override
    public <T> T execute(ExecWorker<T> execWorker) throws Exception {
        IExecConnection connection = null;
        try {
            connection = createConnection();
            return execWorker.doWork(connection);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
