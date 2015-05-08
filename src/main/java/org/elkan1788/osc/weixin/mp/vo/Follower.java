package org.elkan1788.osc.weixin.mp.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信订阅者信息
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @since 2014/11/8
 * @version 1.0.0
 */
public class Follower {

    /**
     * 是否订阅(0 退订, 1 订阅)
     */
	private int subscribe;

    /**
     * 关注公众号唯一标识
     */
	private String openId;

    /**
     * 微信昵称
     */
	private String nickName;

    /**
     * 性别(1 男, 2 女, 0 未知)
     */
	private int sex;

    /**
     * 用户所在城市
     */
	private String city;

    /**
     * 用户所在国家
     */
	private String country;

    /**
     * 用户所在省份
     */
	private String province;

    /**
     * 用户的语言，简体中文为zh_CN
     */
	private String language;

    /**
     * 用户头像，最后一个数值代表正方形头像大小<br/>
     * （有0、46、64、96、132数值可选，0代表640*640正方<br/>
     * 形头像），用户没有头像时该项为空
     */
	private String headImgUrl;

    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    @JSONField(name = "subscribe_time")
	private long subscribeTime;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     */
	private String unionid;

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "Follower{" +
                "subscribe=" + subscribe +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", language='" + language + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
