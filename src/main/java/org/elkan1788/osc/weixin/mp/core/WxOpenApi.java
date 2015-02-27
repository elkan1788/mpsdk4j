package org.elkan1788.osc.weixin.mp.core;

import org.elkan1788.osc.weixin.mp.exception.WxRespException;
import org.elkan1788.osc.weixin.mp.vo.AuthInfo;
import org.elkan1788.osc.weixin.mp.vo.MPAct;

/**
 * 微信开放平台API接口设计
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/1/7
 * @version 1.0.0
 */
public interface WxOpenApi {

    /**
     * 设置公众号信息
     * @param mpAct 公众号信息
     */
    void setMpAct(MPAct mpAct);

    /**
     * 设置凭证
     * @param ticket    凭证
     */
    void setTicket(String ticket);

    /**
     * 获取公众号服务组件的令牌
     * @return 令牌
     * @throws WxRespException
     */
    String getComponentToken() throws WxRespException;

    /**
     * 刷新公众号服务组件的令牌
     * @throws WxRespException
     */
    void refreshComponentToken() throws WxRespException;

    /**
     * 获取公众号预授权码
     * @return  预授权码
     * @throws WxRespException
     */
    String getPreAuthCode() throws WxRespException;

    /**
     * 创建公众号预授权码
     * @throws WxRespException
     */
    void createPreAuthCode() throws WxRespException;

    /**
     * 使用授权码换取授权公众号的授权信息<pre/>
     * 并换取authorizer_access_token和authorizer_refresh_token
     * @param authCode  授权码
     * @return  授权信息
     * @throws WxRespException
     */
    AuthInfo queryAuth(String authCode) throws WxRespException;

    /**
     * 获取授权公众号的令牌
     *
     * @param authAppId         授权方appid
     * @param authRefreshToken  授权方的刷新令牌
     * @return  令牌
     * @throws WxRespException
     */
    String getAuthAccessToken(String authAppId, String authRefreshToken) throws WxRespException;

    /**
     * 刷新授权公众号的令牌
     *
     * @param authAppId         授权方appid
     * @param authRefreshToken  授权方的刷新令牌
     * @throws WxRespException
     */
    void refreshAuthAccessToken(String authAppId, String authRefreshToken) throws WxRespException;

    /**
     * 获取授权方的账户信息
     *
     * @param authAppId 授权方appid
     * @return  权方的账户信息
     * @throws WxRespException
     */
    AuthInfo getAuthorizerInfo(String authAppId) throws WxRespException;

    /**
     * 获取授权方的选项设置信息
     *
     * @param authAppId     授权方appid
     * @param optionName    选项名称(location_report,voice_recognize,customer_service)
     * @return  选项值
     * @throws WxRespException
     */
    String getAuthorizerOption(String authAppId, String optionName) throws WxRespException;

    /**
     * 设置授权方的选项设置信息
     *
     * @param authAppId     授权方appid
     * @param optionName    设置的选项名称
     * @param optionValue   设置的选项值
     *                      ocation_report(地理位置上报) 0关闭 1进入会话时上报 2每5s上报
     *                      voice_recognize（语音识别） 0关闭 1开启
     *                      customer_service（客服开关） 0关闭 1开启
     * @throws WxRespException
     */
    void setAuthorizerOption(String authAppId, String optionName, String optionValue) throws WxRespException;
}
