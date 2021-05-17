package cn.settile.lzjyzq2.ioc.core.impl;

import cn.settile.lzjyzq2.ioc.ApplicationContext;
import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.core.BeanDefinition;
import cn.settile.lzjyzq2.ioc.core.BeanRegister;
import cn.settile.lzjyzq2.ioc.processor.AnnotationProcessor;
import cn.settile.lzjyzq2.ioc.utils.AnnotationUtil;
import cn.settile.lzjyzq2.ioc.utils.BeanUtil;
import cn.settile.lzjyzq2.ioc.utils.PacketScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author lzjyz
 */
public class DefaultBeanRegister extends PacketScanner implements BeanRegister {

    private ApplicationContext context;
    private Map<Class<? extends Annotation>, AnnotationProcessor> processors;


    public DefaultBeanRegister(ApplicationContext context, Map<Class<? extends Annotation>, AnnotationProcessor> processors){
        this.context = context;
        this.processors = processors;
    }

    @Override
    public void dealClass(Class<?> clazz) {
        processors.forEach((c,v)->{
            Annotation annotation = AnnotationUtil.findAnnontation(clazz, c);
            if(annotation!=null){
                v.process(context,annotation,clazz,null,null,null);
            }
        });
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void start(String... packetName) {
        scanPacket(packetName);
    }
}
