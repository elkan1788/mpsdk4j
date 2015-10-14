package io.github.elkan1788.mpsdk4j.vo.message;

/**
 * 图文内容
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class Article {

    /**
     * 图文消息标题
     */
    private String title;
    /**
     * 图文消息描述
     */
    private String description;
    /**
     * 图片链接,支持JPG,PNG格式,
     * </p>
     * 较好的效果为大图360*200,小图200*200
     */
    private String picUrl;
    /**
     * 点击图文消息跳转链接
     */
    private String url;

    public Article() {}

    public Article(String title, String description, String picUrl, String url) {
        super();
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
