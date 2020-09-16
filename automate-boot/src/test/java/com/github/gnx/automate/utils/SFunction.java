package com.github.gnx.automate.utils;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 使Function获取序列化能力
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}