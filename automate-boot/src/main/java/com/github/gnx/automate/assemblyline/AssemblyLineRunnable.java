package com.github.gnx.automate.assemblyline;

import com.github.gnx.automate.assemblyline.field.AssemblyLineStepTask;
import com.github.gnx.automate.assemblyline.field.AssemblyLineTask;
import com.github.gnx.automate.assemblyline.field.ITaskConfig;
import com.github.gnx.automate.assemblyline.field.LocalShellTaskConfig;
import com.github.gnx.automate.assemblyline.plugins.AssemblyLineShellPluginImpl;
import com.github.gnx.automate.common.SpringUtils;
import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.service.IProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 21:27
 */
public class AssemblyLineRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(AssemblyLineRunnable.class);

    private final IAssemblyLineRunnableListener assemblyLineRunnableListener;

    private final AssemblyLineTask assemblyLineTask;

    public AssemblyLineRunnable(AssemblyLineTask assemblyLineTask, IAssemblyLineRunnableListener assemblyLineRunnableListener) {
        this.assemblyLineTask = assemblyLineTask;
        this.assemblyLineRunnableListener = assemblyLineRunnableListener;
    }

    @Override
    public void run() {
        try {
            this.assemblyLineRunnableListener.onStart(this);
            final AssemblyLineEnv assemblyLineEnv = new AssemblyLineEnv();

            if(assemblyLineTask.getProjectId() > 0) {
                IProjectService projectService = SpringUtils.getBean(IProjectService.class);
                Optional<ProjectEntity> projectEntity = projectService.getModel(assemblyLineTask.getProjectId());
                projectEntity.ifPresent(assemblyLineEnv::setProjectEntity);
            }
            IAssemblyLineProgressListener assemblyLineProgressListener = null;
            for (AssemblyLineStepTask stepTask : this.assemblyLineTask.getStepTasks()) {
                for (ITaskConfig specificTask : stepTask.getSpecificTasks()) {
                    AssemblyLinePluginManager.execute(assemblyLineEnv, specificTask, assemblyLineProgressListener);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.assemblyLineRunnableListener.onFinished(this);
        }
    }

    public AssemblyLineTask getAssemblyLineTask() {
        return assemblyLineTask;
    }

}
