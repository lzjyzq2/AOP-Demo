package cn.settile.lzjyzq2.test;


import cn.settile.lzjyzq2.ioc.annotation.Component;

@Component(name = "test")
public class TestAutoBean {

    public void printTest(){
        System.out.println("run in TestAutoBean");
    }


}
