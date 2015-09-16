package io.github.elkan1788.mpsdk4j.core;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.vo.BaseMsg;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;

/**
 * 创建微信公众平台被动响应消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class XmlMsgBuilder {

    private static final Log log = Logs.get();

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
     * @param msg
     *            输出消息实体
     */
    void msgPrefix(BaseMsg msg) {
        msgBuf.append("<ToUserName><![CDATA[")
              .append(msg.getToUserName())
              .append("]]></ToUserName>\n");
        msgBuf.append("<FromUserName><![CDATA[")
              .append(msg.getFromUserName())
              .append("]]></FromUserName>\n");
        msgBuf.append("<CreateTime>").append(msg.getCreateTime()).append("</CreateTime>\n");
        msgBuf.append("<MsgType><![CDATA[").append(msg.getMsgType()).append("]]></MsgType>\n");
    }

    /**
     * 被动文本消息
     * 
     * @param msg
     *            输出消息实体
     */
    public XmlMsgBuilder text(TextMsg msg) {
        msgPrefix(msg);
        msgBuf.append("<Content><![CDATA[").append(msg.getContent()).append("]]></Content>\n");
        return this;
    }

    /**
     * 被动图像消息
     *
     * @param msg
     *            输出消息实体
     */
    public XmlMsgBuilder image(ImageMsg msg) {
        msgPrefix(msg);
        msgBuf.append("<Image>");
        msgBuf.append("<MediaId><![CDATA[").append(msg.getMediaId()).append("]]></MediaId>\n");
        msgBuf.append("</Image>");
        return this;
    }

    /**
     * 输出回复消息
     *
     * @return 回复消息
     */
    public String build() {
        msgBuf.append("</xml>");
        if (log.isDebugEnabled()) {
            log.debugf("Xml message content: %s", msgBuf);
        }
        return new String(msgBuf);
    }
}
