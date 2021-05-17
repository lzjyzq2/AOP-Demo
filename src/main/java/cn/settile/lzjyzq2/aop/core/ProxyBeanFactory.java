package cn.settile.lzjyzq2.aop.core;

import cn.settile.lzjyzq2.ioc.ApplicationContext;
import cn.settile.lzjyzq2.ioc.annotation.Component;

/**
 * @author lzjyzq2
 */
@Component
public interface ProxyBeanFactory {

    void init();

    /**
     *
     * @param bean
     * @return
     */
     Object getProxyInstance(Object bean);

    /**
     *
     * @param name
     * @param bean
     */
     void updateBean(String name,Object bean);

     void updateBeanFactory(ApplicationContext applicationContext);
}
