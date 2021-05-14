package cn.settile.lzjyzq2.test;

import cn.settile.lzjyzq2.ioc.annotation.Autowired;
import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.annotation.Service;

/**
 * @author lzjyz
 */
@Service
public class TestComponent {

    @Autowired(value = "test")
    public TestAutoBean testAutoBean;

    public void printTest(){
        System.out.println("test");
    }

    public void printTestAutoBean(){
        testAutoBean.printTest();
    }

}
