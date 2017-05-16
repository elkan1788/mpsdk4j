package io.github.elkan1788.mpsdk4j.common;

/**
 * 事件消息类型
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public enum EventType {

    /**
     * 新事件
     */
    def,
    /**
     * 用户订阅事件
     */
    subscribe,
    /**
     * 退订事件
     */
    unsubscribe,
    /**
     * 扫描事件
     */
    SCAN,
    /**
     * 自动上传位置
     */
    LOCATION,
    /**
     * 点击事件
     */
    CLICK,
    /**
     * 跳转事件
     */
    VIEW,
    /**
     * 模板消息推送事件
     */
    TEMPLATESENDJOBFINISH,
    /**
     * 群发消息推送事件
     */
    MASSSENDJOBFINISH,

    // 以下事件微信iPhone5.4.1+, Android5.4+仅支持
    /**
     * 扫码推事件
     */
    scancode_push,
    /**
     * 扫码推事件且弹出“消息接收中”提示框
     */
    scancode_waitmsg,
    /**
     * 弹出系统拍照发图
     */
    pic_sysphoto,
    /**
     * 弹出拍照或者相册发图
     */
    pic_photo_or_album,
    /**
     * 弹出微信相册发图器
     */
    pic_weixin,
    /**
     * 弹出地理位置选择器
     */
    location_select,
    /**
     * 下发消息（除文本消息）
     */
    media_id,
    /**
     * 跳转图文消息URL
     */
    view_limited,
    /**
     * 创建客服会话
     */
    kf_create_session,
    /**
     * 关闭客服会话
     */
    kf_close_session,
    /**
     * 转接客服会话
     */
    kf_switch_session

}
