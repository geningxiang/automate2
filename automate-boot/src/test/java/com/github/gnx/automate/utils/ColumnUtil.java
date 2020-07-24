package com.github.gnx.automate.utils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ColumnUtil {

    public static <T, R> String getName(SFunction<T, R> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(false);


        System.out.println(serializedLambda.getImplClass());
        System.out.println(serializedLambda.getImplMethodSignature());

        return serializedLambda.getImplMethodName();

        // 从lambda信息取出method、field、class等
//        String fieldName = serializedLambda.getImplMethodName().substring("get".length());
//        fieldName = fieldName.replaceFirst(fieldName.charAt(0) + "", (fieldName.charAt(0) + "").toLowerCase());
//        Field field;
//        try {
//            field = Class.forName(serializedLambda.getImplClass().replace("/", ".")).getDeclaredField(fieldName);
//        } catch (ClassNotFoundException | NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        }

//        // 从field取出字段名，可以根据实际情况调整
//        TableField tableField = field.getAnnotation(TableField.class);
//        if (tableField != null && tableField.value().length() > 0) {
//            return tableField.value();
//        } else {
//            return fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
//        }
    }
}