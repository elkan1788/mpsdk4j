/**
 * @author senhui.li
 */
package io.github.elkan1788.mpsdk4j.vo.message;

import java.util.Map;

import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;

/**
 * 文本消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class TextMsg extends BasicMsg {

    /**
     * 文本内容
     */
    private String content;

    public TextMsg() {
        super();
        this.msgType = "text";
    }

    public TextMsg(BasicEvent event) {
        super(event);
        this.msgType = "text";
    }

    public TextMsg(BasicMsg msg) {
        super(msg);
        this.msgType = "text";
    }

    public TextMsg(Map<String, String> values) {
        super(values);
        this.content = values.get("content");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextMsg [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", content="
               + content
               + ", msgId="
               + msgId
               + "]";
    }

}
