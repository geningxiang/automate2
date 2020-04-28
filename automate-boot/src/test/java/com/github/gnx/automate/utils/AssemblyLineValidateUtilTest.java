package com.github.gnx.automate.utils;

import com.github.gnx.automate.assemblyline.config.ExecTask;
import com.github.gnx.automate.assemblyline.config.IAssemblyLineTaskConfig;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/28 0:04
 */
class AssemblyLineValidateUtilTest {

    private static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();


    public static void main(String[] args) {

        IAssemblyLineTaskConfig taskItem = new ExecTask();

        Set<ConstraintViolation<IAssemblyLineTaskConfig>> constraintViolations = VALIDATOR.validate(taskItem);

        for (ConstraintViolation<IAssemblyLineTaskConfig> constraintViolation : constraintViolations) {
            System.err.println(constraintViolation.getMessage());
        }


    }

}