package org.elkan1788.osc.weixin.mp.core;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.elkan1788.osc.weixin.mp.MPSDK4J;
import org.elkan1788.osc.weixin.mp.commons.WxMsgType;
import org.elkan1788.osc.weixin.mp.util.StreamTool;
import org.elkan1788.osc.weixin.mp.util.XMLHandler;
import org.elkan1788.osc.weixin.mp.util.XmlMsgBuilder;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;
import org.elkan1788.osc.weixin.mp.vo.ReceiveMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * 微信普通消息互动
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @since 2014/11/8
 * @version 1.2.0
 */
public class WxBase {

    private static final Logger log = LoggerFactory.getLogger(WxBase.class);

    // 开发模式(默认关闭)
    private boolean devMode = false;
    // 消息模式(默认明文)
    private boolean aesEncrypt = false;
    // 定义公众号信息
    private MPAct mpAct;
    // 微信加密
    private WXBizMsgCrypt wxMsgCrt;

    private SAXParserFactory factory = SAXParserFactory.newInstance();
    private SAXParser xmlParser;
    private XMLHandler xmlHandler = new XMLHandler();

    private String signature;
    private String msgSignature;
    private String timeStamp;
    private String nonce;
    private String echostr;

    private InputStream wxMsg;

    public WxBase() {
        try {
            xmlParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            log.error("SAX解析配置文件失败!!!");
            log.error(e.getLocalizedMessage(), e);
        } catch (SAXException e) {
            log.error("SAX异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public WxBase(boolean devMode, boolean aesEncrypt) {
        try {
            xmlParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            log.error("SAX解析配置文件失败!!!");
            log.error(e.getLocalizedMessage(), e);
        } catch (SAXException e) {
            log.error("SAX异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }
        this.devMode = devMode;
        this.aesEncrypt = aesEncrypt;
    }

    /**
     * 初始化
     *
     * @param req   HTTP请求
     * @param mpAct 公众号信息
     * @throws IOException
     */
    public void init(HttpServletRequest req, MPAct mpAct) throws IOException {
        setMpAct(mpAct);
        setSignature(req.getParameter("signature"));
        setTimeStamp(req.getParameter("timestamp"));
        setNonce(req.getParameter("nonce"));
        setEchostr(req.getParameter("echostr"));
        if (this.aesEncrypt) {
            this.setMsgSignature(req.getParameter("msg_signature"));
        }
        this.wxMsg = req.getInputStream();
    }

    /**
     * 微信URL接入校验
     *
     * @param handler   微信消息处理
     * @return  随机字符
     * @throws AesException
     */
    public String check(WxHandler handler) throws AesException {

        boolean check = handler.check(this.mpAct.getToken(),
                this.signature, this.timeStamp, this.nonce);
        return check ? echostr : "";
    }

    /**
     * 微信消息处理
     *
     * @param handler   消息处理器
     * @return  回复消息
     * @throws Exception
     */
    public String handler(WxHandler handler) throws Exception {

        String reply =  "";
        ReceiveMsg rm = convert2VO(wxMsg);
        WxMsgType type = null;
        if ("event".equals(rm.getMsgType())) {
            type = WxMsgType.valueOf(WxMsgType.EVENT_PREFIX + rm.getEvent());
        } else {
            type = WxMsgType.valueOf(rm.getMsgType());
        }
        OutPutMsg om = null;
        switch (type) {
            case text:
                om =  handler.text(rm);
                break;
            case image:
                om = handler.image(rm);
                break;
            case voice:
                om = handler.voice(rm);
                break;
            case video:
                om = handler.video(rm);
                break;
            case location:
                om = handler.location(rm);
                break;
            case link:
                om = handler.link(rm);
                break;
            case e_subscribe:
                om = handler.eSub(rm);
                break;
            case e_unsubscribe:
                handler.eUnSub(rm);
                break;
            case e_SCAN:
                handler.eScan(rm);
                break;
            case e_CLICK:
                om = handler.eClick(rm);
                break;
            case e_VIEW:
                handler.eView(rm);
                break;
            case e_LOCATION:
                handler.eLocation(rm);
                break;
            case e_TEMPLATESENDJOBFINISH:
                handler.eTemplateFinish(rm);
                break;
            case e_MASSSENDJOBFINISH:
                handler.eSendJobFinish(rm);
                break;
            default:
                om = handler.def(rm);
                break;
        }

        if (null != om) {
            reply = reply(om);
            rm = null;
            om = null;
        }

        return reply;
    }

    /**
     * 将微信消息转换成接收消息VO对象
     *
     * @param msg   微信消息输入流
     * @return  接收消息VO对象
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws AesException
     */
    private ReceiveMsg convert2VO(InputStream msg)
            throws ParserConfigurationException,
            SAXException,
            IOException,
            AesException {

        if (!this.aesEncrypt) { // 明文
            this.xmlParser.parse(msg, this.xmlHandler);
        } else {// 密文
            String dcrp_msg = this.wxMsgCrt.decryptMsg(this.msgSignature,
                    this.timeStamp, this.nonce, StreamTool.toString(msg));
            this.wxMsg = StreamTool.toStream(dcrp_msg);
            this.xmlParser.parse(this.wxMsg, this.xmlHandler);
        }

        ReceiveMsg rm = this.xmlHandler.getMsgVO();
        this.xmlHandler.clear();
        // 调试信息
        if (this.devMode) {
            log.info("[MPSDK4J-{}]接收到微信消息[{},{}]:...",
                    MPSDK4J.VERSION,
                    rm.getMsgId(), rm.getCreateTime());
            log.info("{}", rm);
        }

        return rm;
    }

    /**
     * 回复微信消息
     *
     * @param msg   输出消息VO对象
     * @return  微信消息
     * @throws AesException
     */
    private String reply(OutPutMsg msg) throws AesException {

        String reply_msg = "";
        // 获取消息类型
        WxMsgType msg_type = WxMsgType.valueOf(msg.getMsgType());
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

        // 调试信息
        if (this.devMode) {
            log.info("[MPSDK4J-{}]微信回复消息[{},{}]...",
                    MPSDK4J.VERSION,
                    msg.getMsgId(), msg.getCreateTime());
            log.info(reply_msg);
        }

        if (this.aesEncrypt) {// 加密
            reply_msg = this.wxMsgCrt.encryptMsg(reply_msg, this.timeStamp, this.nonce);
        }

        return reply_msg;
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public boolean isAesEncrypt() {
        return aesEncrypt;
    }

    public void setAesEncrypt(boolean aesEncrypt) {
        this.aesEncrypt = aesEncrypt;
    }

    public MPAct getMpAct() {
        return mpAct;
    }

    public void setMpAct(MPAct mpAct) {
        this.mpAct = mpAct;
        if (this.devMode) {
            log.info("微信公众号信息...");
            log.info("{}", this.mpAct);
        }
        if (this.aesEncrypt) {
            try {
                this.wxMsgCrt = new WXBizMsgCrypt(this.mpAct.getToken(),
                        this.mpAct.getAESKey(), this.mpAct.getAppId());
            } catch (AesException e) {
                log.error("创建AES加密失败!!!");
                log.error(e.getLocalizedMessage(), e);
                this.wxMsgCrt = null;
            }
        }
    }

    public XMLHandler getXmlHandler() {
        return xmlHandler;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMsgSignature() {
        return msgSignature;
    }

    public void setMsgSignature(String msgSignature) {
        this.msgSignature = msgSignature;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }
}
