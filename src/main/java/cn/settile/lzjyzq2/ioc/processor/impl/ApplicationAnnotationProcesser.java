package cn.settile.lzjyzq2.ioc.processor.impl;

import cn.settile.lzjyzq2.ioc.ApplicationContext;
import cn.settile.lzjyzq2.ioc.ApplicationLoader;
import cn.settile.lzjyzq2.ioc.annotation.Application;
import cn.settile.lzjyzq2.ioc.core.BeanRegister;
import cn.settile.lzjyzq2.ioc.processor.AnnotationProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Application注解处理器
 * @author lzjyz
 */
public class ApplicationAnnotationProcesser implements AnnotationProcessor {

    static {
        ApplicationLoader.registerProccesser(Application.class,new ApplicationAnnotationProcesser());
    }

    @Override
    public void process(ApplicationContext context, Annotation annotation, Class<?> clazz, Method method, Field field, Object[] args) {
        BeanRegister beanRegister =(BeanRegister) context.getBean("beanRegister");
        beanRegister.start(clazz.getPackage().getName());
    }
}
