package io.github.elkan1788.mpsdk4j.vo.message;

import java.util.Map;

/**
 * 图像消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class ImageMsg extends BasicMsg {

    /**
     * 图片链接
     */
    private String picUrl;
    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    private String mediaId;

    public ImageMsg() {
        super();
        this.msgType = "image";
    }

    public ImageMsg(Map<String, String> values) {
        super(values);
        this.picUrl = values.get("picUrl");
        this.mediaId = values.get("mediaId");
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "ImageMsg [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", msgId="
               + msgId
               + ", picUrl="
               + picUrl
               + ", mediaId="
               + mediaId
               + "]";
    }

}
