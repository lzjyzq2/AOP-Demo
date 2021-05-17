package cn.settile.lzjyzq2.ioc.factory;

/**
 * @author 阳志强
 * @date 2021-05-13 15:21:53 阳志强
 */
public interface BeanFactory {

    /**
     * 获取指定名称的Bean
     * @date 2021-05-13 15:23:09 阳志强
     * @param beanName 获取的Bean名称
     * @return 获取到的bean
     */
    Object getBean(String beanName);
}
