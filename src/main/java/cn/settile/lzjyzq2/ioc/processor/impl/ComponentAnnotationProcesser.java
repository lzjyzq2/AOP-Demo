package cn.settile.lzjyzq2.ioc.processor.impl;

import cn.settile.lzjyzq2.ioc.ApplicationContext;
import cn.settile.lzjyzq2.ioc.ApplicationLoader;
import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.core.BeanDefinition;
import cn.settile.lzjyzq2.ioc.processor.AnnotationProcessor;
import cn.settile.lzjyzq2.ioc.utils.BeanUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 注解Component的处理器
 * @author lzjyz
 */
public class ComponentAnnotationProcesser implements AnnotationProcessor {

    static {
        ApplicationLoader.registerProccesser(Component.class,new ComponentAnnotationProcesser());
    }

    @Override
    public void process(ApplicationContext context, Annotation annotation, Class<?> clazz, Method method, Field field, Object[] args) {
        if(annotation!=null){
            Component component = (Component) annotation;
            if(clazz.isMemberClass()){
                if (!Modifier.isStatic(clazz.getModifiers())){
                    throw new RuntimeException("类"+clazz.getName()+"不是静态类");
                }
            }
            if(clazz.isAnnotation()){
                return;
            }
            if(Modifier.isInterface(clazz.getModifiers())){
                throw new RuntimeException("类"+clazz.getName()+"是一个接口");
            }
            if (Modifier.isAbstract(clazz.getModifiers())){
                throw new RuntimeException("类"+clazz.getName()+"是一个抽象类");
            }
            if(clazz.isAnonymousClass()){
                throw new RuntimeException("类"+clazz.getName()+"是一个匿名类");
            }
            BeanDefinition beanDefinition = new BeanDefinition();
            if("".equals(component.name())){
                beanDefinition.setBeanName(BeanUtil.getLowerFirstCaseName(clazz));
            }else {
                beanDefinition.setBeanName(component.name());
            }
            beanDefinition.setClassName(clazz.getName());
            beanDefinition.setScope(component.scope());
            beanDefinition.setPropertyList(BeanUtil.scanPropertyList(clazz));
            context.registerBeanDefinition(beanDefinition);
            System.out.println(clazz.getName());
        }
    }

}
