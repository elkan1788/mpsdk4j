package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.core.JsonMsgBuilder;
import io.github.elkan1788.mpsdk4j.exception.WechatApiException;
import io.github.elkan1788.mpsdk4j.session.AccessTokenMemoryCache;
import io.github.elkan1788.mpsdk4j.session.JSTicketMemoryCache;
import io.github.elkan1788.mpsdk4j.session.MemoryCache;
import io.github.elkan1788.mpsdk4j.util.HttpTool;
import io.github.elkan1788.mpsdk4j.vo.ApiResult;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;
import io.github.elkan1788.mpsdk4j.vo.api.AccessToken;
import io.github.elkan1788.mpsdk4j.vo.api.FollowList;
import io.github.elkan1788.mpsdk4j.vo.api.Follower;
import io.github.elkan1788.mpsdk4j.vo.api.Follower2;
import io.github.elkan1788.mpsdk4j.vo.api.Groups;
import io.github.elkan1788.mpsdk4j.vo.api.JSTicket;
import io.github.elkan1788.mpsdk4j.vo.api.Media;
import io.github.elkan1788.mpsdk4j.vo.api.Menu;
import io.github.elkan1788.mpsdk4j.vo.api.QRTicket;
import io.github.elkan1788.mpsdk4j.vo.api.Template;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.castor.Castors;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 微信公众平台所有接口实现
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public class WechatAPIImpl implements WechatAPI {

    private static final Log log = Logs.get();

    static int RETRY_COUNT = 3;

    static MemoryCache<AccessToken> _atmc;

    static MemoryCache<JSTicket> _jstmc;

    private MPAccount mpAct;

    public WechatAPIImpl(MPAccount mpAct) {
        this.mpAct = mpAct;
        synchronized (this) {
            if (_atmc == null) {
                _atmc = new AccessTokenMemoryCache();
            }
            if (_jstmc == null) {
                _jstmc = new JSTicketMemoryCache();
            }
        }
    }

    /**
     * WechatAPI 实现方法
     * 
     * @param mpAct
     *            微信公众号信息{@link MPAccount}
     * @return 对应的API
     */
    public static WechatAPI create(MPAccount mpAct) {
        return new WechatAPIImpl(mpAct);
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
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                at = Json.fromJson(AccessToken.class, ar.getJson());
                _atmc.set(mpAct.getMpId(), at);
            }

            if (at != null && at.isAvailable()) {
                return;
            }

            log.errorf("Get mp[%s]access_token failed. There try %d items.", mpAct.getMpId(), i + 1);

        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    private synchronized void refreshJSTicket() {
        String url = mergeUrl(js_ticket + getAccessToken());
        JSTicket jst = null;
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                jst = Json.fromJson(JSTicket.class, ar.getJson());
                _jstmc.set(mpAct.getMpId(), jst);
            }

            if (jst != null && jst.isAvailable()) {
                return;
            }

            log.errorf("Get mp[%s] JSSDK ticket failed. There try %d items.",
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
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                return Json.fromJsonAsList(String.class, Json.toJson(ar.get("ip_list")));
            }

            log.errorf("Get mp[%s] server ips failed. There try %d items.", mpAct.getMpId(), i + 1);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public String getShortUrl(String longUrl) {
        String url = mergeUrl(short_url + getAccessToken());
        String data = "{\"action\":\"long2short\",\"long_url\":\"" + longUrl + "\"}";
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return String.valueOf(ar.get("short_url"));
            }

            log.errorf("Create mp[%p] short url failed. There try %d items.", mpAct.getMpId(), i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public String getJSTicket() {
        JSTicket jst = _jstmc.get(mpAct.getMpId());
        if (jst == null || !jst.isAvailable()) {
            refreshJSTicket();
            jst = _jstmc.get(mpAct.getMpId());
        }
        return jst.getTicket();
    }

    @Override
    public List<Menu> getMenu() {
        String url = mergeUrl(query_menu + getAccessToken());
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
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
        for (int i = 0; i < RETRY_COUNT; i++) {
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
        for (int i = 0; i < RETRY_COUNT; i++) {
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
        for (int i = 0; i < RETRY_COUNT; i++) {
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
        for (int i = 0; i < RETRY_COUNT; i++) {
            Object tmp = HttpTool.download(url);
            if (tmp instanceof File) {
                return (File) tmp;
            }

            ar = ApiResult.create((String) tmp);
            log.errorf("Download mp[%s] media failed. There try %d items.", mpAct.getMpId(), i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public int createGroup(String name) {
        String url = mergeUrl(create_groups + getAccessToken());
        String data = "{\"group\":{\"name\":\"" + name + "\"}}";
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                Groups g = Json.fromJson(Groups.class, Json.toJson(ar.get("group")));
                return g.getId();
            }

            log.errorf("Create mp[%s] group name[%s] failed. There try %d items.",
                       mpAct.getMpId(),
                       name,
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public List<Groups> getGroups() {
        String url = mergeUrl(get_groups + getAccessToken());
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                return Json.fromJsonAsList(Groups.class, Json.toJson(ar.get("groups")));
            }

            log.errorf("Get mp[%s] groups failed. There try %d items.", mpAct.getMpId(), i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public int getGroup(String openId) {
        String url = mergeUrl(get_member_group + getAccessToken());
        String data = "{\"openid\":\"" + openId + "\"}";
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return Integer.parseInt(String.valueOf(ar.get("groupid")));
            }

            log.errorf("Get mp[%s] openId[%s] groups failed. There try %d items.",
                       mpAct.getMpId(),
                       openId,
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean renGroups(int id, String name) {
        String url = mergeUrl(update_group + getAccessToken());
        String data = "{\"group\":{\"id\":" + id + ",\"name\":\"" + name + "\"}}";
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Rename mp[%s] groups[%d-%s] failed. There try %d items.",
                       mpAct.getMpId(),
                       id,
                       name,
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean move2Group(String openId, int groupId) {
        String url = mergeUrl(update_member_group + getAccessToken());
        String data = "{\"openid\":\"" + openId + "\",\"to_groupid\":" + groupId + "}";
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Move mp[%s] openId[%s] to groups[%d] failed. There try %d items.",
                       mpAct.getMpId(),
                       openId,
                       groupId,
                       i);
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
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, Json.toJson(data, JsonFormat.compact())));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Move mp[%s] openIds to groups[%d] failed. There try %d items.",
                       mpAct.getMpId(),
                       groupId,
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean delGroup(int id) {
        String url = mergeUrl(delete_groups + getAccessToken());
        String data = "{\"group\":{\"id\":" + id + "}}";
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Delete mp[%s] groups[%d] failed. There try %d items.",
                       mpAct.getMpId(),
                       id,
                       i);
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
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, Json.toJson(data, JsonFormat.compact())));
            if (ar.isSuccess()) {
                return Json.fromJson(QRTicket.class, Json.toJson(ar.getContent()));
            }

            log.infof("Create mp[%s] scene[%s] qrcode failed. There try %d items.",
                      mpAct.getMpId(),
                      data.get("action_name"),
                      i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public File getQR(String ticket) {
        String url = mergeUrl(show_qrcode + ticket);
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            Object tmp = HttpTool.get(url);
            if (tmp instanceof File) {
                return (File) tmp;
            }

            ar = ApiResult.create((String) tmp);
            log.errorf("Download mp[%s] qrcode image failed. There try %d items.",
                       mpAct.getMpId(),
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean updateRemark(String openId, String remark) {
        String url = mergeUrl(user_remark + getAccessToken());
        ApiResult ar = null;
        String data = "{\"openid\":\"" + openId + "\",\"remark\":\"" + remark + "\"}";
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Update mp[%s] user[%d] remark[%s] failed. There try %d items.",
                       mpAct.getMpId(),
                       openId,
                       remark,
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public FollowList getFollowerList(String nextOpenId) {
        String url = mergeUrl(user_list, getAccessToken(), Strings.sNull(nextOpenId, ""));
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                FollowList fl = Json.fromJson(FollowList.class, ar.getJson());
                Map<String, Object> openid = (Map<String, Object>) ar.get("data");
                fl.setOpenIds(Json.fromJson(List.class, Json.toJson(openid.get("openid"))));
                return fl;
            }

            log.infof("Get mp[%s] follow list failed. There try %d items.", mpAct.getMpId(), i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public Follower getFollower(String openId, String lang) {
        String url = mergeUrl(user_info, getAccessToken(), openId, Strings.sNull(lang, "zh_CN"));
        ApiResult ar = null;
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.get(url));
            if (ar.isSuccess()) {
                return Json.fromJson(Follower.class, ar.getJson());
            }

            log.errorf("Get mp[%s] follower[%s] information failed. There try %d items.",
                       mpAct.getMpId(),
                       openId,
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public List<Follower> getFollowers(Collection<Follower2> users) {
        String url = mergeUrl(batch_user_info + getAccessToken());
        ApiResult ar = null;
        String data = Json.toJson(Lang.map("user_list", users), JsonFormat.compact());
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return Json.fromJsonAsList(Follower.class, Json.toJson(ar.get("user_info_list")));
            }

            log.errorf("Get mp[%s] followers information failed. There try %d items.",
                       mpAct.getMpId(),
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public boolean setIndustry(int id1, int id2) {
        String url = mergeUrl(set_industry + getAccessToken());
        ApiResult ar = null;
        String data = "{\"industry_id1\":\"" + id1 + "\",\"industry_id2\":\"" + id2 + "\"}";
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return true;
            }

            log.errorf("Set mp[%s] template industry failed. There try %d items.",
                       mpAct.getMpId(),
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public String getTemplateId(String tmlShortId) {
        String url = mergeUrl(add_template + getAccessToken());
        ApiResult ar = null;
        String data = "{\"template_id_short\":\"" + tmlShortId + "\"}";
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(url, data));
            if (ar.isSuccess()) {
                return String.valueOf(ar.get("template_id"));
            }

            log.errorf("Get mp[%s] template id failed. There try %d items.", mpAct.getMpId(), i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

    @Override
    public long sendTemplateMsg(String openId,
                                String tmlId,
                                String topColor,
                                String url,
                                Template... tmls) {
        String apiurl = mergeUrl(send_template + getAccessToken());
        ApiResult ar = null;
        String data = JsonMsgBuilder.create().template(openId, tmlId, topColor, url, tmls).build();
        for (int i = 0; i < RETRY_COUNT; i++) {
            ar = ApiResult.create(HttpTool.post(apiurl, data));
            if (ar.isSuccess()) {
                return Long.valueOf(ar.get("msgid").toString());
            }

            log.errorf("Send mp[%s] template message failed. There try %d items.",
                       mpAct.getMpId(),
                       i);
        }

        throw Lang.wrapThrow(new WechatApiException(ar.getJson()));
    }

}
