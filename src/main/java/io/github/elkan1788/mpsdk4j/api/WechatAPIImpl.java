package io.github.elkan1788.mpsdk4j.api;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.castor.Castors;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
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
import io.github.elkan1788.mpsdk4j.vo.api.Groups;
import io.github.elkan1788.mpsdk4j.vo.api.Media;
import io.github.elkan1788.mpsdk4j.vo.api.Menu;
import io.github.elkan1788.mpsdk4j.vo.api.QRTicket;

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
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                at = Json.fromJson(AccessToken.class, ar.getJson());
                _atmc.set(mpAct.getMpId(), at);
            }

            if (at != null && at.isAvailable()) {
                return;
            }

            log.errorf("Get mp[%s]access_token failed. There try %d items.",
                       mpAct.getMpId(),
                       i + 1);

        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
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
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                return Json.fromJsonAsList(String.class, Json.toJson(ar.get("ip_list")));
            }

            log.errorf("Get mp[%s] server ips failed. There try %d items.", mpAct.getMpId(), i + 1);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public List<Menu> getMenu() {
        String url = mergeUrl(query_menu + getAccessToken());
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                Map<String, Object> button = Json.fromJson(Map.class, Json.toJson(ar.get("menu")));
                return Json.fromJsonAsList(Menu.class, Json.toJson(button.get("button")));
            }

            // 菜单为空
            if (ar.getErrCode().intValue() == 46003) {
                return null;
            }

            log.errorf("Get mp[%s] custom menu failed. There try %d items.",
                       mpAct.getAppId(),
                       i + 1);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean createMenu(Menu... menu) {
        String url = mergeUrl(create_menu + getAccessToken());
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("button", menu);
        String data = Json.toJson(body, JsonFormat.compact());
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Create mp[%s] custom menu failed. There try %d items.",
                       mpAct.getAppId(),
                       i + 1);

        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean delMenu() {
        String url = mergeUrl(del_menu + getAccessToken());
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Delete mp[%s] custom menu failed. There try %d items.",
                       mpAct.getMpId(),
                       i + 1);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public Media upMedia(String type, File media) {
        String url = mergeUrl(upload_media, getAccessToken(), type);
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.upload(url, media));
            if (ar.isSuccess()) {
                return Json.fromJson(Media.class, ar.getJson());
            }

            log.errorf("Upload mp[%s] media failed. There try %d items.", mpAct.getMpId(), i + 1);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public File dlMedia(String mediaId) {
        String url = mergeUrl(get_media, getAccessToken(), mediaId);
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            Object tmp = HttpTool.download(url);
            if (tmp instanceof File) {
                return (File) tmp;
            }

            ar = ApiResult.create((String) tmp);
            log.errorf("Download mp[%s] media failed: %s", mpAct.getMpId(), ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public int createGroup(String name) {
        String url = mergeUrl(create_groups + getAccessToken());
        String data = "{\"group\":{\"name\":\"" + name + "\"}}";
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                Groups g = Json.fromJson(Groups.class, Json.toJson(ar.get("group")));
                return g.getId();
            }

            log.errorf("Create mp[%s] group name[%s] failed: %s",
                       mpAct.getMpId(),
                       name,
                       ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public List<Groups> getGroups() {
        String url = mergeUrl(get_groups + getAccessToken());
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                return Json.fromJsonAsList(Groups.class, Json.toJson(ar.get("groups")));
            }

            log.errorf("Get mp[%s] groups failed: %s", mpAct.getMpId(), ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public int getGroup(String openId) {
        String url = mergeUrl(get_member_group + getAccessToken());
        String data = "{\"openid\":\"" + openId + "\"}";
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return Integer.parseInt(String.valueOf(ar.get("groupid")));
            }

            log.errorf("Get mp[%s] openId[%s] groups failed: %s",
                       mpAct.getMpId(),
                       openId,
                       ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean renGroups(int id, String name) {
        String url = mergeUrl(update_group + getAccessToken());
        String data = "{\"group\":{\"id\":" + id + ",\"name\":\"" + name + "\"}}";
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Rename mp[%s] groups[%d-%s] failed: %s",
                       mpAct.getMpId(),
                       id,
                       name,
                       ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean move2Group(String openId, int groupId) {
        String url = mergeUrl(update_member_group + getAccessToken());
        String data = "{\"openid\":\"" + openId + "\",\"to_groupid\":" + groupId + "}";
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Move mp[%s] openId[%s] to groups[%d] failed: %s",
                       mpAct.getMpId(),
                       openId,
                       groupId,
                       ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean batchMove2Group(Collection<String> openIds, int groupId) {
        String url = mergeUrl(update_members_group + getAccessToken());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("openid_list", Json.toJson(openIds));
        data.put("to_groupid", groupId);
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, Json.toJson(data, JsonFormat.compact())));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Move mp[%s] openIds to groups[%d] failed: %s",
                       mpAct.getMpId(),
                       groupId,
                       ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean delGroup(int id) {
        String url = mergeUrl(delete_groups + getAccessToken());
        String data = "{\"group\":{\"id\":" + id + "}}";
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Delete mp[%s] groups[%d] failed: %s", mpAct.getMpId(), id, ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public QRTicket createQR(Object sceneId, int expireSeconds) {
        String url = mergeUrl(create_qrcode + getAccessToken());
        ApiResult ar = null;
        NutMap data = new NutMap();
        NutMap scene;
        // 临时二维码
        if (expireSeconds > 0) {
            data.put("action_name", "QR_SCENE");
            data.put("expire_seconds", expireSeconds);

            scene = Lang.map("scene_id", Castors.me().castTo(sceneId, Integer.class));
        }
        // 永久二维码
        else if (sceneId instanceof Number) {
            data.put("action_name", "QR_LIMIT_SCENE");
            scene = Lang.map("scene_id", Castors.me().castTo(sceneId, Integer.class));
        }
        // 永久字符串二维码
        else {
            data.put("action_name", "QR_LIMIT_STR_SCENE");
            scene = Lang.map("scene_str", sceneId.toString());
        }
        data.put("action_info", Lang.map("scene", scene));
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, Json.toJson(data, JsonFormat.compact())));
            if (ar.isSuccess()) {
                return Json.fromJson(QRTicket.class, Json.toJson(ar.getContent()));
            }
        }
        return null;
    }

    @Override
    public File getQR(String ticket) {
        String url = mergeUrl(show_qrcode + ticket);
        ApiResult ar = null;
        for (int i = 0; i < Constants.RETRY_COUNT; i++) {
            Object tmp = HttpTool.get(url);
            if (tmp instanceof File) {
                return (File) tmp;
            }

            ar = ApiResult.create((String) tmp);
            log.errorf("Download mp[%s] qrcode image failed: %s", mpAct.getMpId(), ar.getJson());
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

}
