package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.core.JsonMsgBuilder;
import io.github.elkan1788.mpsdk4j.exception.WechatAPIException;
import io.github.elkan1788.mpsdk4j.session.AccessTokenMemoryCache;
import io.github.elkan1788.mpsdk4j.session.JSTicketMemoryCache;
import io.github.elkan1788.mpsdk4j.session.MemoryCache;
import io.github.elkan1788.mpsdk4j.session.WebOauth2TokenMemoryCache;
import io.github.elkan1788.mpsdk4j.util.HttpTool;
import io.github.elkan1788.mpsdk4j.vo.APIResult;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;
import io.github.elkan1788.mpsdk4j.vo.api.*;
import io.github.elkan1788.mpsdk4j.vo.message.NewsMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;
import org.nutz.castor.Castors;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信公众平台所有接口实现
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public class WechatAPIImpl implements WechatAPI {

    private static final Log log = Logs.get();

    private static int RETRY_COUNT = 3;

    // 设置HTTP请求方式
    private static int HTTP_GET = 1;
    private static int HTTP_POST = 2;
    private static int HTTP_UPLOAD = 3;
    private static int HTTP_DOWNLOAD = 4;
    private static String NONE_BODY = null;

    private static MemoryCache<AccessToken> _atmc;

    private static MemoryCache<JSTicket> _jstmc;

    private static MemoryCache<WebOauth2Result> _oath2;

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
            if(_oath2 == null) {
                _oath2 = new WebOauth2TokenMemoryCache();
            }
        }
    }

    public static MemoryCache<AccessToken> getAccessTokenCache() {
        return _atmc;
    }

    public static MemoryCache<JSTicket> getJsTicketCache() {
        return _jstmc;
    }

    /**
     * WechatAPI 实现方法
     * 
     * @param mpAct
     *            微信公众号信息{@link MPAccount}
     * @return 对应的API
     */
    public static WechatAPIImpl create(MPAccount mpAct) {
        return new WechatAPIImpl(mpAct);
    }

    private String mergeAPIUrl(String url, Object... values) {
        if (!Lang.isEmpty(values)) {
            return wechatAPIURL + String.format(url, values);
        }
        return wechatAPIURL + url;
    }

    private String mergeCgiBinUrl(String url, Object... values) {
        if (!Lang.isEmpty(values)) {
            return cgiBinURL + String.format(url, values);
        }
        return cgiBinURL + url;
    }

    /**
     * 微信API响应输出
     * @param url   地址
     * @param methodType    请求方式 1:HTTP_GET, 2:HTTP_POST
     * @param body          POST数据内容
     * @param errorMsg      错误信息
     * @param params        错误信息参数
     * @return              {@link APIResult}
     */
    protected APIResult wechatServerResponse(String url, int methodType, Object body, String errorMsg, Object... params) {
        APIResult ar = APIResult.create("{\"errCode\":0,\"errMsg\":\"OK\"}");
        for (int i=0; i < RETRY_COUNT; i++) {
            switch (methodType) {
                case 1:
                    ar = APIResult.create(HttpTool.get(url));
                    break;
                case 2:
                    ar = APIResult.create(HttpTool.post(url, (String)body));
                    break;
                case 3:
                    ar = APIResult.create(HttpTool.upload(url, (File) body));
                    break;
                case 4:
                    Object tmp = HttpTool.download(url);
                    if(tmp instanceof File) {
                        ar.getContent().put("file", tmp);
                    } else {
                        ar = APIResult.create((String) tmp);
                    }
                    break;
                default:
                    break;
            }

            if (ar != null && ar.isSuccess()) {
                return ar;
            }

            log.errorf("第%d尝试与微信服务器建立%s通讯.", (i+1), (methodType == HTTP_GET ? "HTTP GET" : "HTTP POST"));
            if (params == null){
                log.errorf(errorMsg, mpAct.getMpId());
            } else {
                Object[] args = new Object[params.length+1];
                args[0] = mpAct.getMpId();
                System.arraycopy(params, 0, args, 1, params.length);
                log.errorf(errorMsg, args);
                if (ar != null && !Lang.isEmpty(ar.getErrCNMsg())) {
                    log.errorf(ar.getErrCNMsg());
                }
            }
        }

        throw Lang.wrapThrow(new WechatAPIException(ar.getJson()));
    }

    /**
     * 强制刷新微信服务凭证
     */
    private synchronized void refreshAccessToken() {
        String url = mergeCgiBinUrl(getAccessTokenURL, mpAct.getAppId(), mpAct.getAppSecret());
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "获取公众号[%s]的access token失败.");
        _atmc.set(mpAct.getMpId(), Json.fromJson(AccessToken.class, ar.getJson()));
    }

    /**
     * 强制刷新微信JS票据
     */
    private synchronized void refreshJSTicket() {
        String url = mergeCgiBinUrl(jsTicketURL + getAccessToken());
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "获取公众号[%s]的JS SDK Ticket失败.");
        _jstmc.set(mpAct.getMpId(), Json.fromJson(JSTicket.class, ar.getJson()));
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
    public long getAccessTokenExpireTime() {
        AccessToken at = _atmc.get(mpAct.getMpId());
        if (at == null || !at.isAvailable()) {
            refreshAccessToken();
            at = _atmc.get(mpAct.getMpId());
        }
        return at.getExpiresIn();
    }

    @Override
    public List<String> getServerIps() {
        String url = mergeCgiBinUrl(callBackIPSURL + getAccessToken());
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "获取公众号[%s]服务器IP列表失败.");
        return Json.fromJsonAsList(String.class, Json.toJson(ar.get("ip_list")));
    }

    @Override
    public String getShortUrl(String longUrl) {
        String url = mergeCgiBinUrl(shortURL + getAccessToken());
        String data = "{\"action\":\"long2short\",\"long_url\":\"" + longUrl + "\"}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "获取公众号[%s]生成[%s]短链接失败.",
                longUrl);
        return String.valueOf(ar.get("shortURL"));
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
    public long getJSTicketExpireTime() {
        JSTicket jst = _jstmc.get(mpAct.getMpId());
        if (jst == null || !jst.isAvailable()) {
            refreshJSTicket();
            jst = _jstmc.get(mpAct.getMpId());
        }
        return jst.getExpiresIn();
    }

    @Override
    public List<Menu> getMenu() {
        String url = mergeCgiBinUrl(getMenuURL + getAccessToken());
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "获取公众号[%s]的自定义菜单失败.");
        // 菜单为空
        if (ar.getErrCode() != null && ar.getErrCode().intValue() == 46003) {
            return null;
        }

        Map<String, Object> button = Json.fromJson(Map.class, Json.toJson(ar.get("menu")));
        return Json.fromJsonAsList(Menu.class, Json.toJson(button.get("button")));
    }

    @Override
    public boolean createMenu(Menu... menu) {
        String url = mergeCgiBinUrl(createMenuURL + getAccessToken());
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("button", menu);
        String data = Json.toJson(body, JsonFormat.compact());
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "生成公众号[%s]的自定义菜单失败.");
        return ar.isSuccess();
    }

    @Override
    public boolean delMenu() {
        String url = mergeCgiBinUrl(delMenuURL + getAccessToken());
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "删除公众号[%s]自定义菜单失败.");
        return ar.isSuccess();
    }

    @Override
    public Media upMedia(String type, File media) {
        String url = mergeCgiBinUrl(uploadMediaURL, getAccessToken(), type);
        APIResult ar = wechatServerResponse(url,
                HTTP_UPLOAD,
                media,
                "上传公众号[%s]的多媒体文件[%s]失败.",
                media.getName());
        return Json.fromJson(Media.class, ar.getJson());
    }

    @Override
    public File dlMedia(String mediaId) {
        String url = mergeCgiBinUrl(getMediaURL, getAccessToken(), mediaId);
        APIResult ar = wechatServerResponse(url,
                HTTP_DOWNLOAD,
                NONE_BODY,
                "下载公众号[%s]的多媒体文件[%s]失败.",
                mediaId);
        return (File) ar.get("file");
    }

    @Override
    public int createGroup(String name) {
        String url = mergeCgiBinUrl(createGroupsURL + getAccessToken());
        String data = "{\"group\":{\"name\":\"" + name + "\"}}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "创建公众号[%s]的新分组[%s]失败.",
                name);
        Groups g = Json.fromJson(Groups.class, Json.toJson(ar.get("group")));
        return g.getId();
    }

    @Override
    public List<Groups> getGroups() {
        String url = mergeCgiBinUrl(getGroupsURL + getAccessToken());
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "获取公众号[%s]的所有分组信息失败.");
        return Json.fromJsonAsList(Groups.class, Json.toJson(ar.get("groups")));
    }

    @Override
    public int getGroup(String openId) {
        String url = mergeCgiBinUrl(getMemberGroupURL + getAccessToken());
        String data = "{\"openid\":\"" + openId + "\"}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "获取公众号[%s]用户[%s]所在分组ID失败.",
                openId);
        return Integer.parseInt(String.valueOf(ar.get("groupid")));
    }

    @Override
    public boolean renGroups(int id, String name) {
        String url = mergeCgiBinUrl(updateGroupURL + getAccessToken());
        String data = "{\"group\":{\"id\":" + id + ",\"name\":\"" + name + "\"}}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "重新命名公众号[%s]分组[%d-%s]的名称失败.",
                id,
                name);
        return ar.isSuccess();
    }

    @Override
    public boolean move2Group(String openId, int groupId) {
        String url = mergeCgiBinUrl(updateMemberGroupURL + getAccessToken());
        String data = "{\"openid\":\"" + openId + "\",\"to_groupid\":" + groupId + "}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "移动公众号[%s]用户[%s]到新分组[%d]失败.",
                openId,
                groupId);
        return ar.isSuccess();
    }

    @Override
    public boolean batchMove2Group(Collection<String> openIds, int groupId) {
        String url = mergeCgiBinUrl(updateMembersGroupURL + getAccessToken());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("openid_list", Json.toJson(openIds));
        data.put("to_groupid", groupId);
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                Json.toJson(data, JsonFormat.compact()),
                "批量移动公众号[%s]的%d个用户到新分组[%d]失败.",
                openIds.size(),
                groupId);
        return ar.isSuccess();
    }

    @Override
    public boolean delGroup(int id) {
        String url = mergeCgiBinUrl(deleteGroupsURL + getAccessToken());
        String data = "{\"group\":{\"id\":" + id + "}}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "删除公众号[%s]的分组[%d]信息失败.",
                id);
        return ar.isSuccess();
    }

    @Override
    public QRTicket createQR(Object sceneId, int expireSeconds) {
        String url = mergeCgiBinUrl(createQRCodeURL + getAccessToken());
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
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                Json.toJson(data, JsonFormat.compact()),
                "创建公众号[%s]的[%s]场景二维码失败.");
        return Json.fromJson(QRTicket.class, Json.toJson(ar.getContent()));
    }

    @Override
    public File getQR(String ticket) {
        String url = mergeCgiBinUrl(showQRCodeURL + ticket);
        APIResult ar = wechatServerResponse(url,
                HTTP_DOWNLOAD,
                NONE_BODY,
                "下载公众号[%s]的二维码[%s]失败.",
                ticket);
        return (File) ar.get("file");
    }

    @Override
    public boolean updateRemark(String openId, String remark) {
        String url = mergeCgiBinUrl(userRemarkURL + getAccessToken());
        String data = "{\"openid\":\"" + openId + "\",\"remark\":\"" + remark + "\"}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "更新公众号[%s]用户[%s]备注[%s]信息失败.",
                openId,
                remark);
        return ar.isSuccess();
    }

    @Override
    public FollowList getFollowerList(String nextOpenId) {
        String url = mergeCgiBinUrl(userListURL, getAccessToken(), Strings.sNull(nextOpenId, ""));
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "拉取公众号[%s]的关注用户列表信息失败.",
                Lang.isEmpty(nextOpenId) ? "" : "[以用户"+nextOpenId+"开始]");
        FollowList fl = Json.fromJson(FollowList.class, ar.getJson());
        Map<String, Object> openid = (Map<String, Object>) ar.get("data");
        fl.setOpenIds(Json.fromJson(List.class, Json.toJson(openid.get("openid"))));
        return fl;
    }

    @Override
    public Follower getFollower(String openId, String lang) {
        lang = Strings.sBlank(lang, "zh_CN");
        String url = mergeCgiBinUrl(userInfoURL, getAccessToken(), openId, lang);
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "获取公众号[%s]关注用户[%s-%s]信息失败.",
                openId,
                lang);
        return Json.fromJson(Follower.class, ar.getJson());
    }

    @Override
    public List<Follower> getFollowers(Collection<Follower2> users) {
        String url = mergeCgiBinUrl(batchUserInfoURL + getAccessToken());
        String data = Json.toJson(Lang.map("user_list", users), JsonFormat.compact());
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "批量获取公众号[%s]的%d个用户信息失败.",
                users.size());
        return Json.fromJsonAsList(Follower.class, Json.toJson(ar.get("user_info_list")));
    }

    @Override
    public boolean setIndustry(int id1, int id2) {
        String url = mergeCgiBinUrl(setIndustryURL + getAccessToken());
        String data = "{\"industry_id1\":\"" + id1 + "\",\"industry_id2\":\"" + id2 + "\"}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "设置公众号[%s]的模板新属性[%d-%d]信息失败.",
                id1,
                id2);
        return ar.isSuccess();
    }

    @Override
    public String getTemplateId(String tmlShortId) {
        String url = mergeCgiBinUrl(addTemplateURL + getAccessToken());
        String data = "{\"template_id_short\":\"" + tmlShortId + "\"}";
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "获取公众号[%s]的模板ID[%s]失败.",
                tmlShortId);
        return String.valueOf(ar.get("template_id"));
    }

    @Override
    public long sendTemplateMsg(String openId,
                                String tmlId,
                                String topColor,
                                String targetUrl,
                                Template... tmls) {
        String url = mergeCgiBinUrl(sendTemplateURL + getAccessToken());
        String data = JsonMsgBuilder.create().template(openId, tmlId, topColor, targetUrl, tmls).build();
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "公众号[%s]给用户[%s]发送模板[%s]消息失败.",
                openId,
                tmlId);
        return Long.valueOf(ar.get("msgid").toString());
    }

    @Override
    public boolean sendTextMsg(TextMsg textMsg) {
        String url = mergeCgiBinUrl(sendCustomURL +  getAccessToken());
        String data = JsonMsgBuilder.create().text(textMsg).build();
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "公众号[%s]给用户[%s]发送文本客服消息失败.",
                textMsg.getFromUserName());
        return ar.isSuccess();
    }

    @Override
    public boolean sendNewsMsg(NewsMsg newsMsg) {
        String url = mergeCgiBinUrl(sendCustomURL +  getAccessToken());
        String data = JsonMsgBuilder.create().news(newsMsg).build();
        APIResult ar = wechatServerResponse(url,
                HTTP_POST,
                data,
                "公众号[%s]给用户[%s]发送图文客服消息失败.",
                newsMsg.getFromUserName());
        return ar.isSuccess();
    }

    @Override
    public WebOauth2Result getWebOauth2Result(String authCode) {
        String url = mergeAPIUrl(oauth2URL, mpAct.getAppId(), mpAct.getAppSecret(), authCode);
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                "换取公众号[%s]授权码[%s]凭证信息失败.",
                authCode);
        WebOauth2Result result = Json.fromJson(WebOauth2Result.class, ar.getJson());
        _oath2.set(result.getOpenId(), result);
        return result;
    }

    protected WebOauth2Result refreshWebOauth2Result(String refreshToken) {
        String url = mergeAPIUrl(refreshTokenURL, mpAct.getAppId(), refreshToken);
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "刷新公众号[%s]的网页授权[%s]凭证信息失败.",
                refreshToken);
        WebOauth2Result result = Json.fromJson(WebOauth2Result.class, ar.getJson());
        _oath2.set(result.getOpenId(), result);
        return result;
    }

    @Override
    public Follower getWebOauth2User(String openId, String lang) {
        lang = Strings.sBlank(lang, "zh_CN");
        WebOauth2Result result = _oath2.get(openId);
        if (result == null){
           throw Lang.wrapThrow(new WechatAPIException("用户未授权"));
        } else if (!result.isAvailable()){
            result = refreshWebOauth2Result(result.getRefreshToken());
        }

        String url = mergeAPIUrl(oauth2UserURL, result.getAccessToken(), openId, lang);
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "以网页授权方式获取公众号[%s]的用户[%s-%s]信息失败.",
                openId,
                lang);
        return Json.fromJson(Follower.class, ar.getJson());
    }

    @Override
    public boolean checkWebOauth2Token(String accessToken, String openId) {
        String url = mergeAPIUrl(checkTokenURL, accessToken, openId);
        APIResult ar = wechatServerResponse(url,
                HTTP_GET,
                NONE_BODY,
                "验证公众号[%s]用户[%s]授权凭证[%s]信息失败.",
                openId,
                accessToken);
        return ar.isSuccess();
    }
}
