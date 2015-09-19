package io.github.elkan1788.mpsdk4j.vo.push;

import java.util.Map;

import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;

/**
 * 模板消息处理事件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class SentTmlJobEvent extends BasicEvent {

    /**
     * 消息id
     */
    private String msgId;
    /**
     * 发送状态: success,failed:user block,failed: system failed
     */
    private String status;

    public SentTmlJobEvent() {
        super();
    }

    public SentTmlJobEvent(Map<String, String> values) {
        super(values);
        this.msgId = values.get("msgId");
        this.status = values.get("status");
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ Override
    public String toString() {
        return "TemplateJobEvent [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", event="
               + event
               + ", eventKey="
               + eventKey
               + ", msgId="
               + msgId
               + ", status="
               + status
               + "]";
    }
}
