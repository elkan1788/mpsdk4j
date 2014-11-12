package org.elkan1788.osc.weixin.mp.util;

import org.elkan1788.osc.weixin.mp.vo.Article;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;

/**
 * 创建微信被动消息回复
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/9
 * @version 1.0.0
 */
public class XmlMsgBuilder {

    private final StringBuffer msgBuf = new StringBuffer("<xml>\n");;

    /**
     * 创建
     */
    public static XmlMsgBuilder create() {
        return new XmlMsgBuilder();
    }

    /**
     * 创建消息体前缀
     *
     * @param msg   输出消息实体
     */
    void msgPrefix(OutPutMsg msg) {
        msgBuf.append("<ToUserName><![CDATA[")
                .append(msg.getToUserName())
                .append("]]></ToUserName>\n");
        msgBuf.append("<FromUserName><![CDATA[")
                .append(msg.getFromUserName())
                .append("]]></FromUserName>\n");
        msgBuf.append("<CreateTime>")
                .append(msg.getCreateTime())
                .append("</CreateTime>\n");
        msgBuf.append("<MsgType><![CDATA[")
                .append(msg.getMsgType())
                .append("]]></MsgType>\n");
    }

    /**
     * 被动文本消息
     * @param msg   输出消息实体
     */
    public XmlMsgBuilder text(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("<Content><![CDATA[")
                .append(msg.getContent()).append("]]></Content>\n");
        return this;
    }

    /**
     * 被动图像消息
     *
     * @param msg   输出消息实体
     */
    public XmlMsgBuilder image(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("<Image>");
        msgBuf.append("<MediaId><![CDATA[")
                .append(msg.getMediaId())
                .append("]]></MediaId>\n");
        msgBuf.append("</Image>");
        return this;
    }

    /**
     * 被动语音消息
     *
     * @param msg   输出消息实体
     */
    public XmlMsgBuilder vioce(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("<Voice>");
        msgBuf.append("<MediaId><![CDATA[")
                .append(msg.getMediaId())
                .append("]]></MediaId>\n");
        msgBuf.append("</Voice>\n");
        return this;
    }

    /**
     * 被动视频消息
     *
     * @param msg   输出消息实体
     */
    public XmlMsgBuilder video(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("<Video>");
        msgBuf.append("<MediaId><![CDATA[")
                .append(msg.getMediaId())
                .append("]]></MediaId>\n");
        msgBuf.append("<Title><![CDATA[")
                .append(msg.getTitle())
                .append("]]></Title>\n");
        msgBuf.append("<Description><![CDATA[")
                .append(msg.getDescription())
                .append("]]></Description>\n");
        msgBuf.append("</Video>\n");
        return this;
    }

    /**
     * 被动音乐消息
     *
     * @param msg   输出消息实体
     */
    public XmlMsgBuilder music(OutPutMsg msg) {
        msgPrefix(msg);
        msgBuf.append("<Music>");
        msgBuf.append("<Title><![CDATA[")
                .append(msg.getTitle())
                .append("]]></Title>\n");
        msgBuf.append("<Description><![CDATA[")
                .append(msg.getDescription())
                .append("]]></Description>\n");
        msgBuf.append("<MusicUrl><![CDATA[")
                .append(msg.getMusicUrl())
                .append("]]></MusicUrl>\n");
        msgBuf.append("<HQMusicUrl><![CDATA[")
                .append(msg.gethQMusicUrl())
                .append("]]></HQMusicUrl>\n");
        msgBuf.append("<ThumbMediaId><![CDATA[")
                .append(msg.getThumbMediaId())
                .append("]]></ThumbMediaId>\n");
        msgBuf.append("</Music>\n");
        return this;
    }

    /**
     * 被动多图文消息
     *
     * @param msg   输出消息实体
     */
    public XmlMsgBuilder news(OutPutMsg msg) {
        msgPrefix(msg);
        StringBuffer arts_buf = new StringBuffer("<Articles>\n");
        StringBuffer item_buf = new StringBuffer();
            for (Article art : msg.getArticles()) {
                item_buf.setLength(0);
                item_buf.append("<item>\n");
                item_buf.append("<Title><![CDATA[")
                        .append(art.getTitle())
                        .append("]]></Title>\n");
                item_buf.append("<Description><![CDATA[")
                        .append(art.getDescription())
                        .append("]]></Description>\n");
                item_buf.append("<PicUrl><![CDATA[")
                        .append(art.getPicUrl())
                        .append("]]></PicUrl>\n");
                item_buf.append("<Url><![CDATA[")
                        .append(art.getUrl())
                        .append("]]></Url>\n");
                item_buf.append("</item>\n");
                arts_buf.append(item_buf);
            }
        arts_buf.append("</Articles>\n");
        msgPrefix(msg);
        msgBuf.append("<ArticleCount>")
                .append(msg.getArticles().size())
                .append("</ArticleCount>\n");
        msgBuf.append(arts_buf);
        return this;
    }

    /**
     * AES加密信息
     *
     * @param xml           消息原文
     * @param msgSignature  消息签名
     * @param timeStamp     时间戳
     * @param nonce         随机字符
     */
    public String encrypt(String xml, String msgSignature,
                                 String timeStamp, String nonce) {

        msgBuf.setLength(0);
        msgBuf.append("<xml>\n");
        msgBuf.append("<Encrypt><![CDATA[")
                .append(xml)
                .append("]]></Encrypt>\n");
        msgBuf.append("<MsgSignature><![CDATA[")
                .append(msgSignature)
                .append("]]></MsgSignature>\n");
        msgBuf.append("<TimeStamp>")
                .append(timeStamp)
                .append("</TimeStamp>\n");
        msgBuf.append("<Nonce><![CDATA[")
                .append(nonce)
                .append("]]></Nonce>\n");
        msgBuf.append("</xml>");
        return  msgBuf.toString();
    }

    /**
     * 创建回复消息
     *
     * @return  回复消息
     */
    public String build() {
        msgBuf.append("</xml>");
        return msgBuf.toString();
    }
}
