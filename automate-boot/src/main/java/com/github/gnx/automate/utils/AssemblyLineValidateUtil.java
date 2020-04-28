package com.github.gnx.automate.utils;

import com.github.gnx.automate.assemblyline.config.AssemblyLineStepTask;
import com.github.gnx.automate.assemblyline.config.IAssemblyLineTaskConfig;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/27 23:57
 */
public class AssemblyLineValidateUtil {

    private static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static void valid(List<AssemblyLineStepTask> taskList) {
        StringBuilder sb = new StringBuilder(64);

        AssemblyLineStepTask assemblyLineStepTask;
        IAssemblyLineTaskConfig taskItem;
        for (int i = 0; i < taskList.size(); i++) {
            assemblyLineStepTask = taskList.get(i);

            for (int j = 0; j < assemblyLineStepTask.getTasks().size(); j++) {
                taskItem = assemblyLineStepTask.getTasks().get(j);
                Set<ConstraintViolation<IAssemblyLineTaskConfig>> constraintViolations = VALIDATOR.validate(taskItem);
                for (ConstraintViolation<IAssemblyLineTaskConfig> constraintViolation : constraintViolations) {
                    sb.append("阶段").append(i + 1).append("任务").append(j + 1).append(":").append(constraintViolation.getMessage()).append(System.lineSeparator());
                }
            }
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            throw new IllegalArgumentException(sb.toString());
        }

    }
}
