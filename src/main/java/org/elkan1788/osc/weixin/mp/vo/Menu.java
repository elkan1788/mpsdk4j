package org.elkan1788.osc.weixin.mp.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 微信自定义菜单实体
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/7
 * @version 1.0.0
 */
public class Menu {

    public static final String CLICK = "click";
    public static final String VIEW = "view";
    public static final String SCANCODE_PUSH = "scancode_push";
    public static final String SCANCODE_WAITMSG = "scancode_waitmsg";
    public static final String PIC_SYSPHOTO = "pic_sysphoto";
    public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
    public static final String PIC_WEIXIN = "pic_weixin";
    public static final String LOCATION_SELECT = "location_select";

	/**
	 * 菜单标题，不超过16个字节，子菜单不超过40个字节
	 */
	private String name;

	/**
	 * 菜单的响应动作类型
	 * click:点击推事件
	 * view:跳转URL
	 * scancode_push：扫码推事件
	 * scancode_waitmsg：扫码推事件
	 * pic_sysphoto：弹出系统拍照发图
	 * pic_photo_or_album：弹出拍照或者相册发
	 * pic_weixin：弹出微信相册发图器
	 * location_select：弹出地理位置选择器
	 * 
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
    @JSONField(name = "sub_button")
	private List<Menu> subButtons;

    public Menu() {
    }

    public Menu(String name) {
        this.name = name;
    }

    /**
     * 构造函数
     *
     * @param name  菜单名称
     * @param type  菜单类型
     * @param val   KEY值/URL
     */
    public Menu(String name, String type, String val) {
        this.name = name;
        this.type = type;
        if (VIEW.equals(type)) {
            this.url = val;
        } else {
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
        return "Menu{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", key='" + key + '\'' +
                ", url='" + url + '\'' +
                ", subButtons=" + subButtons +
                '}';
    }
}
