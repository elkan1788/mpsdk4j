package io.github.elkan1788.mpsdk4j.api;

import java.util.List;

/**
 * 微信凭据授权接口
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public interface CredentialAPI {

    // 获取access_token地址
    static String get_at = "/token?grant_type=client_credential&appid=%s&secret=%s";
    // 获取微信服务器IP地址
    static String cb_ips = "/getcallbackip?access_token=";

    /**
     * 获取微信服务凭证
     * 
     * @return 凭证
     */
    String getAccessToken();

    /**
     * 获取微信服务器IP地址
     * 
     * @return IP地址
     */
    List<String> getServerIps();
}
