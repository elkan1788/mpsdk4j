package io.github.elkan1788.mpsdk4j.vo.message;

import java.util.Map;

import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;

/**
 * 视频消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class VideoMsg extends BasicMsg {

    /**
     * 视频消息媒体id,可以调用多媒体文件下载接口拉取数据
     */
    private String mediaId;

    /**
     * 视频消息缩略图的媒体id,可以调用多媒体文件下载接口拉取数据
     */
    private String thumbMediaId;

    /**
     * 视频消息的标题
     */
    private String title;

    /**
     * 视频消息的描述
     */
    private String description;

    public VideoMsg() {
        super();
        this.msgType = "video";
    }

    public VideoMsg(BasicEvent event) {
        super(event);
        this.msgType = "video";
    }

    public VideoMsg(BasicMsg msg) {
        super(msg);
        this.msgType = "video";
    }

    public VideoMsg(Map<String, String> values) {
        super(values);
        this.mediaId = values.get("mediaId");
        this.thumbMediaId = values.get("thumbMediaId");
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
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

    @Override
    public String toString() {
        return "VideoMsg [toUserName="
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
               + ", thumbMediaId="
               + thumbMediaId
               + ", title="
               + title
               + ", description="
               + description
               + "]";
    }

}
