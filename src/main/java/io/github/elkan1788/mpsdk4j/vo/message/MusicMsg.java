package io.github.elkan1788.mpsdk4j.vo.message;

import java.util.Map;

import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class MusicMsg extends BasicMsg {

    /**
     * 音乐标题
     */
    private String title;
    /**
     * 音乐描述
     */
    private String description;
    /**
     * 音乐链接
     */
    private String musicUrl;
    /**
     * 高质音乐链接
     */
    private String HQMusicUrl;
    /**
     * 缩略图的媒体id,通过素材管理接口上传多媒体文件,得到的id
     */
    private String thumbMediaId;

    public MusicMsg() {
        super();
        this.msgType = "music";
    }

    public MusicMsg(BasicEvent event) {
        super(event);
        this.msgType = "music";
    }

    public MusicMsg(BasicMsg msg) {
        super(msg);
        this.msgType = "music";
    }

    public MusicMsg(Map<String, String> values) {
        super(values);
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

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String hQMusicUrl) {
        HQMusicUrl = hQMusicUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    @Override
    public String toString() {
        return "MusicMsg [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", msgId="
               + msgId
               + ", title="
               + title
               + ", description="
               + description
               + ", musicUrl="
               + musicUrl
               + ", HQMusicUrl="
               + HQMusicUrl
               + ", thumbMediaId="
               + thumbMediaId
               + "]";
    }

}
