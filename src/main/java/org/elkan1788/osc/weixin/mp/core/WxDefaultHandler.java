package org.elkan1788.osc.weixin.mp.core;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.SHA1;
import org.elkan1788.osc.weixin.mp.commons.WxMsgType;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;
import org.elkan1788.osc.weixin.mp.vo.PicInfo;
import org.elkan1788.osc.weixin.mp.vo.ReceiveMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认的微信消息处理器
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/12/05
 * @version 1.0.0
 */
public class WxDefaultHandler implements WxHandler {

    private static final Logger log = LoggerFactory.getLogger(WxDefaultHandler.class);

    @Override
    public boolean check(String token,
                        String signature,
                        String timestamp,
                        String nonce) throws AesException {

        if (null == signature
                || signature.length() > 128
                || null == timestamp
                || timestamp.length() > 128
                || null == nonce
                || nonce.length() > 128) {
            log.error("验证签名参数失败!!!");
            log.error("signature={},timestamp={},nonce={}", signature, timestamp, nonce);
            return false;
        }

        if (log.isInfoEnabled()) {
           log.info("微信接入URL验证成功...");
           log.info("signature={},timestamp={},nonce={}", signature, timestamp, nonce);
        }

        String s = SHA1.calculate(token, timestamp, nonce);
        if (s.equals(signature)){
            return true;
        }

        return false;
    }


    @Override
    public OutPutMsg def(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        om.setContent("新的功能消息,消息类型为:"+rm.getMsgType());
        if (log.isInfoEnabled()) {
            log.info("微信新类型消息!!!");
            log.info("msgid={}, from={},to={},msgtype={}", rm.getMsgId(),
                    rm.getFromUserName(), rm.getTotalCnt(), rm.getMsgType());
        }
        return om;
    }

    @Override
    public OutPutMsg text(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(rm.getMsgType());
        om.setContent(rm.getContent() + "\n您的消息已经收到[微笑]");
        if (log.isInfoEnabled()) {
            log.info("接收到微信文本消息...");
            log.info("msgid={}, from={}, to={}, content={}", rm.getMsgId(),
                    rm.getFromUserName(), rm.getToUserName(), rm.getContent());
        }
        return om;
    }

    @Override
    public OutPutMsg image(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(rm.getMsgType());
        om.setMediaId(rm.getMediaId());
        if (log.isInfoEnabled()) {
            log.info("接收到微信图像消息...");
            log.info("msgid={}, from={}, to={}, mediaid={}", rm.getMsgId(),
                    rm.getFromUserName(), rm.getToUserName(), rm.getMediaId());
        }
        return om;
    }

    @Override
    public OutPutMsg voice(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        if (null != rm.getRecognition()) {
            om.setMsgType(WxMsgType.text.name());
            om.setContent("您的语音消息已接收.[微笑]\n内容为："+rm.getRecognition());
        } else {
            om.setMsgType(rm.getMsgType());
            om.setMediaId(rm.getMediaId());
        }
        if (log.isInfoEnabled()) {
            log.info("接收到音频消息...");
            log.info("msgid={}, from={}, to={}, mediaid={}, trans={}", rm.getMsgId(),
                    rm.getFromUserName(), rm.getToUserName(), rm.getMediaId(), rm.getRecognition());
        }
        return om;
    }

    @Override
    public OutPutMsg video(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(rm.getMsgType());
        om.setMediaId(rm.getMediaId());
        if (log.isInfoEnabled()) {
            log.info("接收到视频消息...");
            log.info("msgid={}, from={}, to={}, mediaid={}, thumbmid={}", rm.getMsgId(),
                    rm.getFromUserName(), rm.getToUserName(), rm.getMediaId(), rm.getThumbMediaId());
        }
        return om;
    }

    @Override
    public OutPutMsg location(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        om.setContent("您当前的位置:"+rm.getLabel()+
                ",坐标:["+rm.getLatitude()+","+rm.getLongitude()+
                "],地图缩放级别:"+rm.getScale());
        if (log.isInfoEnabled()) {
            log.info("接收到地理位置消息...");
            log.info("msgid={}, from={}, to={}, x={}, y={}, scale={}, label={}",
                    rm.getMsgId(), rm.getFromUserName(), rm.getToUserName(),
                    rm.getLatitude(), rm.getLongitude(), rm.getScale(), rm.getLabel());
        }
        return om;
    }

    @Override
    public OutPutMsg link(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        om.setContent(rm.getTitle()+"\n<a href=\""+rm.getUrl()+"\">点击打开</a>");
        if (log.isInfoEnabled()) {
            log.info("接收到链接消息...");
            log.info("msgid={}, from={}, to={}, title={}, desc={}, url={}",
                    rm.getMsgId(), rm.getFromUserName(), rm.getToUserName(),
                    rm.getTitle(), rm.getDescription(), rm.getUrl());
        }
        return om;
    }

    @Override
    public OutPutMsg eClick(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        om.setContent("MENU_CLICK:"+rm.getEventKey());
        if (log.isInfoEnabled()) {
            log.info("接收到菜单点击消息...");
            log.info("from={}, to={}, event={}, key={}",
                    rm.getFromUserName(), rm.getToUserName(), rm.getEvent(), rm.getEventKey());
        }
        return om;
    }

    @Override
    public void eView(ReceiveMsg rm) {
        if (log.isInfoEnabled()) {
            log.info("接收到菜单视图跳转消息...");
            log.info("from={}, to={}, event={}, key={}",
                    rm.getFromUserName(), rm.getToUserName(), rm.getEvent(), rm.getEventKey());
        }
    }

    @Override
    public OutPutMsg eSub(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        om.setContent("做人最重要的是要有自信，记得每天起床时，在镜子前对自己说，你很好，你可以的，树立生活自信，你可以的！");
        if (log.isInfoEnabled()) {
            log.info("接收到订阅消息...");
            log.info("from={}, to={}, event={}",
                    rm.getFromUserName(), rm.getToUserName(), rm.getEvent());
        }
        return om;
    }

    @Override
    public void eUnSub(ReceiveMsg rm) {
        if (log.isInfoEnabled()) {
            log.info("接收到退订消息...");
            log.info("from={}, to={}, event={}",
                    rm.getFromUserName(), rm.getToUserName(), rm.getEvent());
        }
    }

    @Override
    public OutPutMsg eScan(ReceiveMsg rm) {
        if (log.isInfoEnabled()) {
            log.info("接收到扫描消息...");
            log.info("msgid={}, from={}, to={}, event={}, key={}, ticket={}",
                    rm.getMsgId(), rm.getFromUserName(), rm.getToUserName(),
                    rm.getEvent(), rm.getEventKey(), rm.getTicket());
        }
        return null;
    }

    @Override
    public OutPutMsg eLocation(ReceiveMsg rm) {
        if (log.isInfoEnabled()) {
            log.info("接收到地理位置消息...");
            log.info("msgid={}, from={}, to={}, x={}, y={}, precision={}",
                    rm.getMsgId(), rm.getFromUserName(), rm.getToUserName(),
                    rm.getLatitude(), rm.getLongitude(), rm.getPrecision());
        }
        return null;
    }

    @Override
    public OutPutMsg eScanCodePush(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        String content = "您此次用二维码扫描菜单["+rm.getEventKey()+"],扫描结果为: "+rm.getScanResult();
        om.setContent(content);
        if (log.isInfoEnabled()) {
            log.info("接收到二维码扫描事件消息...");
            log.info("msgid={}, from={}, to={}, eventKey={}, scantype={}, scanresult={}",
                    rm.getMsgId(), rm.getFromUserName(),
                    rm.getToUserName(), rm.getEventKey(),
                    rm.getScanType(), rm.getScanResult());
        }
        return om;
    }

    @Override
    public OutPutMsg eScanCodeWait(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        String content = "您此次用扫描等待菜单["+rm.getEventKey()+"],扫描结果为: "+rm.getScanResult();
        om.setContent(content);
        if (log.isInfoEnabled()) {
            log.info("接收到扫码推事件且弹出“消息接收中”提示消息...");
            log.info("msgid={}, from={}, to={}, eventKey={}, scantype={}, scanresult={}",
                    rm.getMsgId(), rm.getFromUserName(),
                    rm.getToUserName(), rm.getEventKey(),
                    rm.getScanType(), rm.getScanResult());
        }
        return om;
    }

    @Override
    public OutPutMsg ePicSysPhoto(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        String content = "您此次用系统拍照["+rm.getEventKey()+"]共发了"+rm.getCount()+"张图片,图片的MD5值为: ";
        for (PicInfo pic : rm.getPicList()) {
            content += pic.getPicMd5Sum() + ", ";
        }
        om.setContent(content.substring(0, content.lastIndexOf(",")));
        if (log.isInfoEnabled()) {
            log.info("接收到菜单弹出系统拍照发图消息...");
            log.info("msgid={}, from={}, to={}, eventkey={} count={}, picmd5sum={}",
                    rm.getMsgId(), rm.getFromUserName(),
                    rm.getToUserName(), rm.getEventKey(),
                    rm.getCount(), String.valueOf(rm.getPicList()));
        }
        return om;
    }

    @Override
    public OutPutMsg ePicPhotoOrAlbum(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        String content = "您此次用拍照或相册["+rm.getEventKey()+"]共发了"+rm.getCount()+"张图片,图片的MD5值为: ";
        for (PicInfo pic : rm.getPicList()) {
            content += pic.getPicMd5Sum() + ", ";
        }
        om.setContent(content.substring(0, content.lastIndexOf(",")));
        if (log.isInfoEnabled()) {
            log.info("接收到菜单弹出拍照或者相册发图消息...");
            log.info("msgid={}, from={}, to={}, eventkey={}, count={}, picmd5sum={}",
                    rm.getMsgId(), rm.getFromUserName(),
                    rm.getToUserName(), rm.getEventKey(),
                    rm.getCount(), String.valueOf(rm.getPicList()));
        }
        return om;
    }

    @Override
    public OutPutMsg ePicWeixin(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        String content = "您此次用微信相册["+rm.getEventKey()+"]共发了"+rm.getCount()+"张图片,图片的MD5值为: ";
        for (PicInfo pic : rm.getPicList()) {
            content += pic.getPicMd5Sum() + ", ";
        }
        om.setContent(content.substring(0, content.lastIndexOf(",")));
        if (log.isInfoEnabled()) {
            log.info("接收到菜单微信相册发图消息...");
            log.info("msgid={}, from={}, to={}, eventkey={}, count={}, picmd5sum={}",
                    rm.getMsgId(), rm.getFromUserName(),
                    rm.getToUserName(), rm.getEventKey(),
                    rm.getCount(), String.valueOf(rm.getPicList()));
        }
        return om;
    }

    @Override
    public OutPutMsg eLocationSelect(ReceiveMsg rm) {
        OutPutMsg om = new OutPutMsg(rm);
        om.setMsgType(WxMsgType.text.name());
        om.setContent("菜单值:"+rm.getEventKey()+",您当前的位置:"+rm.getLabel()+
                ",坐标:["+rm.getLatitude()+","+rm.getLongitude()+
                "],地图缩放级别:"+rm.getScale()+",朋友圈:"+rm.getPoiName());
        if (log.isInfoEnabled()) {
            log.info("接收到菜单地理位置消息...");
            log.info("msgid={}, from={}, eventkey={}, to={}, " +
                            "x={}, y={}, precision={}, label={}, poiname={}",
                    rm.getMsgId(), rm.getFromUserName(),
                    rm.getEventKey(), rm.getToUserName(),
                    rm.getLatitude(), rm.getLongitude(),
                    rm.getPrecision(), rm.getLabel(), rm.getPoiName());
        }
        return om;
    }

    @Override
    public void eTemplateFinish(ReceiveMsg rm) {
       if (log.isInfoEnabled()) {
            log.info("接收到模板推送消息...");
            log.info("from={}, to={}, msgid={}, status={}",
                    rm.getFromUserName(), rm.getToUserName(), rm.getMsgId(), rm.getStatus());
        }
    }

    @Override
    public void eSendJobFinish(ReceiveMsg rm) {
        if (log.isInfoEnabled()) {
            log.info("接收到群发推送消息...");
            log.info("from={}, to={}, msgid={}, status={}, total={}, filter={}, sent={}, err={}",
                    rm.getFromUserName(), rm.getToUserName(), rm.getMsgId(), rm.getStatus(),
                    rm.getTotalCnt(), rm.getFilterCnt(), rm.getSentCnt(), rm.getErrorCnt());
        }
    }

    @Override
    public void eComponentVerifyTicket(ReceiveMsg rm) {
        if (log.isDebugEnabled()) {
            log.info("接收到微信开放平台推送的组件Ticket消息...");
            log.info("appid={}, infotype={}, ticket={}", rm.getAppId(), rm.getInfoType(), rm.getTicket());
        }
    }

    @Override
    public void eUnAuthorizerMP(ReceiveMsg rm) {
        if (log.isDebugEnabled()) {
            log.info("接收到微信开放平台推送的公众号取消授权消息...");
            log.info("appid={}, infotype={}, unauthorizerappid={}", rm.getAppId(), rm.getInfoType(), rm.getUnAuthAppid());
        }
    }
}
