package cn.settile.lzjyzq2.ioc;

import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.core.BeanDefinition;
import cn.settile.lzjyzq2.ioc.core.BeanRegister;
import cn.settile.lzjyzq2.ioc.factory.impl.DefaultListableBeanFactory;
import cn.settile.lzjyzq2.ioc.listener.ApplicationInitListener;
import cn.settile.lzjyzq2.ioc.listener.RegisterBeanListener;
import cn.settile.lzjyzq2.ioc.utils.BeanUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lzjyz
 */
public class ApplicationContext extends DefaultListableBeanFactory {

    private List<ApplicationInitListener> applicationInitListeners;
    private List<RegisterBeanListener> registerBeanListeners;

    public ApplicationContext(){
        applicationInitListeners = new LinkedList<>();
        registerBeanListeners = new LinkedList<>();
        init();
    }

    /**
     * 进入初始化
     */
    private void init(){
        Iterator<ApplicationInitListener> applicationInitListenerIterator = applicationInitListeners.iterator();
        while (applicationInitListenerIterator.hasNext()){
            applicationInitListenerIterator.next().init(this);
        }
    }

    public void addRegisterBeanListener(RegisterBeanListener registerBeanListener){
        registerBeanListeners.add(registerBeanListener);
    }

    public void removeRegisterBeanListener(RegisterBeanListener registerBeanListener){
        registerBeanListeners.remove(registerBeanListener);
    }

    private void notifyRegisterBean(BeanDefinition beanDefinition,Object bean){
        Iterator<RegisterBeanListener> registerBeanListenerIterator = registerBeanListeners.iterator();
        while (registerBeanListenerIterator.hasNext()){
            registerBeanListenerIterator.next().onCreateBean(beanDefinition,bean);
        }
    }

    public void addApplicationListener(ApplicationInitListener applicationInitListener){
        applicationInitListeners.add(applicationInitListener);
    }

    public void removeApplicationListener(ApplicationInitListener applicationInitListener){
        applicationInitListeners.remove(applicationInitListener);
    }

    /**
     *
     * @param object
     */
    public void createBean(Object object){
        createBean(BeanUtil.getLowerFirstCaseName(object.getClass()),object);
    }

    public void createBean(String name,Object bean){
        getSingletonObjects().put(name,bean);
    }
}
