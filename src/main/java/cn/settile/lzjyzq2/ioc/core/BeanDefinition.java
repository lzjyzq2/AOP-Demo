package cn.settile.lzjyzq2.ioc.core;

import cn.settile.lzjyzq2.ioc.annotation.Autowired;
import cn.settile.lzjyzq2.ioc.annotation.Component;
import cn.settile.lzjyzq2.ioc.utils.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021-05-13 15:26:54
 * @author 阳志强
 */
public class BeanDefinition {
    /**
     * bean标签的ID 作为bean的唯一标识
     */
    private String beanName;
    /**
     * bean的所属class
     */
    private String className;
    /**
     * bean的scope作用域
     */
    private Component.Scope scope = Component.Scope.singleton;
    private List<Property> propertyList = new ArrayList<>();
    public String getBeanName() {
        return beanName;
    }
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public Component.Scope getScope() {
        return scope;
    }
    public void setScope(Component.Scope scope) {
        this.scope = scope;
    }
    public List<Property> getPropertyList() {
        return propertyList;
    }
    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }


}
