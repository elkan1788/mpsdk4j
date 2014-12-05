package org.elkan1788.osc.weixin.mp.core;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.elkan1788.osc.weixin.mp.MPSDK4J;
import org.elkan1788.osc.weixin.mp.commons.MsgType;
import org.elkan1788.osc.weixin.mp.util.StreamTool;
import org.elkan1788.osc.weixin.mp.util.XMLHandler;
import org.elkan1788.osc.weixin.mp.util.XmlMsgBuilder;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;
import org.elkan1788.osc.weixin.mp.vo.ReceiveMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * 微信消息适配器
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @since 2014/11/8
 * @version 1.0.0
 */
public class WxMsgParser {

    private static final Logger log = LoggerFactory.getLogger(WxMsgParser.class);

    // 开发模式(默认关闭)
    public static boolean DEV_MODE = false;
    // 消息模式(默认明文)
    public static boolean AES_ENCRYPT = false;
    // 定义公众号信息
    private static MPAct mpAct;
    // 微信加密
    private static WXBizMsgCrypt wxcrp;

    /**
     * 启用开发模式输出微信收发消息
     */
    public static void enableDev() {
        DEV_MODE = true;
        log.warn("[MPSDK4J-{}]启用开发模式状态...", MPSDK4J.VERSION);
    }

    /**
     * 启用消息加密
     */
    public static void enableAES() {
        AES_ENCRYPT = true;
        log.warn("[MPSDK4J-{}]启用消息安全模式...", MPSDK4J.VERSION);
    }

    /**
     * 关闭开发模式输出微信收发消息
     */
    public static void disableDev() {
        DEV_MODE = false;
        log.warn("[MPSDK4J-{}]关闭开发模式状态...", MPSDK4J.VERSION);
    }

    /**
     * 关闭消息加密
     */
    public static void disableAES() {
        AES_ENCRYPT = false;
        log.warn("[MPSDK4J-{}]关闭消息安全模式...", MPSDK4J.VERSION);
    }

    /**
     * 添加公众号信息
     *
     * @param mpAct 公众号
     */
    public static void addMpAct(MPAct mpAct) {
        WxMsgParser.mpAct = mpAct;
        if (DEV_MODE) {
            log.info("微信公众号信息...");
            log.info("{}", WxMsgParser.mpAct);
        }
        if (AES_ENCRYPT) {
            try {
                wxcrp = new WXBizMsgCrypt(mpAct.getToken(), mpAct.getAESKey(), mpAct.getAppId());
            } catch (AesException e) {
                log.error("创建AES加密失败!!!");
                log.error(e.toString());
                wxcrp = null;
            }
        }
    }

    /**
     * 将微信消息转换成VO对象
     *
     * @param msg       消息输入流
     * @param msgSignature  消息签名
     * @param timeStamp 时间戳
     * @param nonce     随机字符
     * @return  消息VO对象
     * @throws java.lang.Exception
     */
	public static ReceiveMsg convert2VO(InputStream msg,
                                        String msgSignature,
                                        String timeStamp,
                                        String nonce)
            throws ParserConfigurationException,
                                        SAXException,
                                        IOException,
                                        AesException {

        ReceiveMsg rm = null;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        if (!AES_ENCRYPT) { // 明文
            parser.parse(msg, handler);
            rm = handler.getMsgVO();
        } else {// 密文
            String dcrp_msg = wxcrp.decryptMsg(msgSignature,
                    timeStamp, nonce, StreamTool.toString(msg));
            msg = StreamTool.toStream(dcrp_msg);
            parser.parse(msg, handler);
            rm = handler.getMsgVO();
        }

        // 调试信息
        if (DEV_MODE) {
            log.info("[MPSDK4J-{}]接收到微信消息[{},{}]:...",
                    MPSDK4J.VERSION,
                    rm.getMsgId(), rm.getCreateTime());
            log.info("{}", rm);
        }

        return rm;
	}

	/**
	 * 回复消息
     *
     * @param msg 消息VO对象
     * @param msgSignature 消息签名
     * @param timeStamp 消息时间戳
     * @param nonce 随机字符
     * @return 回复内容
	 */
	public static String reply(OutPutMsg msg,
                               String msgSignature,
                               String timeStamp,
                               String nonce) throws AesException {

        String reply_msg = "";
        // 获取消息类型
        MsgType msg_type = MsgType.valueOf(msg.getMsgType());
        switch (msg_type) {
            case text:
                reply_msg = XmlMsgBuilder.create().text(msg).build();
                break;
            case image:
                reply_msg = XmlMsgBuilder.create().image(msg).build();
                break;
            case voice:
                reply_msg = XmlMsgBuilder.create().vioce(msg).build();
                break;
            case video:
                reply_msg = XmlMsgBuilder.create().video(msg).build();
                break;
            case music:
                reply_msg = XmlMsgBuilder.create().music(msg).build();
                break;
            case news:
                reply_msg = XmlMsgBuilder.create().news(msg).build();
                break;
            default:
                break;
        }

        if (AES_ENCRYPT) {// 加密
           reply_msg = wxcrp.encryptMsg(reply_msg, msgSignature, timeStamp, nonce);
        }

        // 调试信息
        if (DEV_MODE) {
            log.info("[MPSDK4J-{}]微信回复消息[{},{}]...",
                    MPSDK4J.VERSION,
                    msg.getMsgId(), msg.getCreateTime());
            log.info(reply_msg);
        }
        return reply_msg;
	}
}
