package org.elkan1788.osc.weixin.mp.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.elkan1788.osc.weixin.mp.commons.MsgType;
import org.elkan1788.osc.weixin.mp.commons.WxApiUrl;
import org.elkan1788.osc.weixin.mp.exception.WxRespException;
import org.elkan1788.osc.weixin.mp.util.JsonMsgBuilder;
import org.elkan1788.osc.weixin.mp.util.SimpleHttpReq;
import org.elkan1788.osc.weixin.mp.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信API接口实现
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/10
 * @version 1.0.0
 */
public class WxApiImpl implements WxApi {

    private static final Logger log = LoggerFactory.getLogger(WxApiImpl.class);

    private MPAct mpAct;

    public WxApiImpl(MPAct mpAct) {
        this.mpAct = mpAct;
    }

    @Override
    public String getAccessToken() throws WxRespException {
        String token = mpAct.getAccessToken();
        if (null == token
                || mpAct.getExpiresIn() < System.currentTimeMillis()) {
            refreshAccessToken();
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
            log.error(e.toString());
        }

        if (result.isEmpty()
                || result.indexOf("errcode") > 0) {
           throw new WxRespException(result);
        }

        mpAct.accessToken(result);
    }

    @Override
    public List<Menu> getMenu() throws WxRespException {
        String url = String.format(WxApiUrl.CUSTOM_MENU_API, "get", getAccessToken());
        String result = "";
        try {
            result = SimpleHttpReq.get(url);
        } catch (IOException e) {
            log.error("获取当前自定义菜单时出现异常!!!");
            log.error(e.toString());
        }

        if (result.isEmpty()
                || result.indexOf("errcode") > 0) {
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
            log.error(e.toString());
        }

        if (result.isEmpty()
                || result.indexOf("ok") < 0) {
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
            log.error(e.toString());
        }

        if (result.isEmpty()
                || result.indexOf("ok") < 0) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public int creatGroup(String name) throws WxRespException {
        String url = String.format(WxApiUrl.GROUPS_API, "create", getAccessToken());
        String body = "{\"group\":{\"name\":\""+name+"\"}}";
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, body);
        } catch (IOException e) {
            log.error("创建微信分组时出现异常!!!");
            log.error(e.toString());
        }

        if (result.isEmpty()
                || result.indexOf("group") < 0) {
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
            log.error(e.toString());
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
        String body = "{\"group\":{\"id\":"+id+",\"name\":\""+name+"\"}}";
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, body);
        } catch (IOException e) {
            log.error("修改分组名称时出现异常!!!");
            log.error(e.toString());
        }

        if (result.isEmpty()
                || result.indexOf("ok") < 0) {
            throw new WxRespException(result);
        }

        return true;
    }

    @Override
    public int getGroupId(String openId) throws WxRespException {
        String url = String.format(WxApiUrl.GROUPS_API, "getid", getAccessToken());
        String body = "{\"openid\":\""+openId+"\"}";
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, body);
        } catch (IOException e) {
            log.error("获取用户所在分组时出现异常!!!");
            log.error(e.toString());
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
        String body = "{\"openid\":\""+openId+"\",\"to_groupid\":"+groupId+"}";
        String result = "";
        try {
            result = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, body);
        } catch (IOException e) {
            log.error("移动用户分组时出现异常!!!");
            log.error(e.toString());
        }

        if (result.isEmpty()
                || result.indexOf("ok") < 0) {
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
            log.error(e.toString());
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
            log.error(e.toString());
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
        MsgType type = MsgType.valueOf(msg.getMsgType());
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
            log.error(e.toString());
        }

        if (result.isEmpty() ||
                result.indexOf("ok") < 0) {
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
            log.error(e.toString());
        }

        if (result.isEmpty() ||
                result.indexOf("ok") < 0) {
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
            log.error(e.toString());
        }

        if (result.isEmpty() ||
                result.indexOf("errcode") > -1) {
            throw new WxRespException(result);
        }

        String media_id = JSON.parseObject(result).getString("media_id");

        return media_id;
    }

    @Override
    public void dlMedia(String mediaId, File file) throws WxRespException {
        String url = String.format(WxApiUrl.MEDIA_DL_API, getAccessToken(), mediaId);
        boolean is_file = file.isFile();
        if (!is_file){
            file = new File(file.getAbsolutePath()+"/"+mediaId);
        }

        try {
            SimpleHttpReq.download(url, file);
        } catch (IOException e) {
            log.error("下载多媒体文件时出现异常!!!");
            log.error(e.toString());
        }
    }
}
