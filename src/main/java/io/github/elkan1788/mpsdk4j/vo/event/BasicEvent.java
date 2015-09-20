package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * 事件消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class BasicEvent {

    /**
     * 微信公众号Id/OpenId
     */
    protected String toUserName;
    /**
     * OpenId/微信公众号Id
     */
    protected String fromUserName;
    /**
     * 消息创建时间 (整型)
     */
    protected int createTime;
    /**
     * 消息类型: event
     */
    protected String msgType;
    /**
     * 事件类型:subscribe(订阅),unsubscribe(取消订阅)...
     */
    protected String event;
    /**
     * 事件KEY值:扫码(二维码场景Id),菜单值...
     */
    protected String eventKey;

    public BasicEvent() {
        this.msgType = "event";
    }

    public BasicEvent(Map<String, String> values) {
        this.fromUserName = values.get("fromUserName");
        this.toUserName = values.get("toUserName");
        this.createTime = Integer.parseInt(values.get("createTime"));
        this.msgType = "event";
        this.event = values.get("event");
        this.eventKey = values.get("eventKey");
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

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
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

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
