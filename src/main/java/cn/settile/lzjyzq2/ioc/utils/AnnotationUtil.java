package cn.settile.lzjyzq2.ioc.utils;

import javax.annotation.*;
import java.lang.annotation.*;

/**
 * @date 2021-05-14 10:16:45
 * @author lzjyz
 */
public class AnnotationUtil {
    public static Annotation findAnnontation(Class<?> target,Class<? extends Annotation> clazz){
        Annotation[] annotations = target.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() != Deprecated.class &&
                    annotation.annotationType() != SuppressWarnings.class &&
                    annotation.annotationType() != Override.class &&
                    annotation.annotationType() != PostConstruct.class &&
                    annotation.annotationType() != PreDestroy.class &&
                    annotation.annotationType() != Resource.class &&
                    annotation.annotationType() != Resources.class &&
                    annotation.annotationType() != Generated.class &&
                    annotation.annotationType() != Target.class &&
                    annotation.annotationType() != Retention.class &&
                    annotation.annotationType() != Documented.class &&
                    annotation.annotationType() != Inherited.class
            ) {
                if (annotation.annotationType() == clazz){
                    return annotation;
                }else{
                    return findAnnontation(annotation.annotationType(),clazz);
                }
            }
        }
        return null;
    }
}
