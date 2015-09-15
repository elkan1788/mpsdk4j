/**
 * @author senhui.li
 */
package io.github.elkan1788.mpsdk4j.vo.api;

/**
 * 批量获取用户信息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class Follower2 {

    /**
     * 用户的标识
     */
    private String openid;

    /**
     * 国家地区语言版本,zh_CN 简体,zh_TW 繁体,en 英语,默认为zh-CN
     */
    private String lang;

    public Follower2() {}

    public Follower2(String openid) {
        this(openid, "zh_CN");
    }

    public Follower2(String openid, String lang) {
        this.openid = openid;
        this.lang = lang;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Follower2 [openid=" + openid + ", lang=" + lang + "]";
    }

}
