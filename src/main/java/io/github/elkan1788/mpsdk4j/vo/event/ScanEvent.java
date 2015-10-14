package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * 扫码事件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class ScanEvent extends BasicEvent {

    /**
     * 二维码的ticket,可用来换取二维码图片
     */
    private String ticket;

    public ScanEvent() {
        super();
    }

    public ScanEvent(Map<String, String> values) {
        super(values);
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @ Override
    public String toString() {
        return "ScanEvent [toUserName="
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
               + ", ticket="
               + ticket
               + "]";
    }

}
