package io.github.elkan1788.mpsdk4j.api;

import java.util.Map;

/**
 * 页面网页授权
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.b.2
 */
public interface WebOauth2API {

    String oauth2 = "/sns/oauth2/access_token?appid=%s&secret=%s&grant_type=authorization_code&code=%s";

    String refresh_token = "/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

    String oauth2_user = "/sns/userinfo?access_token=%s&openid=%s&lang=%s";

    Map<String, String> getWebAuth2Result(String authCode);
}
