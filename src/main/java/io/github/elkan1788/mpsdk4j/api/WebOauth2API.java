package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.vo.api.Follower;
import io.github.elkan1788.mpsdk4j.vo.api.WebOauth2Result;

/**
 * 网页授权
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.b.2
 */
public interface WebOauth2API {

    /**
     * 换取网页授权access_token地址
     */
    String oauth2URL = "/sns/oauth2/access_token?appid=%s&secret=%s&grant_type=authorization_code&code=%s";
    /**
     * 刷新access_token地址
     */
    String refreshTokenURL = "/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
    /**
     * 拉取用户信息地址
     */
    String oauth2UserURL = "/sns/userinfo?access_token=%s&openid=%s&lang=%s";
    /**
     * 检验授权凭证地址
     */
    String checkTokenURL = "/sns/auth?access_token=%s&openid=%s";

    /**
     * 获取网页授权信息
     *
     * @param authCode  授权码
     *
     * @return  授权信息 {@link io.github.elkan1788.mpsdk4j.vo.api.WebOauth2Result}
     */
    WebOauth2Result getWebOauth2Result(String authCode);

    /**
     * 获取网页授权用户信息(详细)
     *
     * @param openId    用户ID
     * @param lang    语言
     *
     * @return 用户信息 {@link io.github.elkan1788.mpsdk4j.vo.api.Follower}
     */
    Follower getWebOauth2User(String openId, String lang);

    /**
     * 检验网页授权状态
     *
     * @param accessToken   授权凭证
     * @param openId        用户ID
     * @return  授权成功返回 "true",否则返回"false"
     */
    boolean checkWebOauth2Token(String accessToken, String openId);
}
