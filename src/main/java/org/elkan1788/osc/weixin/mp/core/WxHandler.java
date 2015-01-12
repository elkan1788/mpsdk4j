package org.elkan1788.osc.weixin.mp.core;

import com.qq.weixin.mp.aes.AesException;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;
import org.elkan1788.osc.weixin.mp.vo.ReceiveMsg;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 微信消息处理接口
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/12/05
 * @version 1.0.0
 */
public interface WxHandler {

    /**
     * 微信接入时URL验证
     *
     * @param token     密钥
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机字符
     * @return  true或false
     * @throws AesException 签名异常
     */
    boolean check(String token,
                 String signature,
                 String timestamp,
                 String nonce) throws AesException;

    /**
     * 处理微信新功能的消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg def(ReceiveMsg rm);

    /**
     * 处理文本消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg text(ReceiveMsg rm);

    /**
     * 处理图像消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg image(ReceiveMsg rm);

    /**
     * 处理音频消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg voice(ReceiveMsg rm);

    /**
     * 处理视频消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg video(ReceiveMsg rm);

    /**
     * 处理主动上传地理位置消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg location(ReceiveMsg rm);

    /**
     * 处理链接消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg link(ReceiveMsg rm);

    /**
     * 处理菜单点击事件消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg eClick(ReceiveMsg rm);

    /**
     * 处理菜单视图事件消息
     *
     * @param rm    接收到的消息
     */
    void eView(ReceiveMsg rm);

    /**
     * 处理订阅事件消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg eSub(ReceiveMsg rm);

    /**
     * 处理退订事件消息
     *
     * @param rm    接收到的消息
     */
    void eUnSub(ReceiveMsg rm);

    /**
     * 处理扫描事件消息
     *
     * @param rm    接收到的消息
     */
    void eScan(ReceiveMsg rm);

    /**
     * 处理自动上传地理事件消息
     *
     * @param rm    接收到的消息
     */
    void eLocation(ReceiveMsg rm);

    /**
     * 处理二维码扫描事件消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg eScanCodePush(ReceiveMsg rm);

    /**
     * 扫码推事件且弹出“消息接收中”提示框
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg eScanCodeWait(ReceiveMsg rm);

    /**
     * 处理弹出系统拍照发图的事件推送
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg ePicSysPhoto(ReceiveMsg rm);

    /**
     * 处理弹出拍照或者相册发图的事件推送
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg ePicPhotoOrAlbum(ReceiveMsg rm);

    /**
     * 处理弹出微信相册发图器的事件推送
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg ePicWeixin(ReceiveMsg rm);

    /**
     * 处理弹出地理位置选择器的事件推送消息
     *
     * @param rm    接收到的消息
     * @return  回复消息
     */
    OutPutMsg eLocationSelect(ReceiveMsg rm);

    /**
     * 处理模板发送事件消息
     *
     * @param rm    接收到的消息
     */
    void eTemplateFinish(ReceiveMsg rm);

    /**
     * 处理群发消息事件消息
     *
     * @param rm    接收到的消息
     */
    void eSendJobFinish(ReceiveMsg rm);

    /**
     * 处理微信开放平台推送的Ticket事件消息<pre/>
     * (只要回复"success"即可)
     * @param rm    接收到的消息
     */
    void eComponentVerifyTicket(ReceiveMsg rm);

    /**
     * 处理微信开放平台推送的取消授权公众号事件消息<pre/>
     * (只要回复"success"即可)
     * @param rm    接收到的消息
     */
    void eUnAuthorizerMP(ReceiveMsg rm);
}
