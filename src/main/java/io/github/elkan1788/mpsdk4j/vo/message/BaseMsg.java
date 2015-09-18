package io.github.elkan1788.mpsdk4j.vo.message;

import java.util.Map;

/**
 * 消息基础类
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class BaseMsg {

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
     * 消息类型: text, image, voice ...
     */
    protected String msgType;
    /**
     * 消息Id, 64位整型
     */
    protected long msgId;

    /**
     * 默认构造方法
     */
    public BaseMsg() {
        this.createTime = Long.valueOf(System.currentTimeMillis() / 1000).intValue();
    }

    /**
     * 带XML解析值构造方法
     * 
     * @param values
     *            XML值
     */
    public BaseMsg(Map<String, String> values) {
        this.fromUserName = values.get("fromUserName");
        this.toUserName = values.get("toUserName");
        this.createTime = Integer.parseInt(values.get("createTime"));
        this.msgType = values.get("msgType");
        this.msgId = Long.parseLong(values.get("msgId"));
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

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

}
