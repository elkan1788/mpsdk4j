package io.github.elkan1788.mpsdk4j.core;

import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.common.EventType;
import io.github.elkan1788.mpsdk4j.common.MessageType;
import io.github.elkan1788.mpsdk4j.repo.com.qq.weixin.mp.aes.AesException;
import io.github.elkan1788.mpsdk4j.repo.com.qq.weixin.mp.aes.SHA1;
import io.github.elkan1788.mpsdk4j.repo.com.qq.weixin.mp.aes.WXBizMsgCrypt;
import io.github.elkan1788.mpsdk4j.util.StreamTool;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;
import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;
import io.github.elkan1788.mpsdk4j.vo.event.LocationEvent;
import io.github.elkan1788.mpsdk4j.vo.event.LocationSelectEvent;
import io.github.elkan1788.mpsdk4j.vo.event.MenuEvent;
import io.github.elkan1788.mpsdk4j.vo.event.ScanCodeEvent;
import io.github.elkan1788.mpsdk4j.vo.event.ScanEvent;
import io.github.elkan1788.mpsdk4j.vo.event.SendPhotosEvent;
import io.github.elkan1788.mpsdk4j.vo.message.BasicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.LinkMsg;
import io.github.elkan1788.mpsdk4j.vo.message.MusicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.NewsMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VideoMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VoiceMsg;

/**
 * 微信消息内核
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WechatKernel {

    private static final Log log = Logs.get();

    // XML解析准备
    private SAXParserFactory factory = SAXParserFactory.newInstance();
    private SAXParser xmlParser;
    private MessageHandler msgHandler = new MessageHandler();

    // Request参数
    private Map<String, String> params;
    // 公众号信息
    private MPAccount mpAct;

    public WechatKernel(MPAccount mpAct, Map<String, String> params) {
        this.mpAct = mpAct;
        this.params = params;
        try {
            xmlParser = factory.newSAXParser();
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
    }

    public String check() {
        String sign = params.get("signature");
        String ts = params.get("timestamp");
        String nonce = params.get("nonce");

        if (sign == null
            || sign.length() > 128
            || ts == null
            || ts.length() > 128
            || nonce == null
            || nonce.length() > 128) {
            log.warnf("The sign params are too long. Please check them.");
            return "error";
        }

        try {
            String echo = SHA1.calculate(mpAct.getToken(), sign, ts, nonce);
            if (log.isDebugEnabled()) {
                log.debugf("Sign echo string succes. echo: %s", echo);
            }
            return echo;
        }
        catch (AesException e) {
            throw Lang.wrapThrow(e);
        }
    }

    // TODO 是否考虑添加重复消息过滤功能
    public String handle(InputStream is, WechatHandler handler) {
        String encrypt = params.get("encrypt_type");
        WXBizMsgCrypt pc = null;
        BasicMsg msg = null;
        String respmsg = "success";
        // 密文模式
        if (encrypt != null && "aes".equals(encrypt) && mpAct.getAESKey() != null) {
            try {
                pc = new WXBizMsgCrypt(mpAct.getToken(), mpAct.getAESKey(), mpAct.getAppId());

                String ts = params.get("timestamp");
                String nonce = params.get("nonce");
                String msgsign = params.get("msg_signature");

                String decmsg = pc.decryptMsg(msgsign, ts, nonce, is);
                xmlParser.parse(StreamTool.toStream(decmsg), msgHandler);
                msg = handle(msgHandler.getValues(), handler);
                respmsg = pc.encryptMsg(responseXML(msg), ts, nonce);
            }
            catch (Exception e) {
                throw Lang.wrapThrow(e);
            }
        }
        // 明文模式
        else {
            try {
                xmlParser.parse(is, msgHandler);
            }
            catch (Exception e) {
                throw Lang.wrapThrow(e);
            }
            msg = handle(msgHandler.getValues(), handler);
            respmsg = responseXML(msg);
        }

        return respmsg;
    }

    protected BasicMsg handle(Map<String, String> data, WechatHandler handler) {
        String msgtype = msgHandler.getValues().get("msgType");
        if ("event".equals(msgtype)) {
            return handleEventMsg(msgtype, handler);
        }
        else {
            return handleNormalMsg(msgtype, handler);
        }
    }

    protected BasicMsg handleNormalMsg(String msgType, WechatHandler handler) {
        BasicMsg msg = null;
        MessageType mt = MessageType.valueOf(msgType);
        switch (mt) {
            case text:
                TextMsg tm = new TextMsg(msgHandler.getValues());
                msg = handler.text(tm);
                break;
            case image:
                ImageMsg im = new ImageMsg(msgHandler.getValues());
                msg = handler.image(im);
                break;
            case voice:
                VoiceMsg vom = new VoiceMsg(msgHandler.getValues());
                msg = handler.voice(vom);
                break;
            case video:
                VideoMsg vim = new VideoMsg(msgHandler.getValues());
                msg = handler.video(vim);
                break;
            case link:
                LinkMsg lm = new LinkMsg(msgHandler.getValues());
                msg = handler.link(lm);
                break;
            default:
                BasicMsg bm = new BasicMsg(msgHandler.getValues());
                msg = handler.defMsg(bm);
                break;
        }
        return msg;
    }

    protected BasicMsg handleEventMsg(String msgType, WechatHandler handler) {
        BasicMsg msg = null;
        EventType et = EventType.valueOf(msgType);
        switch (et) {
            case subscribe:
                BasicEvent sube = new BasicEvent(msgHandler.getValues());
                msg = handler.eSub(sube);
                break;
            case unsubscribe:
                BasicEvent unsube = new BasicEvent(msgHandler.getValues());
                msg = handler.eSub(unsube);
                break;
            case SCAN:
                ScanEvent se = new ScanEvent(msgHandler.getValues());
                msg = handler.eScan(se);
                break;
            case LOCATION:
                LocationEvent le = new LocationEvent(msgHandler.getValues());
                handler.eLocation(le);
                break;
            case CLICK:
                MenuEvent cme = new MenuEvent(msgHandler.getValues());
                msg = handler.eClick(cme);
                break;
            case VIEW:
                MenuEvent vme = new MenuEvent(msgHandler.getValues());
                handler.eView(vme);
                break;
            case scancode_push:
                ScanCodeEvent sce = new ScanCodeEvent(msgHandler.getValues());
                msg = handler.eScanCodePush(sce);
                break;
            case scancode_waitmsg:
                ScanCodeEvent scemsg = new ScanCodeEvent(msgHandler.getValues());
                msg = handler.eScanCodeWait(scemsg);
                break;
            case pic_sysphoto:
                SendPhotosEvent spesys = new SendPhotosEvent(msgHandler.getValues());
                msg = handler.ePicSysPhoto(spesys);
                break;
            case pic_photo_or_album:
                SendPhotosEvent spealb = new SendPhotosEvent(msgHandler.getValues());
                msg = handler.ePicPhotoOrAlbum(spealb);
                break;
            case pic_weixin:
                SendPhotosEvent spewx = new SendPhotosEvent(msgHandler.getValues());
                msg = handler.ePicWeixin(spewx);
                break;
            case location_select:
                LocationSelectEvent lse = new LocationSelectEvent(msgHandler.getValues());
                msg = handler.eLocationSelect(lse);
                break;
            case media_id:
            case view_limited:
                BasicEvent mvbe = new BasicEvent(msgHandler.getValues());
                msg = handler.defEvent(mvbe);
                break;
            default:
                BasicEvent be = new BasicEvent(msgHandler.getValues());
                msg = handler.defEvent(be);
                break;
        }
        return msg;
    }

    protected String responseXML(BasicMsg msg) {
        String respmsg = "success";
        if (msg == null || Strings.isBlank(msg.getMsgType())) {
            return respmsg;
        }

        MessageType mt = MessageType.valueOf(msg.getMsgType());
        switch (mt) {
            case text:
                respmsg = XmlMsgBuilder.create().text((TextMsg) msg).build();
                break;
            case image:
                respmsg = XmlMsgBuilder.create().image((ImageMsg) msg).build();
                break;
            case music:
                respmsg = XmlMsgBuilder.create().music((MusicMsg) msg).build();
                break;
            case video:
                respmsg = XmlMsgBuilder.create().video((VideoMsg) msg).build();
                break;
            case news:
                respmsg = XmlMsgBuilder.create().news((NewsMsg) msg).build();
                break;
            default:
                respmsg = XmlMsgBuilder.create().text((TextMsg) msg).build();
                break;
        }
        return respmsg;
    }
}
