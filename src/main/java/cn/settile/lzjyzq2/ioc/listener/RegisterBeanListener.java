package cn.settile.lzjyzq2.ioc.listener;

import cn.settile.lzjyzq2.ioc.core.BeanDefinition;

/**
 * @author lzjyz
 */
public interface RegisterBeanListener {

    /**
     * 当创建Bean时触发
     * @param beanDefinition 创建Bean时的描述对象
     * @param bean 被创建的Bean对象
     */
    void onCreateBean(BeanDefinition beanDefinition,Object bean);

    /**
     * 当创建Bean时触发
     * @param beanDefinition beanDefinition 创建Bean时的描述对象
     * @param bean 被创建的Bean对象
     * @param tag 刷新Bean时的Tag
     */
    void onRefreshBean(BeanDefinition beanDefinition,Object bean,String tag);
}
