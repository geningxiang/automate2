package com.automate.common.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 标注方式 允许未登陆时访问
 * @author: genx
 * @date: 2019/1/29 23:32
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowNoLogin {
}
