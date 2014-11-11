package org.elkan1788.osc.weixin.mp.vo;

/**
 * 微信消息实体基类
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @since 2014/11/7
 * @version 1.0.0
 */
public abstract class BaseMsg {

	/**
	 * 消息唯一ID(64位整型)
	 */
	protected long msgId;

	/**
	 * 消息创建时间 （整型） 
	 */
	protected long createTime;

	/**
	 * 消息类型(text, image, video, voice, location, link,event)
	 */
	protected String msgType;

	/**
	 * 消息事件:
	 * subscribe:订阅
	 * unsubscribe:取消订阅
	 * SCAN:关注后场景扫描
	 * LOCATION:主动上传位置
	 * VIEW,CLICK:菜单点击事件
	 * TEMPLATESENDJOBFINISH:模板消息推送
	 */
	protected String event;

	/**
	 * 接收消息用户ID
	 */
	protected String toUserName;

	/**
	 * 消息来自用户ID
	 */
	protected String fromUserName;

	/**
	 * 文本消息内容
	 */
	protected String content;

	/**
	 * 多媒体消息ID(微信服务器有效时间为3天)
	 */
	protected String mediaId;

	/**
	 * 链接,文章消息标题
	 */
	protected String title;

	/**
	 * 详细描述
	 */
	protected String description;

	/**
	 * 视频消息缩略图的媒体id
	 */
	protected String thumbMediaId;
    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
