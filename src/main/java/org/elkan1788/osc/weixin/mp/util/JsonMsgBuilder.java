package org.elkan1788.osc.weixin.mp.util;

import org.elkan1788.osc.weixin.mp.commons.WxMsgType;
import org.elkan1788.osc.weixin.mp.vo.Article;
import org.elkan1788.osc.weixin.mp.vo.Article2;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;
import org.elkan1788.osc.weixin.mp.vo.Template;

/**
 * 创建微信客服消息
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/10
 * @version 1.0.0
 */
public class JsonMsgBuilder {

    private final StringBuffer msgBuf = new StringBuffer("{");

    /**
     * 创建
     */
    public static JsonMsgBuilder create() {
        return new JsonMsgBuilder();
    }

    /**
     * 创建消息体前缀
     *
     * @param msg   客服消息实体
     */
    void msgPrefix(OutPutMsg msg) {
        msgBuf.append("\"touser\":\"")
                .append(msg.getToUserName())
                .append("\",");
        msgBuf.append("\"msgtype\":\"")
                .append(msg.getMsgType())
                .append("\",");
    }

    void msgSuffix(OutPutMsg msg) {
        msgBuf.append("\"msgtype\":\"").append(msg.getMsgType()).append("\"");
    }

    /**
     * 文本客服消息
     *
     * @param msg   客服消息实体
     */
    public JsonMsgBuilder text(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("\"text\": {");
        msgBuf.append(" \"content\":\"")
                .append(msg.getContent())
                .append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 图像客服消息
     *
     * @param msg   客服消息实体
     */
    public JsonMsgBuilder image(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("\"image\": {");
        msgBuf.append(" \"media_id\":\"")
                .append(msg.getMediaId())
                .append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 语音客服消息
     *
     * @param msg   客服消息实体
     */
    public JsonMsgBuilder voice(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("\"voice\": {");
        msgBuf.append(" \"media_id\":\"")
                .append(msg.getMediaId())
                .append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 视频客服消息
     *
     * @param msg   客服消息实体
     */
    public JsonMsgBuilder video(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("\"video\": {");
        msgBuf.append(" \"media_id\":\"")
                .append(msg.getMediaId())
                .append("\",");
        msgBuf.append(" \"thumb_media_id\":\"")
                .append(msg.getThumbMediaId())
                .append("\",");
        msgBuf.append(" \"title\":\"")
                .append(msg.getTitle())
                .append("\",");
        msgBuf.append(" \"description\":\"")
                .append(msg.getDescription())
                .append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 音乐客服消息
     *
     * @param msg   客服消息实体
     */
    public JsonMsgBuilder music(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("\"music\": {");
        msgBuf.append(" \"title\":\"")
                .append(msg.getTitle())
                .append("\",");
        msgBuf.append(" \"description\":\"")
                .append(msg.getDescription())
                .append("\",");
        msgBuf.append(" \"musicurl\":\"")
                .append(msg.getMusicUrl())
                .append("\",");
        msgBuf.append(" \"hqmusicurl\":\"")
                .append(msg.gethQMusicUrl())
                .append("\",");
        msgBuf.append(" \"thumb_media_id\":\"")
                .append(msg.getThumbMediaId())
                .append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 多图文客服消息
     *
     * @param msg   客服消息实体
     */
    public JsonMsgBuilder news(OutPutMsg msg) {
        msgPrefix(msg);
        StringBuffer arts_buf = new StringBuffer("\"articles\": [");
        StringBuffer art_buf = new StringBuffer();
        for (Article art : msg.getArticles()) {
            art_buf.setLength(0);
            art_buf.append("{");
            art_buf.append(" \"title\":\"")
                    .append(art.getTitle())
                    .append("\",");
            art_buf.append(" \"description\":\"")
                    .append(art.getDescription())
                    .append("\",");
            art_buf.append(" \"picurl\":\"")
                    .append(art.getPicUrl())
                    .append("\",");
            art_buf.append(" \"url\":\"")
                    .append(art.getUrl())
                    .append("\",");
            art_buf.append("},");
            arts_buf.append(art_buf);
        }
        arts_buf.append("]");
        msgBuf.append("\"news\": {");
        msgBuf.append(arts_buf.substring(0, arts_buf.lastIndexOf(",")));
        msgBuf.append("}");
        return this;
    }

    /**
     * 模板消息
     *
     * @param openId    接收者
     * @param templateId    模板ID
     * @param topColor  顶部颜色
     * @param url   链接地址
     * @param templates 模板数据
     */
    public JsonMsgBuilder template(String openId, String templateId,
                                   String topColor, String url, Template... templates) {
        msgBuf.append("\"touser\":\"").append(openId).append("\",");
        msgBuf.append("\"template_id\":\"").append(templateId).append("\",");
        msgBuf.append("\"url\":\"").append(url).append("\",");
        msgBuf.append("\"topcolor\":\"").append(topColor).append("\",");
        msgBuf.append("\"data\":{");
        StringBuffer data = new StringBuffer("");
        for (Template t : templates) {
           data.append(t.templateData()).append(",");
        }
        msgBuf.append(data.substring(0, data.lastIndexOf(",")));
        msgBuf.append("}");
        return this;
    }

    /**
     * 上传多图文消息
     *
     * @param articles2s    图文消息
     */
    public JsonMsgBuilder uploadNews(Article2... articles2s) {
        msgBuf.append("\"articles\":[");
        StringBuffer art2_buf = new StringBuffer();
        for (Article2 art2 : articles2s) {
            art2_buf.append("{");
            art2_buf.append("\"thumb_media_id\":\"").append(art2.getMediaId()).append("\",");
            art2_buf.append("\"author\":\"").append(art2.getAuthor()).append("\",");
            art2_buf.append("\"title\":\"").append(art2.getTitle()).append("\",");
            art2_buf.append("\"content_source_url\":\"").append(art2.getSourceUrl()).append("\",");
            art2_buf.append("\"content\":\"").append(art2.getContent()).append("\",");
            art2_buf.append("\"digest\":\"").append(art2.getDigest()).append("\",");
            art2_buf.append("\"show_cover_pic\":\"").append(art2.getShowCover()).append("\"");
            art2_buf.append("},");
        }
        msgBuf.append(art2_buf.substring(0, art2_buf.lastIndexOf(",")));
        msgBuf.append("]");
        return this;
    }

    /**
     * 上传视频
     *
     * @param mediaId   多媒体ID
     * @param title 视频标题
     * @param description   视频描述
     */
    public JsonMsgBuilder uploadVideo(String mediaId, String title, String description) {
        msgBuf.append("\"media_id\":\"").append(mediaId).append("\",");
        msgBuf.append("\"title\":\"").append(title).append("\",");
        msgBuf.append("\"description\":\"").append(description).append("\"");
        return this;
    }

    /**
     * 群发消息
     *
     * @param msg 输出消息实体
     */
    public JsonMsgBuilder sendAll(OutPutMsg msg) {
        if (msg.getToUsers().isEmpty()) {
          msgBuf.append("\"filter\":{")
                  .append("\"group_id\":\"")
                  .append(msg.getGroupId())
                  .append("\"},");
        } else {
            msgBuf.append("\"touser\":[");
            StringBuffer users_buf = new StringBuffer();
            for (String touser : msg.getToUsers()) {
                users_buf.append("\"").append(touser).append("\",");
            }
            msgBuf.append(users_buf.substring(0, users_buf.lastIndexOf(",")));
            msgBuf.append("],");
        }

        WxMsgType type = WxMsgType.valueOf(msg.getMsgType());
        switch (type) {
            case text:
                msgBuf.append("\"text\":{\"content\":\"").append(msg.getContent()).append("\"}");
                break;
            case image:
                msgBuf.append("\"image\":{\"media_id\":\"").append(msg.getMediaId()).append("\"}");
                break;
            case voice:
                msgBuf.append("\"voice\":{\"media_id\":\"").append(msg.getMediaId()).append("\"}");
                break;
            case mpvideo:
                msgBuf.append("\"mpvideo\":{\"media_id\":\"").append(msg.getMediaId()).append("\"}");
                break;
            case video:
                msgBuf.append("\"video\":{\"media_id\":\"")
                        .append(msg.getMediaId())
                        .append("\",\"title\":\"")
                        .append(msg.getTitle())
                        .append("\",\"description\":\"")
                        .append(msg.getDescription()).append("\"}");
                break;
            case mpnews:
                msgBuf.append("\"mpnews\":{\"media_id\":\"").append(msg.getMediaId()).append("\"}");
                break;
            default:
                break;
        }

        msgBuf.append(",\"msgtype\":\"").append(msg.getMsgType()).append("\"");

        return this;
    }

    public String build() {
        msgBuf.append("}");
        return msgBuf.toString();
    }
}
