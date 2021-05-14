package cn.settile.lzjyzq2.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author lzjyz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Component
public @interface Service{
    String name() default "";
    Component.Scope scope() default Component.Scope.singleton;
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
