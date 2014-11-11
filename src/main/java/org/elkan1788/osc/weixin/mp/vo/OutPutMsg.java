package org.elkan1788.osc.weixin.mp.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 输出消息实体
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/7
 * @version 1.0.0
 */
public class OutPutMsg extends BaseMsg {

	/**
	 * 音乐链接
	 */
	private String musicUrl;

	/**
	 * 高质量音乐链接
	 */
	private String hQMusicUrl;

    /**
     * 多图文消息
     */
	private List<Article> articles = new ArrayList<>();

    /**
     * 带基础参数的构造函数
     * @param fromUserName 微信用户openId
     * @param toUserName   微信公众号原始ID
     * @param msgType   消息类型
     */
    public OutPutMsg(String fromUserName, String toUserName, String msgType) {
        this.fromUserName = toUserName;
        this.toUserName = fromUserName;
        this.msgType =  msgType;
    }

    public OutPutMsg() {
        this.createTime = System.currentTimeMillis() / 1000;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String gethQMusicUrl() {
        return hQMusicUrl;
    }

    public void sethQMusicUrl(String hQMusicUrl) {
        this.hQMusicUrl = hQMusicUrl;
    }

    public List<Article> getArticles() {

        return articles;
    }

    public void setArticles(List<Article> articles) {
        if (null != articles
                && articles.size() > 10) {
            this.articles = articles.subList(0, 10);
        } else {
            this.articles = articles;
        }
    }

    @Override
    public String toString() {
        return "OutPutMsg{" +
                "msgId='" + msgId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", msgType='" + msgType + '\'' +
                ", event='" + event + '\'' +
                ", content='" + content + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", hQMusicUrl='" + hQMusicUrl + '\'' +
                ", articles=" + ((null==articles) ? 0 : articles.size()) +
                "} " + super.toString();
    }
}
