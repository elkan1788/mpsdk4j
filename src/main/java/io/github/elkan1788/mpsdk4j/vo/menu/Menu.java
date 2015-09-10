/**
 * @author senhui.li
 */
package io.github.elkan1788.mpsdk4j.vo.menu;

import io.github.elkan1788.mpsdk4j.common.EventType;

import java.util.List;

import org.nutz.json.JsonField;

/**
 * 自定义菜单
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class Menu {

    /**
     * 菜单标题，不超过16个字节，子菜单不超过40个字节
     */
    private String name;

    /**
     * 菜单的响应动作类型
     * <p/>
     * click: 点击推事件
     * <p/>
     * view: 跳转URL
     * <p/>
     * scancode_push: 扫码推事件
     * <p/>
     * scancode_waitmsg: 扫码推事件
     * <p/>
     * pic_sysphoto: 弹出系统拍照发图
     * <p/>
     * pic_photo_or_album: 弹出拍照或者相册发
     * <p/>
     * pic_weixin: 弹出微信相册发图器
     * <p/>
     * location_select: 弹出地理位置选择器
     * <p/>
     * media_id: 下发消息(除文本消息)
     * <p/>
     * view_limited: 跳转图文消息URL
     * <p/>
     */
    private String type;

    /**
     * 点击类型菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String key;

    /**
     * 网页链接，用户点击菜单可打开链接，不超过256字节
     */
    private String url;

    /**
     * 二级菜单
     */
    @JsonField(value = "sub_button")
    private List<Menu> subButtons;

    /**
     * 构造函数
     */
    public Menu() {}

    /**
     * 一级菜单构造函数
     * 
     * @param name
     *            菜单名字
     */
    public Menu(String name) {
        this.name = name;
    }

    /**
     * 二级菜单构造函数
     *
     * @param name
     *            菜单名称
     * @param type
     *            菜单类型
     * @param val
     *            KEY值/URL
     */
    public Menu(String name, String type, String val) {
        this.name = name;
        this.type = type;
        if (EventType.VIEW.name().equals(type)) {
            this.url = val;
        }
        else {
            this.key = val;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getSubButtons() {
        return subButtons;
    }

    public void setSubButtons(List<Menu> subButtons) {
        this.subButtons = subButtons;
    }

    @Override
    public String toString() {
        return "Menu [name="
               + name
               + ", type="
               + type
               + ", key="
               + key
               + ", url="
               + url
               + ", subButtons="
               + subButtons
               + "]";
    }

}
