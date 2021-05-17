package cn.settile.lzjyzq2.ioc;

import cn.settile.lzjyzq2.ioc.core.BeanRegister;
import cn.settile.lzjyzq2.ioc.core.impl.DefaultBeanRegister;
import cn.settile.lzjyzq2.ioc.processor.AnnotationProcessor;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lzjyz
 */
public final class ApplicationLoader {

    private static ApplicationContext applicationContext;

    private static Map<Class<? extends Annotation>, AnnotationProcessor> proccessers = new ConcurrentHashMap<>();

    public static void load(String packetName){
        applicationContext = new ApplicationContext();
        applicationContext.createBean(applicationContext);
        BeanRegister beanRegister = new DefaultBeanRegister(applicationContext,proccessers);
        applicationContext.createBean("beanRegister",beanRegister);
        beanRegister.start("cn.settile.lzjyzq2",packetName);

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void load(Class<?> clazz){
        load(clazz.getPackage().getName());
    }

    public static void registerProccesser(Class<? extends Annotation> clazz,AnnotationProcessor proccesser){
        proccessers.put(clazz,proccesser);
    }

    public static void removeProccesser(Class<? extends Annotation> clazz){
        proccessers.remove(clazz);
    }
}
