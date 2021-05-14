package cn.settile.lzjyzq2.ioc.core;

/**
 * @date 2021-05-13 15:27:47
 * @author lzjyz
 */
public class Property {
    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性的值
     */
    private String value;
    /**
     * 属性的引用
     */
    private String ref;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
}
