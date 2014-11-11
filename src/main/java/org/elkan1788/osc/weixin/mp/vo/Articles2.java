package org.elkan1788.osc.weixin.mp.vo;

/**
 * 高级群发消息的多图文
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/11
 * @version 1.0.0
 */
public class Articles2 {

    /**
     * 缩略图的media_id
     */
    private String mediaId;
    /**
     * 图文消息作者
     */
    private String author;
    /**
     * 标题
     */
    private String title;
    /**
     * 图文消息页面点击“阅读原文”后的页面
     */
    private String sourceUrl;
    /**
     * 图文消息页面的内容，支持HTML标签
     */
    private String content;
    /**
     * 图文消息的描述
     */
    private String digest;
    /**
     * 是否显示封面，1为显示，0为不显示
     */
    private int showCover;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public int getShowCover() {
        return showCover;
    }

    public void setShowCover(int showCover) {
        this.showCover = showCover;
    }
}
