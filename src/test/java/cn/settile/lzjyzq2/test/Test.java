package cn.settile.lzjyzq2.test;

import cn.settile.lzjyzq2.ioc.annotation.Application;
import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.annotation.LoadForm;
import cn.settile.lzjyzq2.ioc.core.BeanDefinition;
import cn.settile.lzjyzq2.ioc.impl.DefaultListableBeanFactory;
import cn.settile.lzjyzq2.ioc.utils.AnnotationUtil;
import cn.settile.lzjyzq2.ioc.utils.BeanUtil;
import cn.settile.lzjyzq2.ioc.utils.PacketScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

/**
 * @author lzjyz
 */
@Application
@LoadForm(packet = "cn.settile.lzjyzq2.test")
public class Test {

    public static void main(String[] args) {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        PacketScanner packetScanner = new PacketScanner() {
            @Override
            public void dealClass(Class<?> clazz) {
                Annotation annotation = AnnotationUtil.findAnnontation(clazz,Component.class);
                if(annotation!=null){
                    Component component = (Component) annotation;
                    if(clazz.isMemberClass()){
                        if (!Modifier.isStatic(clazz.getModifiers())){
                            throw new RuntimeException("类"+clazz.getName()+"不是静态类");
                        }
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
                    factory.registerBeanDefinition(beanDefinition);
                    System.out.println(clazz.getName());
                }
            }
        };
        packetScanner.scanPacket("cn.settile.lzjyzq2.test");
        TestComponent testComponent = (TestComponent) factory.getBean("testComponent");
        testComponent.printTest();
        testComponent.printTestAutoBean();
    }
}
