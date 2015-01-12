package org.elkan1788.osc.weixin.mp.commons;

/**
 * 微信所有的API地址
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @version 1.0.2
 * @since 2014/11/12
 */
public interface WxApiUrl {

    /**
     * 微信API入口
     */
    public static final String WX_API = "https://api.weixin.qq.com/cgi-bin";
    /**
     * 微信开放平台入口
     */
    public static final String WX_OPEN_API = "https://open.weixin.qq.com";
    /**
     * 微信多媒体文件API入口
     */
    public static final String MEDIA_API = "http://file.api.weixin.qq.com/cgi-bin/media";
    /**
     * 令牌API入口
     */
    public static final String ACCESS_TOKEN_API = WX_API + "/token?grant_type=client_credential&appid=%1$s&secret=%2$s";
    /**
     * 获取微信服务器IP
     */
    public static final String IP_LIST_API = WX_API + "/getcallbackip?access_token=%1$s";
    /**
     * 自定义菜单API入口[create, get, delete]
     */
    public static final String CUSTOM_MENU_API = WX_API + "/menu/%1$s?access_token=%2$s";
    /**
     * 分组管理API入口[create, get, getid, update]
     */
    public static final String GROUPS_API = WX_API + "/groups/%1$s?access_token=%2$s";
    /**
     * 用户分组API入口
     */
    public static final String GROUPS_USER_API = WX_API + "/groups/members/update?access_token=%1$s";
    /**
     * 微信用户信息API入口
     */
    public static final String USER_INFO_API = WX_API + "/user/info?access_token=%1$s&openid=%2$s&lang=%3$s";
    /**
     * 公众号关注者API入口
     */
    public static final String FOLLOWS_USER_API = WX_API + "/user/get?access_token=%1$s&next_openid=%2$s";
    /**
     * 下载多媒体文件API入口
     */
    public static final String MEDIA_DL_API = MEDIA_API + "/get?access_token=%1$s&media_id=%2$s";
    /**
     * 上传多媒体文件API入口
     */
    public static final String MEDIA_UP_API = MEDIA_API + "/upload?type=%1$s&access_token=%2$s";
    /**
     * 发送客服消息入口
     */
    public static final String CUSTOM_MESSAGE_API = WX_API + "/message/custom/send?access_token=%1$s";
    /**
     * 网页授权请求地址
     */
    public static final String WEB_OAUTH2 = "/connect/oauth2/authorize?appid=%1$s&redirect_uri=%2$s&response_type=code&scope=%3$s&state=%4$s;%5$s#wechat_redirect";
    /**
     * 网页授权获取TOKEN
     */
    public static final String OAUTH_TOKEN = "/sns/oauth2/access_token?appid=%1$s&secret=%2$s&grant_type=authorization_code&code=%3$s";
    /**
     * 网页授权取得用户信息地址
     */
    public static final String OAUTH_USERINFO = "/sns/userinfo?access_token=%1$s&openid=%2$s&lang=%3$s";
    /**
     * 发送模板消息地址
     */
    public static final String TEMPLATE_MESSAGE_API = WX_API + "/message/template/send?access_token=%1$";
    /**
     * 上传图文素材地址
     */
    public static final String NEWS_UPLOAD_API = WX_API + "/media/uploadnews?access_token=%1$s";
    /**
     * 分组群消息[sendall,send,delete]入口
     */
    public static final String GROUP_NEWS_MESSAGE_API = WX_API + "/message/mass/%1$s?access_token=%2$s";
    /**
     * 群发消息上传视频地址
     */
    public static final String MEDIA_UPVIDEO_API = MEDIA_API + "/uploadvideo?access_token=%1$s";
    /**
     * 服务组件API入口
     */
    public static final String COMPONENT_API = WX_API + "/component/%1$s?component_access_token=%2$s";
    /**
     * 获取服务组件token地址
     */
    public static final String COMPONENT_TOKEN_API = WX_API + "/component/api_component_token";
}
