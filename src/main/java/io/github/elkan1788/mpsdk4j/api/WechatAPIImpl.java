package io.github.elkan1788.mpsdk4j.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.castor.Castors;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.common.Constants;
import io.github.elkan1788.mpsdk4j.exception.WechatApiException;
import io.github.elkan1788.mpsdk4j.session.AccessTokenMemoryCache;
import io.github.elkan1788.mpsdk4j.session.MemoryCache;
import io.github.elkan1788.mpsdk4j.util.HttpTool;
import io.github.elkan1788.mpsdk4j.vo.ApiResult;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;
import io.github.elkan1788.mpsdk4j.vo.api.AccessToken;
import io.github.elkan1788.mpsdk4j.vo.api.Media;
import io.github.elkan1788.mpsdk4j.vo.api.Menu;

/**
 * 微信公众平台所有接口实现
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public class WechatAPIImpl implements WechatAPI {

    private static final Log log = Logs.get();

    static MemoryCache<AccessToken> _atmc;

    private MPAccount mpAct;

    public WechatAPIImpl(MPAccount mpAct) {
        this.mpAct = mpAct;
        synchronized (this) {
            if (_atmc == null) {
                _atmc = new AccessTokenMemoryCache();
            }
        }
    }

    public WechatAPIImpl(MPAccount mpAct, MemoryCache<AccessToken> atmc) {
        this.mpAct = mpAct;
        synchronized (this) {
            if (_atmc == null) {
                _atmc = atmc;
            }
        }
    }

    public static WechatAPI create(MPAccount mpAct) {
        return new WechatAPIImpl(mpAct);
    }

    public static WechatAPI create(MPAccount mpAct, MemoryCache<AccessToken> atmc) {
        return new WechatAPIImpl(mpAct, atmc);
    }

    private String mergeUrl(String url, Object... values) {
        if (!Lang.isEmpty(values)) {
            return wechatapi + String.format(url, values);
        }
        return wechatapi + url;
    }

    /**
     * 强制刷新微信服务凭证
     */
    private synchronized void refreshAccessToken() {
        String url = mergeUrl(get_at, mpAct.getAppId(), mpAct.getAppSecret());
        AccessToken at = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ApiResult ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                at = Json.fromJson(AccessToken.class, ar.getJson());
                _atmc.set(mpAct.getMpId(), at);
            }

            if (at != null && at.isAvailable()) {
                break;
            }

            if (log.isInfoEnabled()) {
                log.infof("Get mp[%s] access_token url: %s", mpAct.getMpId(), url);
                log.infof("Get mp[%s]access_token failed. There try %d items.",
                          mpAct.getMpId(),
                          i + 1);
            }

            if (i == Constants.RETRY_COUNT - 1) {
                throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
            }
        }

    }

    @Override
    public String getAccessToken() {
        AccessToken at = _atmc.get(mpAct.getMpId());
        if (at == null || !at.isAvailable()) {
            refreshAccessToken();
            at = _atmc.get(mpAct.getMpId());
        }
        return at.getAccessToken();
    }

    @Override
    public List<String> getServerIps() {
        String url = mergeUrl(cb_ips + getAccessToken());
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ApiResult ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                return Castors.me().castTo(ar.getContent().get("ip_list"), List.class);
            }

            if (log.isInfoEnabled()) {
                log.infof("Get mp[%s] server ips url: %s", mpAct.getMpId(), url);
                log.infof("Get mp[%s] server ips failed. There try %d items.",
                          mpAct.getMpId(),
                          i + 1);
            }

            if (i == Constants.RETRY_COUNT - 1) {
                throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
            }
        }

        return null;
    }

    @Override
    public List<Menu> getMenu() {
        String url = mergeUrl(query_menu + getAccessToken());
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ApiResult ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                Map<String, Object> button = Json.fromJson(Map.class,
                                                           Json.toJson(ar.getContent()
                                                                         .get("menu")));
                return Json.fromJsonAsList(Menu.class, Json.toJson(button.get("button")));
            }

            // 菜单为空
            if (ar.getErrCode().intValue() == 46003) {
                break;
            }

            if (log.isInfoEnabled()) {
                log.infof("Get mp[%s] custom menu url: %s", mpAct.getAppId(), url);
                log.infof("Get mp[%s] custom menu failed. There try %d items.",
                          mpAct.getAppId(),
                          i + 1);
            }

            if (i == Constants.RETRY_COUNT - 1) {
                throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
            }
        }
        return null;
    }

    @Override
    public boolean createMenu(Menu... menu) {
        String url = mergeUrl(create_menu + getAccessToken());
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("button", menu);
        String data = Json.toJson(body, JsonFormat.compact());
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ApiResult ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            if (log.isInfoEnabled()) {
                log.infof("Create mp[%s] custom menu url: %s", mpAct.getAppId(), url);
                log.infof("Create mp[%s] custom menu failed. There try %d items.",
                          mpAct.getAppId(),
                          i + 1);
            }

            if (i == Constants.RETRY_COUNT - 1) {
                throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
            }
        }
        return false;
    }

    @Override
    public boolean delMenu() {
        String url = mergeUrl(del_menu + getAccessToken());
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ApiResult ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                return true;
            }

            if (log.isInfoEnabled()) {
                log.infof("Delete mp[%s] custom menu url: %s", mpAct.getAppId(), url);
                log.infof("Delete mp[%s] custom menu failed. There try %d items.",
                          mpAct.getMpId(),
                          i + 1);
            }

            if (i == Constants.RETRY_COUNT - 1) {
                throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
            }
        }
        return false;
    }

    @Override
    public Media upload(String type, File media) {
        String url = mergeUrl(upload_media, getAccessToken(), type);
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ApiResult ar = ApiResult.create(HttpTool.upload(url, media));
            if (ar.isSuccess()) {
                return Json.fromJson(Media.class, ar.getJson());
            }

            if (log.isInfoEnabled()) {
                log.infof("Upload mp[%s] media url: %s", mpAct.getAppId(), url);
                log.infof("Upload mp[%s] media failed. There try %d items.",
                          mpAct.getMpId(),
                          i + 1);
            }

            if (i == Constants.RETRY_COUNT - 1) {
                throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
            }
        }
        return null;
    }

    @Override
    public File get(String mediaId) {
        String url = mergeUrl(get_media, getAccessToken(), mediaId);
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            Object tmp = HttpTool.download(url);
            if (tmp instanceof File) {
                return (File) tmp;
            }

            ApiResult ar = ApiResult.create((String) tmp);
            log.errorf("Download mp[%s] media url: %s", url);
            log.errorf("Download mp[%s] media failed: %s", ar.getJson());

            if (i == Constants.RETRY_COUNT - 1) {
                throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
            }
        }
        return null;
    }

}
