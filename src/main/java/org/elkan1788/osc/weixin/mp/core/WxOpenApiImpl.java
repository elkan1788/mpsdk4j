package org.elkan1788.osc.weixin.mp.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elkan1788.osc.weixin.mp.commons.WxApiUrl;
import org.elkan1788.osc.weixin.mp.exception.WxRespException;
import org.elkan1788.osc.weixin.mp.util.SimpleHttpReq;
import org.elkan1788.osc.weixin.mp.vo.AuthInfo;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信开放平台API接口设计
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/1/7
 * @version 1.0.0
 */
public class WxOpenApiImpl implements WxOpenApi {

    private static final Logger log = LoggerFactory.getLogger(WxOpenApiImpl.class);

    private MPAct mpAct;

    private String ticket;

    public WxOpenApiImpl() {
    }

    /**
     * 微信开放平台接口构建
     *
     * @param mpAct     服务组件公众号信息
     * @param ticket    允许凭证
     */
    public WxOpenApiImpl(MPAct mpAct, String ticket) {
        this.mpAct = mpAct;
        this.ticket = ticket;
    }

    @Override
    public void setMpAct(MPAct mpAct) {
        this.mpAct = mpAct;
    }

    @Override
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String getComponentToken() throws WxRespException {
        String token = mpAct.getAccessToken();
        if (null == token
                || token.isEmpty()
                || mpAct.getExpiresIn() < System.currentTimeMillis()) {
            synchronized (this){
                refreshComponentToken();
            }
            token = mpAct.getAccessToken();
        }
        return token;
    }

    @Override
    public void refreshComponentToken() throws WxRespException {
        String result = "";

        String data = "{" +
                "\"component_appid\":\"" + mpAct.getAppId() + "\"," +
                "\"component_appsecret\":\"" + mpAct.getAppSecret() + "\"," +
                "\"component_verify_ticket\":\"" + ticket + "\"" +
                "}";

        try {
            result = SimpleHttpReq.post(WxApiUrl.COMPONENT_TOKEN_API,
                    SimpleHttpReq.APPLICATION_JSON,
                    data);
        } catch (IOException e) {
            log.error("刷新服务组件ACCESS_TOKEN时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
            throw new WxRespException(result);
        }

        mpAct.createAccessToken(result);
    }

    @Override
    public void createPreAuthCode() throws WxRespException {
        String url = String.format(WxApiUrl.COMPONENT_API,
                "api_create_preauthcode", getComponentToken());
        String result = "";
        String data = "{" +
                "\"component_appid\":\"" + mpAct.getAppId() + "\"" +
                "}";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, data);
        } catch (IOException e) {
            log.error("创建公众权预授权码时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
            throw new WxRespException(result);
        }

        mpAct.createPreAuthCode(result);
    }

    @Override
    public String getPreAuthCode() throws WxRespException {
        String auth_code = mpAct.getPreAuthCode();
        if (null == auth_code
                || mpAct.getPreAuthExpiresIn() < System.currentTimeMillis()){
            synchronized (this.mpAct){
                createPreAuthCode();
            }
        }
        return auth_code;
    }

    @Override
    public AuthInfo queryAuth(String authCode) throws WxRespException {
        String url = String.format(WxApiUrl.COMPONENT_API, "api_query_auth", getComponentToken());
        String result = "";
        String data = "{" +
                "\"component_appid\":\"" + mpAct.getAppId() + "\" ," +
                "\" authorization_code\": \"" + authCode + "\"" +
                "}";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, data);
        } catch (IOException e) {
            log.error("换取授权公众号信息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
            throw new WxRespException(result);
        }

        AuthInfo tmp = JSON.parseObject(result, AuthInfo.class);
        return tmp;
    }

    @Override
    public String getAuthAccessToken(String authAppId, String authRefreshToken) throws WxRespException {
        return null;
    }

    @Override
    public void refreshAuthAccessToken(String authAppId, String authRefreshToken) throws WxRespException {

    }

    @Override
    public AuthInfo getAuthorizerInfo(String authAppId) throws WxRespException {
        return null;
    }

    @Override
    public String getAuthorizerOption(String authAppId, String optionName) throws WxRespException {
        String url = String.format(WxApiUrl.COMPONENT_API, "api_get_authorizer_option", getComponentToken());
        String result = "";
        String data = "{" +
                "\"component_appid\":\""+mpAct.getAppId()+"\"," +
                "\"authorizer_appid\": \""+authAppId+"\"," +
                "\"option_name\": \""+optionName+"\"" +
                "}";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, data);
        } catch (IOException e) {
            log.error("获取授权公众号[{}]的选项值时出现异常!!!",authAppId);
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
            throw new WxRespException(result);
        }

        JSONObject tmp = JSON.parseObject(result);
        String option_value = tmp.getString("option_value");
        if (log.isInfoEnabled()) {
            String info = "获取授权公众号["+authAppId+"]";
            switch (optionName) {
                case "location_report":
                    info += "地理位置上报选项成功,当前状态为: ";
                    if (option_value.equals("0")) {
                        info += "无上报";
                    } else if (option_value.equals("1")) {
                        info += "进入会话时上报";
                    } else {
                        info += "每5s上报";
                    }
                    break;
                case "voice_recognize":
                    info += "语音识别选项成功,当前状态为: ";
                    info += (option_value.equals("0")?"关闭":"开启");
                    break;
                case "customer_service":
                    info += "多客服选项成功,当前状态为: ";
                    info += (option_value.equals("0")?"关闭":"开启");
                    break;
                default:
                    break;
            }
            log.info("{}",info);
        }

        return result;
    }

    @Override
    public void setAuthorizerOption(String authAppId, String optionName, String optionValue) throws WxRespException {
        String url = String.format(WxApiUrl.COMPONENT_API, "api_set_authorizer_option", getComponentToken());
        String result = "";
        String data = "{" +
                "\"component_appid\":\""+mpAct.getAppId()+"\"," +
                "\"authorizer_appid\": \""+authAppId+"\"," +
                "\"option_name\": \""+optionName+"\"," +
                "\"option_value\":\""+optionValue+"\"" +
                "}";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, data);
        } catch (IOException e) {
            log.error("设置授权公众号[{}]的选项值时出现异常!!!",authAppId);
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || !result.contains("ok")) {
            throw new WxRespException(result);
        }

        if (log.isInfoEnabled()) {
            String info = "设置授权公众号["+authAppId+"]";
            switch (optionName) {
                case "location_report":
                    info += "地理位置上报选项成功,当前状态为: ";
                    if (optionValue.equals("0")) {
                        info += "无上报";
                    } else if (optionValue.equals("1")) {
                        info += "进入会话时上报";
                    } else {
                        info += "每5s上报";
                    }
                    break;
                case "voice_recognize":
                    info += "语音识别选项成功,当前状态为: ";
                    info += (optionValue.equals("0")?"关闭":"开启");
                    break;
                case "customer_service":
                    info += "多客服选项成功,当前状态为: ";
                    info += (optionValue.equals("0")?"关闭":"开启");
                    break;
                default:
                    break;
            }
            log.info("{}",info);
        }
    }
}
