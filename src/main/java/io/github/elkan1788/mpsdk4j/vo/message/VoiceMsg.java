package io.github.elkan1788.mpsdk4j.vo.message;

import java.util.Map;

import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;

/**
 * 音频消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class VoiceMsg extends BasicMsg {

    /**
     * 语音消息媒体id,可以调用多媒体文件下载接口拉取数据
     */
    private String mediaId;
    /**
     * 语音格式,如amr,speex等
     */
    private String format;
    /**
     * 语音识别结果,UTF8编码
     */
    private String recognition;

    public VoiceMsg() {
        super();
        this.msgType = "voice";
    }

    public VoiceMsg(BasicEvent event) {
        super(event);
        this.msgType = "voice";
    }

    public VoiceMsg(BasicMsg msg) {
        super(msg);
        this.msgType = "voice";
    }

    public VoiceMsg(Map<String, String> values) {
        super(values);
        this.mediaId = values.get("mediaId");
        this.format = values.get("format");
        this.recognition = values.get("recognition");
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    @Override
    public String toString() {
        return "VoiceMsg [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", msgId="
               + msgId
               + ", mediaId="
               + mediaId
               + ", format="
               + format
               + ", recognition="
               + recognition
               + "]";
    }
}
