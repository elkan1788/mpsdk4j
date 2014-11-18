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
	 * 凭证有效时间
	 */
	private long expiresIn;

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

    public void accessToken(String msg) {
        JSONObject tmp = JSON.parseObject(msg);
        setAccessToken(tmp.getString("access_token"));
        setExpiresIn((tmp.getLong("expires_in") - 60)* 1000);
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
                '}';
    }
}
