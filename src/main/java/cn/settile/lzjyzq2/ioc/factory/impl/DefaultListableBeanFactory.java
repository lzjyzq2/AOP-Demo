package cn.settile.lzjyzq2.ioc.factory.impl;

import cn.settile.lzjyzq2.ioc.annotation.Autowired;
import cn.settile.lzjyzq2.ioc.factory.BeanFactory;
import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.core.BeanDefinition;
import cn.settile.lzjyzq2.ioc.core.Property;
import cn.settile.lzjyzq2.ioc.utils.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认Bean工厂化方法
 *
 * @author 阳志强
 * @date 2021-05-13 15:26:01 阳志强
 */
public class DefaultListableBeanFactory implements BeanFactory {

    /**
     * bean的单例模式
     */
    private final static Component.Scope SINGLETON = Component.Scope.singleton;

    /**
     * 提供 beanDefinitionMap 存储bean的定义
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    /**
     * 提供 singletonObjects 存储bean的对象实例 (scope为singleton的)
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String beanName) {
        //        1. 先从单例的map集合中获取 是否有指定beanName的对象
        Object singletonObj = singletonObjects.get(beanName);
//                有直接返回
        if (singletonObj != null) {
            return singletonObj;
        }
//                没有下一步
//        2. 从注册集合中获取bean的定义对象
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
//                有下一步
//        没有抛异常NoSuchBeanDefinition
        if (beanDefinition == null) {
            throw new RuntimeException("NoSuchBeanDefinition : 你找的 " + beanName + " 对象 不存在");
        }
//        3. 判断bean的scope作用域
        Component.Scope scope = beanDefinition.getScope();
//                singleton单例
        if (SINGLETON.equals(scope)) {
//        createBean对象
            Object obj = createBean(beanDefinition);
//        存入单例集合
            singletonObjects.put(beanName, obj);
//        返回对象
            return obj;
        } else {
//        prototype多例
//        createBean对象
            return createBean(beanDefinition);
//        返回对象
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("类未找到" + e.getMessage());
        }
        // 创建对象:
        Object obj = null;
        try {

            obj = createInstance(aClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("创建对象失败" + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("非法访问" + e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 依赖注入
        List<Property> propertyList = beanDefinition.getPropertyList();
        for (Property property : propertyList) {
            String name = property.getName();
            String value = property.getValue();
            String ref = property.getRef();
            // 属性名不为空 进行注入
            if (name != null && !"".equals(name)) {
                // 如果配置的是value值 直接注入
                if (value != null && !"".equals(value)) {
                    Map<String, String> params = new HashMap<>();
                    params.put(name, value);
                    try {
                        BeanUtils.populate(obj, params);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException("非法访问" + e.getMessage());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException("调用目标对象失败" + e.getMessage());
                    }
                }
                // 如果配置的是ref需要获取其它对象注入
                if (ref != null && !"".equals(ref)) {
                    try {
                        //Object autoBean = getBean(ref);
                        //BeanUtils.setProperty(obj,name,autoBean);
                        BeanUtil.setProperty(obj, name, getBean(ref));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException("非法访问" + e.getMessage());
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }

    public void preInstaniceSingletons() {
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Component.Scope scope = beanDefinition.getScope();
            // 判断单例  非抽象   不懒加载
            if (SINGLETON.equals(scope)) {
                this.getBean(beanName);
            }
        });
    }

    /**
     * 将bean注册到容器中
     *
     * @param beanDefinition Bean描述对象
     */
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanDefinition.getBeanName(), beanDefinition);
    }

    public void updateBean(BeanDefinition beanDefinition, Object bean) {
        beanDefinitionMap.put(beanDefinition.getBeanName(), beanDefinition);
        singletonObjects.put(beanDefinition.getBeanName(), bean);
    }

    protected Map<String, Object> getSingletonObjects() {
        return singletonObjects;
    }


    private Object createInstance(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<?> target = null;
        if (constructors.length > 1) {
            for (Constructor<?> constructor : constructors) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    target = constructor;
                    break;
                }
            }
        } else {
            target = constructors[0];
        }
        assert target != null;
        if (target.getParameterCount() > 0) {
            Class<?>[] paramsTypes = target.getParameterTypes();
            Object[] params = new Object[target.getParameterCount()];
            for (int i = 0; i < target.getParameterCount(); i++) {
                String paramName = BeanUtil.getLowerFirstCaseName(paramsTypes[i]);
                params[i] = getBean(paramName);
            }
            return target.newInstance(params);
        } else {
            return target.newInstance();
        }
        
    }
}
