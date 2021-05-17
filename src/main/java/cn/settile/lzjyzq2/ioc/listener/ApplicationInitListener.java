package cn.settile.lzjyzq2.ioc.listener;

import cn.settile.lzjyzq2.ioc.ApplicationContext;

/**
 * @author lzjyz
 */
public interface ApplicationInitListener {
    /**
     * 初始化
     */
    void init(ApplicationContext applicationContext);
}
