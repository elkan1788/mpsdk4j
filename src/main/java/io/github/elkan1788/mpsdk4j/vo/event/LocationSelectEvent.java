package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * 用户主动发送地理位置事件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class LocationSelectEvent extends MenuEvent {

    /**
     * 发送的位置信息
     */
    private SendLocationInfo sendLocationInfo;

    public LocationSelectEvent() {
        super();
    }

    public LocationSelectEvent(Map<String, String> values) {
        super(values);
    }

    public SendLocationInfo getSendLocationInfo() {
        return sendLocationInfo;
    }

    public void setSendLocationInfo(SendLocationInfo sendLocationInfo) {
        this.sendLocationInfo = sendLocationInfo;
    }

    @ Override
    public String toString() {
        return "LocationSelectEvent [toUserName="
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
               + ", sendLocationInfo="
               + sendLocationInfo
               + "]";
    }

}
