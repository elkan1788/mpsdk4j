package org.elkan1788.osc.weixin.mp.commons;

/**
 * 微信消息的类型
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @version 1.0
 * @since 2014/11/6
 */
public enum MsgType {

    /**
     * 文本消息
     */
    text,
    /**
     * 图像消息
     */
    image,
    /**
     * 语音消息
     */
    voice,
    /**
     * 视频消息
     */
    video,
    /**
     * 地理位置消息
     */
    location,
    /**
     * 链接消息
     */
    link,
    /**
     * 音乐消息
     */
    music,
    /**
     * 多图文消息
     */
    news,
    /**
     * 多客服消息
     */
    transfer_customer_service,
    /**
     * 订阅事件
     */
    e_subscribe,
    /**
     * 退订事件
     */
    e_unsubscribe,
    /**
     * 扫描事件
     */
    e_SCAN,
    /**
     * 点击事件
     */
    e_CLICK,
    /**
     * 跳转事件
     */
    e_VIEW,
    /**
     * 模板消息推送事件
     */
    e_TEMPLATESENDJOBFINISH,

    // 以下事件微信iPhone5.4.1+, Android5.4+仅支持
    /**
     * 扫码推事件
     */
    e_scancode_push,
    /**
     * 扫码推事件且弹出“消息接收中”提示框
     */
    e_scancode_waitmsg,
    /**
     * 弹出系统拍照发图
     */
    e_pic_sysphoto,
    /**
     * 弹出拍照或者相册发图
     */
    e_pic_photo_or_album,
    /**
     * 弹出微信相册发图器
     */
    e_pic_weixin,
    /**
     * 弹出地理位置选择器
     */
    e_location_select,
    /**
     * 群发消息中的图文消息
     */
    mpnews,
    /**
     * 群发消息中的视频消息
     */
    mpvideo;

    /**
     * 事件类型标识
     */
    public static final String EVENT_PREFIX = "e_";
}
