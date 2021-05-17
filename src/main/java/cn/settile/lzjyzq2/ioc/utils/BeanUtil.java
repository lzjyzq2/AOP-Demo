package cn.settile.lzjyzq2.ioc.utils;

import cn.settile.lzjyzq2.ioc.annotation.Autowired;
import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.core.Property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lzjyz
 */
public class BeanUtil {
    public static boolean isBean(Class<?> clazz) {
        boolean flag = true;
        Annotation component = AnnotationUtil.findAnnontation(clazz,Component.class);
        if (component != null) {
            if(clazz.isMemberClass()){
                if (!Modifier.isStatic(clazz.getModifiers())) {
                    flag = false;
                }
            }
            if (Modifier.isInterface(clazz.getModifiers())) {
                flag = false;
            }
            if (Modifier.isAbstract(clazz.getModifiers())) {
                flag = false;
            }
            if (clazz.isAnonymousClass()) {
                flag = false;
            }
        }
        return flag;
    }

    public static List<Property> scanPropertyList(Class<?> clazz){
        List<Property> propertyList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if(declaredField.isAnnotationPresent(Autowired.class)){
                Autowired autowired = declaredField.getAnnotation(Autowired.class);
                Property property = new Property();
                property.setName(declaredField.getName());
                if (BeanUtil.isBean(declaredField.getType())){
                    if("".equals(autowired.value().trim())){
                        property.setRef(getLowerFirstCaseName(declaredField.getType()));
                    }else {
                        property.setRef(autowired.value());
                    }
                }else {
                    throw new RuntimeException("自动注入的Bean:"+declaredField.getName()+"不是一个Component");
                }
                propertyList.add(property);
            }
        }
        return propertyList;
    }

    public static String getLowerFirstCaseName(Class<?> clazz){
        String clazzName = clazz.getSimpleName();
        if(Character.isLowerCase(clazzName.charAt(0))) {
            return clazzName;
        } else {
            return Character.toLowerCase(clazzName.charAt(0)) + clazzName.substring(1);
        }
    }


    public static void setProperty(Object target,String name,Object property) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(name);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(target,property);
        field.setAccessible(accessible);
    }


    public static String getBeanName(Class<?> clazz){
        Annotation annotation = AnnotationUtil.findAnnontation(clazz,Component.class);
        assert annotation != null;
        if(clazz.isAnnotationPresent(annotation.getClass())){
            return getLowerFirstCaseName(clazz);
        }else {
            for (Class<?> anInterface : clazz.getInterfaces()) {
                return getBeanName(clazz);
            }
        }
        return null;
    }
}
