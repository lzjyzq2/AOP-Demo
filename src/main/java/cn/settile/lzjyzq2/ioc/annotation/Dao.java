package cn.settile.lzjyzq2.ioc.annotation;

import java.lang.annotation.*;

/**
 * @author lzjyz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Component
public @interface Dao {
}
