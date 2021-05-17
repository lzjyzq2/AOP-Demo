package cn.settile.lzjyzq2.aop.core.impl;

import cn.settile.lzjyzq2.aop.core.ProxyBeanFactory;
import cn.settile.lzjyzq2.aop.core.ProxyInvocationHandler;
import cn.settile.lzjyzq2.ioc.ApplicationContext;
import cn.settile.lzjyzq2.ioc.annotation.Autowired;
import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.annotation.Require;
import cn.settile.lzjyzq2.ioc.core.BeanDefinition;
import cn.settile.lzjyzq2.ioc.listener.RegisterBeanListener;

import java.lang.reflect.Proxy;

/**
 * @author lzjyz
 */
public class DefaultProxyBeanFactory implements ProxyBeanFactory,RegisterBeanListener {


    @Autowired
    ApplicationContext applicationContext;

    private final static String TAG = "DefaultProxyBeanFactory";

    @Override
    public void init() {

    }

    @Override
    public Object getProxyInstance(Object bean) {
        ProxyInvocationHandler handler = new ProxyInvocationHandler();
        handler.setObject(bean);

        return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), handler);
    }

    @Override
    public void updateBean(String name, Object bean) {

    }

    @Override
    public void updateBeanFactory(ApplicationContext applicationContext) {

    }

    @Override
    public void onCreateBean(BeanDefinition beanDefinition, Object bean) {

    }

    @Override
    public void onRefreshBean(BeanDefinition beanDefinition, Object bean, String tag) {
        if(!tag.equals(TAG)){
            updateBean(beanDefinition.getBeanName(), bean);
        }
    }
}
