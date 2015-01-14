package org.elkan1788.osc.weixin.mp.core;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.elkan1788.osc.weixin.mp.MPSDK4J;
import org.elkan1788.osc.weixin.mp.commons.WxEventType;
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

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 微信普通消息互动
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @version 1.2.0
 * @since 2014/11/8
 */
public class WxBase {

    private static final Logger log = LoggerFactory.getLogger(WxBase.class);
    // 消息模式(默认明文)
    private boolean aesEncrypt = false;
    // 定义公众号信息
    private MPAct mpAct;
    // 微信加密
    private WXBizMsgCrypt wxInMsgCrt;
    // XML解析准备
    private SAXParserFactory factory = SAXParserFactory.newInstance();
    private SAXParser xmlParser;
    private XMLHandler xmlHandler = new XMLHandler();
    // 微信交互参数
    private String signature;
    private String msgSignature;
    private String timeStamp;
    private String nonce;
    private String echostr;
    // 微信消息流
    private InputStream wxInMsg;
    // 微信消息处理器
    private WxHandler wxHandler;
    // 解析/响应微信消息
    private ReceiveMsg rm;
    private OutPutMsg om;    

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

    /**
     * 微信基础功能参数初始化
     *
     * @param req 请求
     */
    public void init(HttpServletRequest req) {
        // 请求编码设置
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("设置微信服务器请求编辑时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }
        String sign = req.getParameter("signature");
        String time = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echo = req.getParameter("echostr");
        String encrypt = req.getParameter("encrypt_type");
        String msgSign = req.getParameter("msg_signature");
        InputStream wxInMsg = null;
        try {
            wxInMsg = req.getInputStream();
        } catch (IOException e) {
            log.error("接收微信消息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        setSignature(sign);
        setTimeStamp(time);
        setNonce(nonce);
        setEchostr(echo);
        if ("aes".equals(encrypt)) {
            setAesEncrypt(true);
            setMsgSignature(msgSign);
        }
        this.wxInMsg = wxInMsg;
    }

    /**
     * 微信URL接入校验
     *
     * @return 随机字符
     * @throws AesException
     */
    public String check() throws AesException {

        boolean check = wxHandler.check(this.mpAct.getToken(),
                this.signature, this.timeStamp, this.nonce);
        return check ? echostr : "";
    }

    /**
     * 微信消息处理
     *
     * @return 回复消息
     * @throws Exception
     */
    public String handler() throws Exception {
        clear();
        String reply = "";
        this.rm = convert2VO(this.wxInMsg);
        // 处理消息
        String msg_type = this.rm.getMsgType();
        if ("event".equals(msg_type)) {
            this.om = handlerEvent();
        } else {
            this.om = handlerMsg();
        }
        // 输出消息
        if (null != this.om) {
            reply = reply(this.om);
        }
        return reply;
    }

    /**
     * 处理普通的消息
     *
     * @return 回复消息实体
     * @throws Exception
     */
    private OutPutMsg handlerMsg() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("[MPSDK4J-{}]处理普通消息...", MPSDK4J.version());
        }
        OutPutMsg om = null;
        WxMsgType type = WxMsgType.valueOf(this.rm.getMsgType());
        switch (type) {
            case text:
                om = wxHandler.text(this.rm);
                break;
            case image:
                om = wxHandler.image(this.rm);
                break;
            case voice:
                om = wxHandler.voice(this.rm);
                break;
            case video:
                om = wxHandler.video(this.rm);
                break;
            case location:
                om = wxHandler.location(this.rm);
                break;
            case link:
                om = wxHandler.link(this.rm);
                break;
            default:
                om = wxHandler.def(this.rm);
                break;
        }
        return om;
    }

    /**
     * 处理事件推送消息
     *
     * @return 回复消息实体
     * @throws Exception
     */
    private OutPutMsg handlerEvent() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("[MPSDK4J-{}]处理事件推送消息...", MPSDK4J.version());
        }
        OutPutMsg om = null;
        WxEventType type = WxEventType.valueOf(this.rm.getEvent());
        switch (type) {
            case subscribe:
                om = wxHandler.eSub(this.rm);
                break;
            case unsubscribe:
                wxHandler.eUnSub(this.rm);
                break;
            case SCAN:
                wxHandler.eScan(this.rm);
                break;
            case CLICK:
                om = wxHandler.eClick(this.rm);
                break;
            case VIEW:
                wxHandler.eView(this.rm);
                break;
            case scancodpush:
                om = wxHandler.eScanCodePush(this.rm);
                break;
            case scancodwaitmsg:
                om = wxHandler.eScanCodeWait(this.rm);
                break;
            case pic_sysphoto:
                om = wxHandler.ePicSysPhoto(this.rm);
                break;
            case pic_photo_or_album:
                om = wxHandler.ePicPhotoOrAlbum(this.rm);
                break;
            case pic_weixin:
                om = wxHandler.ePicWeixin(this.rm);
                break;
            case location_select:
                om = wxHandler.eLocationSelect(this.rm);
                break;
            case LOCATION:
                wxHandler.eLocation(this.rm);
                break;
            case TEMPLATESENDJOBFINISH:
                wxHandler.eTemplateFinish(this.rm);
                break;
            case MASSSENDJOBFINISH:
                wxHandler.eSendJobFinish(this.rm);
                break;
            default:
                om = wxHandler.def(this.rm);
                break;
        }
        return om;
    }


    /**
     * 处理微信开放平台的推送消息
     *
     * @return 默认返回"success"
     * @throws Exception
     */
    public String handlerPush() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("[MPSDK4J-{}]处理开放平台推送消息...", MPSDK4J.version());
        }
        this.rm = convert2VO(this.wxInMsg);
        String info_type = this.rm.getInfoType();
        switch (info_type) {
            case "component_verify_ticket":
                wxHandler.eComponentVerifyTicket(this.rm);
                break;
            case "unauthorized":
                wxHandler.eUnAuthorizerMP(this.rm);
                break;
            default:
                break;
        }
        return "success";
    }

    /**
     * 将微信消息转换成接收消息VO对象
     *
     * @param msg 微信消息输入流
     * @return 接收消息VO对象
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
            String dcrp_msg = this.wxInMsgCrt.decryptMsg(this.msgSignature,
                    this.timeStamp, this.nonce, StreamTool.toString(msg));
            this.wxInMsg = StreamTool.toStream(dcrp_msg);
            this.xmlParser.parse(this.wxInMsg, this.xmlHandler);
        }

        ReceiveMsg rm = this.xmlHandler.getMsgVO();
        this.xmlHandler.clear();
        // 调试信息
        if (log.isInfoEnabled()) {
            log.info("[MPSDK4J-{}]接收到微信消息[{},{}]:...",
                    MPSDK4J.version(),
                    rm.getMsgId(), rm.getCreateTime());
            log.info("{}", rm);
        }

        return rm;
    }

    /**
     * 回复微信消息
     *
     * @param msg 输出消息VO对象
     * @return 微信消息
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
        if (log.isInfoEnabled()) {
            log.info("[MPSDK4J-{}]微信回复消息[{},{}]...",
                    MPSDK4J.version(),
                    msg.getMsgId(), msg.getCreateTime());
            log.info(reply_msg);
        }

        if (this.aesEncrypt) {// 加密
            reply_msg = this.wxInMsgCrt.encryptMsg(reply_msg, this.timeStamp, this.nonce);
        }

        return reply_msg;
    }

    private void clear() {
        if (null != this.rm) {
            this.rm = null;
        }
        if (null != this.om) {
            this.om = null;
        }
    }

    public boolean isAesEncrypt() {
        return aesEncrypt;
    }

    public void setAesEncrypt(boolean aesEncrypt) {
        this.aesEncrypt = aesEncrypt;
        if (aesEncrypt) {
            try {
                this.wxInMsgCrt = new WXBizMsgCrypt(this.mpAct.getToken(),
                        this.mpAct.getAESKey(), this.mpAct.getAppId());
            } catch (AesException e) {
                log.error("创建AES加密失败!!!");
                log.error(e.getLocalizedMessage(), e);
                this.wxInMsgCrt = null;
            }
        }
    }

    public MPAct getMpAct() {
        return mpAct;
    }

    public void setMpAct(MPAct mpAct) {
        this.mpAct = mpAct;
        if (log.isInfoEnabled()) {
            log.info("微信公众号信息...");
            log.info("{}", this.mpAct);
        }
    }

    public XMLHandler getXmlHandler() {
        return xmlHandler;
    }

    public String getSignature() {
        return signature;
    }

    public void setWxHandler(WxHandler wxHandler) {
        this.wxHandler = wxHandler;
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

    public void setWxInMsg(InputStream wxInMsg) {
        this.wxInMsg = wxInMsg;
    }

    public ReceiveMsg getReceiveMsg() {
        return rm;
    }

    public OutPutMsg getOutPutMsg() {
        return om;
    }
}
