package cn.settile.lzjyzq2.ioc.annotation;

import java.lang.annotation.*;

/**
 * @date 2021-05-13 16:29:54
 * @author lzjyzq2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface Component {
    String name() default "";
    Scope scope() default Scope.singleton;
    /**
     * 生成作用域
     */
    public enum Scope{
        /**
         *  在整个Application中单例存在
         */
        singleton,
        /**
         *  每次获取Bean时创建
         */
        prototype
    }
}


