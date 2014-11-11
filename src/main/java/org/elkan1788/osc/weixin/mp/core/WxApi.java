package org.elkan1788.osc.weixin.mp.core;

import org.elkan1788.osc.weixin.mp.exception.WxRespException;
import org.elkan1788.osc.weixin.mp.vo.*;

import java.io.File;
import java.util.List;

/**
 * 微信API接口设计
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/10
 * @version 1.0.0
 */
public interface WxApi {

    public static final String TEXT = "text";
    public static final String IMAGE = "image";
    public static final String VOICE = "voice";
    public static final String VIDEO = "video";
    public static final String MUSIC = "music";
    public static final String NEWS = "news";

	/**
	 * 创建ACCESS_TOKEN
	 */
	public String getAccessToken() throws WxRespException;

    /**
     * 刷新ACCESS_TOKE
     */
	public void refreshAccessToken() throws WxRespException;


    /**
     * 上传多媒体文件，微信服务器只保存3天
     *
     * @param mediaType 媒体文件类型[image, voice, video]
     * @param file      文件
     * @return  储存媒体ID
     * @throws org.elkan1788.osc.weixin.mp.exception.WxRespException
     */
    public String upMedia(String mediaType, File file) throws WxRespException;

    /**
     * 下载多媒体文件
     *
     * @param mediaId   多媒体ID
     * @param file      本地存储位置[默认使用媒体ID作文件名]
     * @throws org.elkan1788.osc.weixin.mp.exception.WxRespException
     */
    public void dlMedia(String mediaId, File file) throws WxRespException;

    /**
     * 获取微信菜单
     * @return  微信菜单
     */
	public List<Menu> getMenu() throws WxRespException;

    /**
     * 创建微信菜单
     * @param menus 微信菜单
     * @return  true或false
     */
	public boolean createMenu(Menu... menus) throws WxRespException;

	/**
	 * 删除自定义菜单
	 */
	public boolean deleteMenu() throws WxRespException;

	/**
	 * 创建分组，成功返回分组ID，否则抛出异常
	 */
	public int creatGroup(String name) throws WxRespException;

	/**
	 * 获取所有分组
	 */
	public List<Group> getGroups() throws WxRespException;

	/**
	 * 重命名分组
	 */
	public boolean renGroup(int id, String name) throws WxRespException;

	/**
	 * 获取用户分组ID
	 */
	public int getGroupId(String openId) throws WxRespException;

	/**
	 * 移动用户分组
	 */
	public boolean move2Group(String openId, int groupId) throws WxRespException;

	/**
	 * 获取关注用户列表
	 */
	public FollowList getFollowerList(String nextOpenId) throws WxRespException;

	/**
	 * 获取关注者的信息
	 */
	public Follower getFollower(String openId, String lang) throws WxRespException;

    /**
     * 发送客服消息
     *
     * @param msg       消息
     * @return  false或true
     * @throws org.elkan1788.osc.weixin.mp.exception.WxRespException
     */
	public boolean sendCustomerMsg(OutPutMsg msg) throws WxRespException;

    /**
     * 发送模板消息
     *
     * @param openId        接收者ID
     * @param templateId    模板ID
     * @param topColor      顶部颜色
     * @param url           跳转地址
     * @param templates     模样数据
     * @return
     * @throws org.elkan1788.osc.weixin.mp.exception.WxRespException
     */
	public boolean sendTemplateMsg(String openId, String templateId, String topColor, String url, Template... templates) throws WxRespException;


}
