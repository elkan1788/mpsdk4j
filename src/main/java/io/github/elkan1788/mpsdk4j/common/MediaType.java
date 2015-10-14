package io.github.elkan1788.mpsdk4j.common;

/**
 * 多媒体文件类型
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public enum MediaType {

    /**
     * 图片: 1M,支持JPG格式
     */
    image, 
    /**
     * 语音:2M,播放长度不超过60s,支持AMR\MP3格式
     */
    voice, 
    /**
     * 视频:10MB,支持MP4格式
     */
    video, 
    /**
     * 缩略图:64KB,支持JPG格式
     */
    thumb
}
