package io.github.elkan1788.mpsdk4j.core;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.vo.api.Template;
import io.github.elkan1788.mpsdk4j.vo.message.BasicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;

/**
 * 创建微信公众平台高级接口消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class JsonMsgBuilder {

    private static final Log log = Logs.get();

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
     * @param msg
     *            客服消息实体
     */
    void msgPrefix(BasicMsg msg) {
        msgBuf.append("\"touser\":\"").append(msg.getToUserName()).append("\",");
        msgBuf.append("\"msgtype\":\"").append(msg.getMsgType()).append("\",");
    }

    /**
     * 文本客服消息
     *
     * @param msg
     *            客服消息实体
     */
    public JsonMsgBuilder text(TextMsg msg) {
        msgPrefix(msg);
        msgBuf.append("\"text\": {");
        msgBuf.append(" \"content\":\"").append(msg.getContent()).append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 图像客服消息
     *
     * @param msg
     *            客服消息实体
     */
    public JsonMsgBuilder image(ImageMsg msg) {
        msgPrefix(msg);
        msgBuf.append("\"image\": {");
        msgBuf.append(" \"media_id\":\"").append(msg.getMediaId()).append("\"");
        msgBuf.append("}");
        return this;
    }

    /**
     * 模板消息
     *
     * @param openId
     *            接收者
     * @param tmlId
     *            模板Id
     * @param topColor
     *            顶部颜色
     * @param url
     *            链接地址
     * @param tmls
     *            模板数据
     */
    public JsonMsgBuilder template(String openId,
                                   String tmlId,
                                   String topColor,
                                   String url,
                                   Template... tmls) {
        msgBuf.append("\"touser\":\"").append(openId).append("\",");
        msgBuf.append("\"template_id\":\"").append(tmlId).append("\",");
        msgBuf.append("\"url\":\"").append(url).append("\",");
        msgBuf.append("\"topcolor\":\"").append(topColor).append("\",");
        msgBuf.append("\"data\":{");
        StringBuffer data = new StringBuffer("");
        for (Template t : tmls) {
            data.append(t.templateData()).append(",");
        }
        msgBuf.append(data.substring(0, data.lastIndexOf(",")));
        msgBuf.append("}");
        return this;
    }

    /**
     * 输出消息
     * 
     * @return json格式消息
     */
    public String build() {
        msgBuf.append("}");
        if (log.isDebugEnabled()) {
            log.debugf("Json message content: %s", msgBuf);
        }
        return new String(msgBuf);
    }
}
