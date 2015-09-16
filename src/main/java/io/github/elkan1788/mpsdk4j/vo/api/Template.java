package io.github.elkan1788.mpsdk4j.vo.api;

/**
 * 微信模板消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class Template {

    /**
     * 模板字段名称
     */
    private String name;
    /**
     * 显示颜色
     */
    private String color;
    /**
     * 显示数据
     */
    private String value;

    public Template() {

    }

    /**
     * 默认颜色(蓝色)
     * 
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public Template(String name, String value) {
        this.name = name;
        this.color = "#119EF3";
        this.value = value;
    }

    /**
     * 带参数构造方法
     * 
     * @param name
     *            参数名称
     * @param color
     *            字体颜色
     * @param value
     *            参数值
     */
    public Template(String name, String color, String value) {
        this.name = name;
        this.color = color;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 组装模板数据
     */
    public String templateData() {
        StringBuffer data = new StringBuffer("\"" + name + "\":{");
        data.append("\"value\":\"").append(value).append("\",");
        data.append("\"color\":\"").append(color).append("\"}");
        return data.toString();
    }

}
