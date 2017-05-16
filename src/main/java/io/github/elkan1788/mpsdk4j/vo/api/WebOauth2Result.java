package io.github.elkan1788.mpsdk4j.vo.api;

import org.nutz.json.JsonField;
import org.nutz.lang.Lang;

/**
 * 网页授权结果
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.b.2
 */
public class WebOauth2Result {

    /**
     * 网页授权接口调用凭证
     */
    @JsonField(value = "access_token")
    private String accessToken;
    /**
     * 凭证超时时间
     */
    @JsonField(value = "expires_in")
    private long expiresIn;
    /**
     * 用户刷新access_token
     */
    @JsonField(value = "refresh_token")
    private String refreshToken;
    /**
     * 用户唯一标识,请注意,在未关注公众号时,
     * 用户访问公众号的网页,
     * 也会产生一个用户和公众号唯一的OpenID
     */
    @JsonField(value = "openid")
    private String openId;
    /**
     * 用户授权的作用域,使用逗号(,)分隔
     */
    private String scope;

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
        // 考虑到服务器时间同步,故将刷新时间提前60秒.
        this.expiresIn = System.currentTimeMillis() + (expiresIn - 60) * 1000;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isAvailable() {
        if (!Lang.isEmpty(accessToken) && this.expiresIn >= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "WebOauth2Result [accessToken="
               + accessToken
               + ", expiresIn="
               + expiresIn
               + ", refreshToken="
               + refreshToken
               + ", openId="
               + openId
               + ", scope="
               + scope
               + "]";
    }
}
