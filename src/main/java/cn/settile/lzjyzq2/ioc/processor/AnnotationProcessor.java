package cn.settile.lzjyzq2.ioc.processor;

import cn.settile.lzjyzq2.ioc.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author lzjyz
 */
public interface AnnotationProcessor {

    /**
     * 被处理对象
     * @param context 上下文
     * @param clazz 目标类
     * @param method 目标方法
     * @param field 属性
     * @param args 方法参数
     */
    void process(ApplicationContext context, Annotation annotation, Class<?> clazz, Method method, Field field, Object[] args);
}
