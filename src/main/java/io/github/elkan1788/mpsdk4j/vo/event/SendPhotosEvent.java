package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.List;
import java.util.Map;

import org.nutz.json.Json;

/**
 * 用户拍照/相册发图事件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class SendPhotosEvent extends MenuEvent {

    /**
     * 发送的图片信息
     */
    private SendPicsInfo sendPicsInfo;

    public SendPhotosEvent() {

    }

    public SendPhotosEvent(Map<String, String> values) {
        super(values);
        List<PicItem> items = Json.fromJsonAsList(PicItem.class, values.get("picList"));
        this.sendPicsInfo = new SendPicsInfo(Integer.parseInt(values.get("count")), items);
    }

    public SendPicsInfo getSendPicsInfo() {
        return sendPicsInfo;
    }

    public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
        this.sendPicsInfo = sendPicsInfo;
    }

    @Override
    public String toString() {
        return "ScanSysPhotoEvent [toUserName="
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
               + ", sendPicsInfo="
               + sendPicsInfo
               + "]";
    }

}
