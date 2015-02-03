package org.elkan1788.osc.weixin.mp.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信公众号信息
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @since 2014/11/8
 * @version 1.0.2
 */
public class MPAct {

	/**
	 * 公众号原始ID
	 */
	private String mpId;

	/**
	 * 公众号昵称
	 */
	private String nickName;

	/**
	 * 应用Id
	 */
	private String appId;

	/**
	 * 应用密钥
	 */
	private String appSecert;

    /**
     * 令牌
     */
    private String token;

	/**
	 * AES安全加密密钥
	 */
	private String AESKey;

	/**
	 * 公众号类型
	 * D:订阅号
	 * E:企业号
	 * S:服务号
	 * 
	 */
	private String mpType;

	/**
	 * 是否认证
	 */
	private boolean pass;

	/**
	 * 应用凭证
	 */
	private String accessToken;

	/**
	 * 凭证有效时间(秒)
	 */
	private long expiresIn;

    /**
     * 预授权码
     */
    private String preAuthCode;

    /**
     * 预授权码有效时间(秒)
     */
    private long preAuthExpiresIn;

    /**
     * JSAPI凭证
     */
    private String jsTicket;

    /**
     * JSAPI凭证有效时间(秒)
     */
    private long jsExpiresIn;

    public String getMpId() {
        return mpId;
    }

    public void setMpId(String mpId) {
        this.mpId = mpId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecert() {
        return appSecert;
    }

    public void setAppSecert(String appSecert) {
        this.appSecert = appSecert;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAESKey() {
        return AESKey;
    }

    public void setAESKey(String AESKey) {
        this.AESKey = AESKey;
    }

    public String getMpType() {
        return mpType;
    }

    public void setMpType(String mpType) {
        this.mpType = mpType;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * 解析微信服务器返回消息生成高级API或服务的凭证
     *
     * @param result    返回消息
     */
    public void createAccessToken(String result) {

        JSONObject tmp = JSON.parseObject(result);
        if (result.contains("access_token")) {
            setAccessToken(tmp.getString("access_token"));
        } else {
            setAccessToken(tmp.getString("component_access_token"));
        }
        long lose_time = (tmp.getLong("expires_in")-60) * 1000
                + System.currentTimeMillis();
        setExpiresIn(lose_time);
    }

    public String getPreAuthCode() {
        return preAuthCode;
    }

    public void setPreAuthCode(String preAuthCode) {
        this.preAuthCode = preAuthCode;
    }

    public long getPreAuthExpiresIn() {
        return preAuthExpiresIn;
    }

    public void setPreAuthExpiresIn(long preAuthExpiresIn) {
        this.preAuthExpiresIn = preAuthExpiresIn;
    }

    /**
     * 解析微信服务器返回消息生成预授权码
     *
     * @param result    返回消息
     */
    public void createPreAuthCode(String result) {
        JSONObject tmp = JSONObject.parseObject(result);
        setPreAuthCode(tmp.getString("pre_auth_code"));
        long lose_time = (tmp.getLong("expires_in")-60) * 1000
                + System.currentTimeMillis();
        setPreAuthExpiresIn(lose_time);
    }

    public String getJsTicket() {
        return jsTicket;
    }

    public void setJsTicket(String jsTicket) {
        this.jsTicket = jsTicket;
    }

    public long getJsExpiresIn() {
        return jsExpiresIn;
    }

    public void setJsExpiresIn(long jsExpiresIn) {
        this.jsExpiresIn = jsExpiresIn;
    }

    /**
     * 解析微信服务器返回消息生成JSTICKET
     *
     * @param result    返回消息
     */
    public void createJsTicket(String result) {
        JSONObject tmp = JSONObject.parseObject(result);
        setJsTicket(tmp.getString("ticket"));
        long lose_time = (tmp.getLong("expires_in")-60) * 1000
                + System.currentTimeMillis();
        setJsExpiresIn(lose_time);
    }

    @Override
    public String toString() {
        return "MPAct{" +
                "mpId='" + mpId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", appId='" + appId + '\'' +
                ", appSecert='" + appSecert + '\'' +
                ", token='" + token + '\'' +
                ", AESKey='" + AESKey + '\'' +
                ", mpType='" + mpType + '\'' +
                ", pass=" + pass +
                ", accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", preAuthCode='" + preAuthCode + '\'' +
                ", preAuthExpiresIn=" + preAuthExpiresIn +
                ", jsTicket='" + jsTicket + '\'' +
                ", jsExpiresIn=" + jsExpiresIn +
                '}';
    }
}
