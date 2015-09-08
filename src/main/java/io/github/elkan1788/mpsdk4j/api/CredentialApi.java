package io.github.elkan1788.mpsdk4j.api;

import java.util.List;

import org.nutz.castor.Castors;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.common.Constants;
import io.github.elkan1788.mpsdk4j.session.AccessTokenMemoryCache;
import io.github.elkan1788.mpsdk4j.util.HttpTool;
import io.github.elkan1788.mpsdk4j.vo.ApiResult;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;
import io.github.elkan1788.mpsdk4j.vo.credential.AccessToken;

/**
 * 微信凭据授权接口
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public class CredentialApi {

    private static final Log log = Logs.get();

    // 获取access_token地址
    static String at_url = Constants.WX_API
                           + "/token?grant_type=client_credential&appid=%s&secret=%s";
    // 获取微信服务器IP地址
    static String ips_url = Constants.WX_API + "/getcallbackip?access_token=";

    static AccessTokenMemoryCache _atc = new AccessTokenMemoryCache();

    private MPAccount mpAct;

    private CredentialApi(MPAccount mpAct) {
        this.mpAct = mpAct;
    }

    public static CredentialApi create(MPAccount mpAct) {
        return new CredentialApi(mpAct);
    }

    private synchronized void refreshAccessToken() {
        at_url = String.format(at_url, mpAct.getAppId(), mpAct.getAppSecret());
        AccessToken at = null;
        for (int i = 0; i < Constants.TRY_COUNT; i++) {
            ApiResult ar = ApiResult.create(HttpTool.get(at_url));
            if (ar.isSuccess()) {
                at = Json.fromJson(AccessToken.class, ar.getJson());
            }

            if (at != null && at.isAvailable()) {
                break;
            }

            if (log.isInfoEnabled()) {
                log.infof("Get access_token url: %s", at_url);
                log.infof("Get access_token failed. There try %d items.", i + 1);
            }
        }

        _atc.set(mpAct.getMpId(), at);
    }

    public String getAccessToken() {
        AccessToken at = _atc.get(mpAct.getMpId());
        if (at == null || !at.isAvailable()) {
            refreshAccessToken();
            at = _atc.get(mpAct.getMpId());
        }
        return at.getAccessToken();
    }

    public List<String> getServerIps() {
        ips_url += getAccessToken();
        for (int i = 0; i < Constants.TRY_COUNT; i++) {
            ApiResult ar = ApiResult.create(HttpTool.get(ips_url));
            if (ar.isSuccess()) {
                return Castors.me().castTo(ar.getContent().get("ip_list"), List.class);
            }
        }

        return null;
    }
}
