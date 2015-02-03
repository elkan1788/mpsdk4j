package org.elkan1788.osc.weixin.mp.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.SHA1;
import org.elkan1788.osc.weixin.mp.commons.WxMsgType;
import org.elkan1788.osc.weixin.mp.commons.WxApiUrl;
import org.elkan1788.osc.weixin.mp.exception.WxRespException;
import org.elkan1788.osc.weixin.mp.util.JsonMsgBuilder;
import org.elkan1788.osc.weixin.mp.util.SimpleHttpReq;
import org.elkan1788.osc.weixin.mp.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 微信公众平台开发者API接口实现
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/12
 * @version 1.2.2
 */
public class WxApiImpl implements WxApi {

    private static final Logger log = LoggerFactory.getLogger(WxApiImpl.class);

    private MPAct mpAct;

    /**
     * 微信公众平台接口构建
     *
     * @param mpAct 公众号信息
     */
    public WxApiImpl(MPAct mpAct) {
        this.mpAct = mpAct;
    }

    @Override
    public String getAccessToken() throws WxRespException {
        String token = mpAct.getAccessToken();
        if (null == token
                || token.isEmpty()
                || mpAct.getExpiresIn() < System.currentTimeMillis()) {
            synchronized (mpAct){
                refreshAccessToken();
            }
            token = mpAct.getAccessToken();
        }
        return token;
    }

    @Override
    public void refreshAccessToken() throws WxRespException {
        String url = String.format(WxApiUrl.ACCESS_TOKEN_API,
                mpAct.getAppId(),
                mpAct.getAppSecert());
        String result = "";
        try {
            result = SimpleHttpReq.get(url);
        } catch (IOException e) {
            log.error("刷新ACCESS_TOKEN时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
           throw new WxRespException(result);
        }

        mpAct.createAccessToken(result);
    }

    @Override
    public List<String> getServerIp() throws WxRespException {
        String url = String.format(WxApiUrl.IP_LIST_API, getAccessToken());
        String result = "";
        try {
            result = SimpleHttpReq.get(url);
        } catch (IOException e) {
            log.error("获取微信服务器IP时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
            throw new WxRespException(result);
        }

        JSONObject tmp = JSON.parseObject(result);
        List<String> ips = JSONObject.parseArray(tmp.getString("ip_list"), String.class);

        return ips;
    }

    @Override
    public List<Menu> getMenu() throws WxRespException {
        String url = String.format(WxApiUrl.CUSTOM_MENU_API, "get", getAccessToken());
        String result = "";
        try {
            result = SimpleHttpReq.get(url);
        } catch (IOException e) {
            log.error("获取当前自定义菜单时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
            throw new WxRespException(result);
        }
        JSONObject tmp = JSON.parseObject(result).getJSONObject("menu");
        List<Menu> menus = JSON.parseArray(tmp.getString("button"), Menu.class);
        return menus;
    }

    @Override
    public boolean createMenu(Menu... menus) throws WxRespException {

        PropertyFilter null_filter = new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                if(name.equals("key")
                        && (null==value || "".equals(value))) {
                    return false;
                }
                if (name.equals("url")
                        && (null==value || "".equals(value))) {
                    return false;
                }
                return true;
            }
        };
        Map<String, Object> buttons = new HashMap<>();
        buttons.put("button", Arrays.asList(menus));
        String btn_json = JSONObject.toJSONString(buttons, null_filter);
        String url = String.format(WxApiUrl.CUSTOM_MENU_API, "create", getAccessToken());
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, btn_json);
        } catch (IOException e) {
            log.error("创建自定义菜单时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || !result.contains("ok")) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public boolean deleteMenu() throws WxRespException {
        String url = String.format(WxApiUrl.CUSTOM_MENU_API, "delete", getAccessToken());
        String result = "";
        try {
            result = SimpleHttpReq.get(url);
        } catch (IOException e) {
            log.error("删除微信自定义菜单时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public int creatGroup(String name) throws WxRespException {
        String url = String.format(WxApiUrl.GROUPS_API, "create", getAccessToken());
        String data = "{\"group\":{\"name\":\""+name+"\"}}";
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, data);
        } catch (IOException e) {
            log.error("创建微信分组时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || !result.contains("group")) {
            throw new WxRespException(result);
        }

        JSONObject tmp = JSON.parseObject(result).getJSONObject("group");

        return tmp.getInteger("id");
    }

    @Override
    public List<Group> getGroups() throws WxRespException {
        String url = String.format(WxApiUrl.GROUPS_API, "get", getAccessToken());
        String result = "";
        try {
            result = SimpleHttpReq.get(url);
        } catch (IOException e) {
            log.error("获取所有分组时失败!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.indexOf("errcode") > -1) {
            throw new WxRespException(result);
        }

        String tmp = JSON.parseObject(result).getString("groups");
        List<Group> groups = JSON.parseArray(tmp, Group.class);

        return groups;
    }

    @Override
    public boolean renGroup(int id, String name) throws WxRespException {
        String url = String.format(WxApiUrl.GROUPS_API, "update", getAccessToken());
        name = name.length() > 30 ? name.substring(0,30) : name;
        String data = "{\"group\":{\"id\":"+id+",\"name\":\""+name+"\"}}";
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, data);
        } catch (IOException e) {
            log.error("修改分组名称时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.contains("errcode")) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public int getGroupId(String openId) throws WxRespException {
        String url = String.format(WxApiUrl.GROUPS_API, "getid", getAccessToken());
        String data = "{\"openid\":\""+openId+"\"}";
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, data);
        } catch (IOException e) {
            log.error("获取用户所在分组时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.indexOf("errcode") > -1) {
            throw new WxRespException(result);
        }

        int group_id = JSON.parseObject(result).getInteger("groupid");

        return group_id;
    }

    @Override
    public boolean move2Group(String openId, int groupId) throws WxRespException {
        String url = String.format(WxApiUrl.GROUPS_USER_API, getAccessToken());
        String data = "{\"openid\":\""+openId+"\",\"to_groupid\":"+groupId+"}";
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, data);
        } catch (IOException e) {
            log.error("移动用户分组时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || !result.contains("ok")) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public FollowList getFollowerList(String nextOpenId) throws WxRespException {
        String url = String.format(WxApiUrl.FOLLOWS_USER_API, getAccessToken(), nextOpenId);
        String result = "";
        try {
            result = SimpleHttpReq.get(url);
        } catch (IOException e) {
            log.error("获取关注用户列表时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.indexOf("errcode") > -1) {
            throw new WxRespException(result);
        }

        FollowList followers = JSON.parseObject(result, FollowList.class);
        String openid = JSON.parseObject(result).getJSONObject("data").getString("openid");
        List<String> openids = JSON.parseArray(openid, String.class);
        followers.setOpenIds(openids);
        return followers;
    }

    @Override
    public Follower getFollower(String openId, String lang) throws WxRespException {
        String url = String.format(WxApiUrl.USER_INFO_API, getAccessToken(), openId, lang);
        String result = "";
        try {
            result = SimpleHttpReq.get(url);
        } catch (IOException e) {
            log.error("获取用户信息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || result.indexOf("errcode") > -1) {
            throw new WxRespException(result);
        }

        Follower follower = JSON.parseObject(result, Follower.class);

        return follower;
    }

    @Override
    public boolean sendCustomerMsg(OutPutMsg msg) throws WxRespException {
        String url = String.format(WxApiUrl.CUSTOM_MESSAGE_API, getAccessToken());
        String custom_msg = "";
        WxMsgType type = WxMsgType.valueOf(msg.getMsgType());
        switch (type) {
            case text:
                custom_msg = JsonMsgBuilder.create().text(msg).build();
                break;
            case image:
                custom_msg = JsonMsgBuilder.create().image(msg).build();
                break;
            case voice:
                custom_msg = JsonMsgBuilder.create().voice(msg).build();
                break;
            case video:
                custom_msg = JsonMsgBuilder.create().video(msg).build();
                break;
            case music:
                custom_msg = JsonMsgBuilder.create().music(msg).build();
                break;
            case news:
                custom_msg = JsonMsgBuilder.create().news(msg).build();
                break;
            default:
                break;
        }
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, custom_msg);
        } catch (IOException e) {
            log.error("发送客服消息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty() 
                || !result.contains("ok")) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public boolean sendTemplateMsg(String openId, String templateId,
                                   String topColor, String url, Template... templates) throws WxRespException {
        String api_url = String.format(WxApiUrl.TEMPLATE_MESSAGE_API, getAccessToken());
        String template_msg = JsonMsgBuilder.create()
                .template(openId, templateId, topColor, url, templates)
                .build();
        String result = "";
        try {
            result = SimpleHttpReq.post(api_url, SimpleHttpReq.APPLICATION_JSON, template_msg);
        } catch (IOException e) {
            log.error("发送模板消息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty() ||
                result.contains("errcode")) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public String upMedia(String mediaType, File file) throws WxRespException {
        String url = String.format(WxApiUrl.MEDIA_UP_API, mediaType, getAccessToken());
        String result = "";
        try {
            result = SimpleHttpReq.upload(url, file);
        } catch (IOException e) {
            log.error("上传多媒体文件时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                ||result.contains("errcode")) {
            throw new WxRespException(result);
        }

        String media_id = JSON.parseObject(result).getString("media_id");

        return media_id;
    }

    @Override
    public void dlMedia(String mediaId, File file) throws WxRespException {

        String url = String.format(WxApiUrl.MEDIA_DL_API, getAccessToken(), mediaId);
        // 检查文件夹是否存在
        if(!file.exists()) {
            String path = file.getAbsolutePath();
            String separ = System.getProperties().getProperty("file.separator");
            File dir = new File(path.substring(0, path.lastIndexOf(separ)));
            dir.mkdirs();
        }

        try {
            SimpleHttpReq.download(url, file);
        } catch (IOException e) {
            log.error("下载多媒体文件时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public String[] upNews(Article2... articles2s) throws WxRespException {
        String url = String.format(WxApiUrl.NEWS_UPLOAD_API, getAccessToken());
        String upnews_msg = JsonMsgBuilder.create().uploadNews(articles2s).build();
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, upnews_msg);
        } catch (IOException e) {
            log.error("上传群发图文消息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                ||result.contains("errcode")) {
            throw new WxRespException(result);
        }
        JSONObject tmp = JSON.parseObject(result);
        String[] results = {
                tmp.getString("type"),
                tmp.getString("media_id"),
                tmp.getString("created_at")
        };
        return results;
    }

    @Override
    public String[] upVideo(String mediaId,
                                String title, String description) throws WxRespException {
        String url = String.format(WxApiUrl.MEDIA_UPVIDEO_API, getAccessToken());
        String result = "";
        String upvideo_msg = JsonMsgBuilder.create().uploadVideo(mediaId,title,description).build();
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, upvideo_msg);
        } catch (IOException e) {
            log.error("上传群发消息中的视频时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                ||result.contains("errcode")) {
            throw new WxRespException(result);
        }
        JSONObject tmp = JSON.parseObject(result);
        String[] results = {
                tmp.getString("type"),
                tmp.getString("media_id"),
                tmp.getString("created_at")
        };
        return results;
    }

    @Override
    public String sendAll(OutPutMsg msg) throws WxRespException {
        String group_id = msg.getGroupId();
        List<String> to_users = msg.getToUsers();
        if (null != group_id
                && !group_id.isEmpty()
                && !to_users.isEmpty()) {
            throw new RuntimeException("群发消息只能选择一种模式");
        }

        String url = "";
        if (to_users.isEmpty()) {
           url =  String.format(WxApiUrl.GROUP_NEWS_MESSAGE_API, "sendall", getAccessToken());
        } else {
            url = String.format(WxApiUrl.GROUP_NEWS_MESSAGE_API, "send", getAccessToken());
        }

        String result = "";
        String send_msg = JsonMsgBuilder.create().sendAll(msg).build();
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, send_msg);
        } catch (IOException e) {
            log.error("发送群消息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                ||!result.contains("msg_id")) {
            throw new WxRespException(result);
        }

        JSONObject tmp = JSON.parseObject(result);

        return tmp.getString("msg_id");
    }

    @Override
    public boolean delSendAll(String msgId) throws WxRespException {
        String url = String.format(WxApiUrl.GROUP_NEWS_MESSAGE_API, "delete", getAccessToken());
        String result = "";
        String del_msg = "{\"msg_id\":"+ msgId +"}";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, del_msg);
        } catch (IOException e) {
            log.error("删除群发消息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                ||!result.contains("ok")) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public Map<String, Object> getJsAPISign(String url) throws WxRespException {
        // 1.创建JSTICKET
        String ticket = mpAct.getJsTicket();
        if (null == ticket
                || ticket.isEmpty()
                || mpAct.getJsExpiresIn() < System.currentTimeMillis()) {
            synchronized (mpAct){
                refreshJsAPITicket();
            }
            ticket = mpAct.getJsTicket();
        }

        // 2.生成签名
        String sign_param = "jsapi_ticket=%1$s&noncestr=%2$s&timestamp=%3$s&url=%4$s";
        String nonce = UUID.randomUUID().toString().toLowerCase();
        long time = System.currentTimeMillis() / 1000;
        String sign = null;
        try {
            sign = SHA1.calculate(String.format(sign_param, ticket, nonce, time, url));
        } catch (AesException e) {
            log.error("生成JSTICKET的签名时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        // 3.返回参数与值
        Map<String, Object> sign_map = new HashMap<>();
        sign_map.put("url", url);
        sign_map.put("ticket", ticket);
        sign_map.put("nonce", nonce);
        sign_map.put("timestamp", time);
        sign_map.put("sign", sign);
        return sign_map;
    }

    @Override
    public void refreshJsAPITicket() throws WxRespException {

        String token_url = WxApiUrl.JSAPI_TICKET_URL+getAccessToken();

        String result = "";
        try {
            result = SimpleHttpReq.get(token_url);
        } catch (IOException e) {
            log.error("刷新JSTICKET时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }

        if (result.isEmpty()
                || !result.contains("ok")) {
            throw new WxRespException(result);
        }

        mpAct.createJsTicket(result);
    }
}
