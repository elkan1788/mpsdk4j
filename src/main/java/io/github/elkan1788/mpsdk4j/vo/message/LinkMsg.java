package io.github.elkan1788.mpsdk4j.vo.message;

import java.util.Map;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class LinkMsg extends BasicMsg {

    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息描述
     */
    private String description;
    /**
     * 消息链接
     */
    private String url;

    public LinkMsg() {
        super();
        this.msgType = "link";
    }

    public LinkMsg(Map<String, String> values) {
        super(values);
        this.title = values.get("title");
        this.description = values.get("description");
        this.url = values.get("url");
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LinkMsg [toUserName="
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
               + ", url="
               + url
               + "]";
    }
}
