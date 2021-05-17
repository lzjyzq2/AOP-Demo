package cn.settile.lzjyzq2.test;


import cn.settile.lzjyzq2.aop.core.impl.DefaultProxyBeanFactory;
import cn.settile.lzjyzq2.ioc.ApplicationContext;
import cn.settile.lzjyzq2.ioc.ApplicationLoader;
import cn.settile.lzjyzq2.ioc.annotation.Application;
import cn.settile.lzjyzq2.ioc.utils.BeanUtil;

import java.io.IOException;

public class TestApplication {

    public static void main(String[] args) {
        //ApplicationLoader.load(TestApplication.class);
        ApplicationLoader.load("cn.settile.lzjyzq2.test");
        ApplicationContext application = ApplicationLoader.getApplicationContext();

        TestComponent testComponent = (TestComponent) application.getBean("testComponent");
        testComponent.printTest();
        testComponent.printTestAutoBean();

        System.out.println(BeanUtil.getBeanName(DefaultProxyBeanFactory.class));
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
