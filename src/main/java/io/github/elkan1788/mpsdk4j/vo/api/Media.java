package io.github.elkan1788.mpsdk4j.vo.api;

import org.nutz.json.JsonField;

/**
 * 多媒体文件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class Media {

    /**
     * 媒体文件类型,分别有
     * 
     * <pre/>
     * 图片(image)
     * <p/>
     * 语音(voice)
     * <p/>
     * 视频(video)和
     * <p/>
     * 缩略图(thumb,主要用于视频与音乐格式的缩略图)
     */
    private String type;

    /**
     * 媒体文件上传后，获取时的唯一标识
     */
    @JsonField(value = "media_id")
    private String mediaId;

    /**
     * 媒体文件上传时间戳
     */
    @JsonField(value = "created_at")
    private long createdAt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Media [type=" + type + ", mediaId=" + mediaId + ", createdAt=" + createdAt + "]";
    }
}
