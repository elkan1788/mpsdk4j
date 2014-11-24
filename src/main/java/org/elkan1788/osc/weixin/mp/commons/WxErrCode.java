package org.elkan1788.osc.weixin.mp.commons;

/**
 * 微信通讯的错误代码
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/6
 * @version 1.0.0
 */
public class WxErrCode {

    public static final int SYS_BUSY = -1;
    public static final int OK = 0;
    public static final int ERROR_SECRET = 40001;
    public static final int INVALID_CERTIFICATE = 40002;
    public static final int INVALID_OPENID = 40003;
    public static final int INVALID_MEDIATYPE = 40004;
    public static final int INVALID_FILETYPE = 40005;
    public static final int INVALID_FILESIZE = 40006;
    public static final int INVALID_MEDIAID = 40007;
    public static final int INVALID_MESGTYPE = 40008;
    public static final int INVALID_IMAGESIZE = 40009;
    public static final int INVALID_VOICESIZE = 40010;
    public static final int INVALID_VIDEOSIZE = 40011;
    public static final int INVALID_THUMBSIZE = 40012;
    public static final int INVALID_APPID = 40013;
    public static final int INVALID_ACCESSTOKEN = 40014;
    public static final int INVALID_MENUTYPE = 40015;
    public static final int INVALID_BUTTONSIZE = 40016;
    public static final int INVALID_BUTTONSIZE2 = 40017;
    public static final int INVALID_BUTTONNAMESIZE = 40018;
    public static final int INVALID_BUTTONKEYSIZE = 40019;
    public static final int INVALID_BUTTONURLSIZE = 40020;
    public static final int INVALID_BUTTONVERSION = 40021;
    public static final int INVALID_MENULEVEL = 40022;
    public static final int INVALID_SUBBUTTONSIZE = 40023;
    public static final int INVALID_SUBMENUTYPE = 40024;
    public static final int INVALID_SUBBUTTONNAMESIZE = 40025;
    public static final int INVALID_SUBBUTTONKEYSIZE = 40026;
    public static final int INVALID_SUBBUTTONURLSIZE = 40027;
    public static final int INVALID_MENUUSER = 40028;
    public static final int INVALID_OAUTHCODE = 40029;
    public static final int INVALID_REFRESHCODE = 40030;
    public static final int INVALID_OPENIDLIST = 40031;
    public static final int INVALID_OPENIDLENGTH = 40032;
    public static final int INVALID_REQUESTSTRING = 40033;
    public static final int INVALID_PARAMETERS = 40035;
    public static final int INVALID_REQUESTSTYLE = 40038;
    public static final int INVALID_URLLENGTH = 40039;
    public static final int INVALID_GROUPID = 40050;
    public static final int INVALID_GROUPNAME = 40051;
    public static final int INVALID_MSGID = 40059;
    public static final int LOST_AESSTOKEN = 41001;
    public static final int LOST_APPID = 41002;
    public static final int LOST_REFRESHTOKEN = 41003;
    public static final int LOST_APPSECRET = 41004;
    public static final int LOST_MEDIAFILE_DATA = 41005;
    public static final int LOST_MEDIAID = 41006;
    public static final int LOST_SUBMENUS = 41007;
    public static final int LOST_OAUTHCODE = 41008;
    public static final int LOST_OPENID = 41009;
    public static final int ACCESSTOKEN_OVERTIME = 42001;
    public static final int REFRESHTOKEN_OVERTIME = 42002;
    public static final int OAUTHCODE_OVERTIME = 42003;
    public static final int GET_REUEST = 43001;
    public static final int POST_REUEST = 43002;
    public static final int HTTPS_REUEST = 43003;
    public static final int FOLLOW_REUEST = 43004;
    public static final int FRIEND_REUEST = 43005;
    public static final int NULL_MEDIAFILE = 44001;
    public static final int NULL_POSTDATA = 44002;
    public static final int NULL_NEWSDATA = 44003;
    public static final int NULL_TEXTDATA = 44004;
    public static final int MEDIASIZE_LIMIT_OVER = 45001;
    public static final int CONTENT_LIMIT_OVER = 45002;
    public static final int TITLE_LIMIT_OVER = 45003;
    public static final int DIGEST_LIMIT_OVER = 45004;
    public static final int LINKURL_LIMIT_OVER = 45005;
    public static final int PICURL_LIMIT_OVER = 45006;
    public static final int VIOCE_PLAY_LIMIT_OVER = 45007;
    public static final int NEWS_LIMIT_OVER = 45008;
    public static final int INTERFACE_LIMIT_OVER = 45009;
    public static final int MENUSIZE_LIMIT_OVER = 45010;
    public static final int REPLYTIME_LIMIT_OVER = 45015;
    public static final int SYSTEM_GROUP = 45016;
    public static final int GROUPNAME_LIMIT_OVER = 45017;
    public static final int GROUPSIZE_LIMIT_OVER = 45018;
    public static final int NOEXIST_MEDIAFILE = 46001;
    public static final int NOEXIST_MENUVERSION = 46002;
    public static final int NOEXIST_MENUDATA = 46003;
    public static final int NOEXIST_USER = 46004;
    public static final int PARSE_XML_JSON_ERR = 47001;
    public static final int UNOAUTH_MODULE_API = 48001;
    public static final int UNOATH_API = 50001;

    /**
     * 获取错误信息描述
     *
     * @param errCode   错误代码
     * @return  错误描述
     */
    public static String getErrDesc(int errCode){
        switch (errCode) {
            case OK:
                return "请求成功";
            case SYS_BUSY:
                return "系统繁忙";
            case ERROR_SECRET:
                return "获取access_token时AppSecret错误，或者access_token无效";
            case INVALID_CERTIFICATE:
                return "不合法的凭证类型";
            case INVALID_OPENID:
                return "不合法的OpenID";
            case INVALID_MEDIATYPE:
                return "不合法的媒体文件类型";
            case INVALID_FILETYPE:
                return "不合法的文件类型";
            case INVALID_FILESIZE:
                return "不合法的文件大小";
            case INVALID_MEDIAID:
                return "不合法的媒体文件id";
            case INVALID_MESGTYPE:
                return "不合法的消息类型";
            case INVALID_IMAGESIZE:
                return "不合法的图片文件大小";
            case INVALID_VOICESIZE:
                return "不合法的语音文件大小";
            case INVALID_VIDEOSIZE:
                return "不合法的视频文件大小";
            case INVALID_THUMBSIZE:
                return "不合法的缩略图文件大小";
            case INVALID_APPID:
                return "不合法的APPID";
            case INVALID_ACCESSTOKEN:
                return "不合法的access_token";
            case INVALID_MENUTYPE:
                return "不合法的菜单类型";
            case INVALID_BUTTONSIZE:
            case INVALID_BUTTONSIZE2:
                return "不合法的按钮个数";
            case INVALID_BUTTONNAMESIZE:
                return "不合法的按钮名字长度";
            case INVALID_BUTTONKEYSIZE:
                return "不合法的按钮KEY长度";
            case INVALID_BUTTONURLSIZE:
                return "不合法的按钮URL长度";
            case INVALID_BUTTONVERSION:
                return "不合法的菜单版本号";
            case INVALID_MENULEVEL:
                return "不合法的子菜单级数";
            case INVALID_SUBBUTTONSIZE:
                return "不合法的子菜单按钮个数";
            case INVALID_SUBMENUTYPE:
                return "不合法的子菜单按钮类型";
            case INVALID_SUBBUTTONNAMESIZE:
                return "不合法的子菜单按钮名字长度";
            case INVALID_SUBBUTTONKEYSIZE:
                return "不合法的子菜单按钮KEY长度";
            case INVALID_SUBBUTTONURLSIZE:
                return "不合法的子菜单按钮KEY长度";
            case INVALID_MENUUSER:
                return "不合法的自定义菜单使用用户";
            case INVALID_OAUTHCODE:
                return "不合法的oauth_code";
            case INVALID_REFRESHCODE:
                return "不合法的refresh_token";
            case INVALID_OPENIDLIST:
                return "不合法的openid列表";
            case INVALID_OPENIDLENGTH:
                return "不合法的openid列表长度";
            case INVALID_REQUESTSTRING:
                return "不合法的请求字符，不能包含\\uxxxx格式的字符";
            case INVALID_PARAMETERS:
                return "不合法的参数";
            case INVALID_REQUESTSTYLE:
                return "不合法的请求格式";
            case INVALID_URLLENGTH:
                return "不合法的URL长度";
            case INVALID_GROUPID:
                return "不合法的分组id";
            case INVALID_GROUPNAME:
                return "分组名字不合法";
            case INVALID_MSGID:
                return "不合法的消息ID";
            case LOST_AESSTOKEN:
                return "缺少access_token参数";
            case LOST_APPID:
                return "缺少appid参数";
            case LOST_REFRESHTOKEN:
                return "缺少refresh_token参数";
            case LOST_APPSECRET:
                return "缺少secret参数";
            case LOST_MEDIAFILE_DATA:
                return "缺少多媒体文件数据";
            case LOST_MEDIAID:
                return "缺少media_id参数";
            case LOST_SUBMENUS:
                return "缺少子菜单数据";
            case LOST_OAUTHCODE:
                return "缺少oauth code";
            case LOST_OPENID:
                return "缺少openid";
            case ACCESSTOKEN_OVERTIME:
                return "access_token超时";
            case REFRESHTOKEN_OVERTIME:
                return "refresh_token超时";
            case OAUTHCODE_OVERTIME:
                return "oauth code超时";
            case GET_REUEST:
                return "需求GET请求";
            case POST_REUEST:
                return "需求POST请求";
            case HTTPS_REUEST:
                return "需求HTTPS请求";
            case FOLLOW_REUEST:
                return "需要接收者关注";
            case FRIEND_REUEST:
                return "需要好友关系";
            case NULL_MEDIAFILE:
                return "多媒体文件为空";
            case NULL_POSTDATA:
                return "POST的数据包为空";
            case NULL_NEWSDATA:
                return "图文消息内容为空";
            case NULL_TEXTDATA:
                return "文本消息内容为空";
            case MEDIASIZE_LIMIT_OVER:
                return "多媒体文件大小超过限制";
            case CONTENT_LIMIT_OVER:
                return "消息内容超过限制";
            case TITLE_LIMIT_OVER:
                return "标题字段超过限制";
            case DIGEST_LIMIT_OVER:
                return "描述字段超过限制";
            case LINKURL_LIMIT_OVER:
                return "链接字段超过限制";
            case PICURL_LIMIT_OVER:
                return "图片链接字段超过限制";
            case VIOCE_PLAY_LIMIT_OVER:
                return "语音播放时间超过限制";
            case NEWS_LIMIT_OVER:
                return "图文消息超过限制";
            case INTERFACE_LIMIT_OVER:
                return "接口调用超过限制";
            case MENUSIZE_LIMIT_OVER:
                return "创建菜单个数超过限制";
            case REPLYTIME_LIMIT_OVER:
                return "回复时间超过限制";
            case SYSTEM_GROUP:
                return "系统分组，不允许修改";
            case GROUPNAME_LIMIT_OVER:
                return "分组名字过长";
            case GROUPSIZE_LIMIT_OVER:
                return "分组数量超过上限";
            case NOEXIST_MEDIAFILE:
                return "不存在媒体数据";
            case NOEXIST_MENUVERSION:
                return "不存在的菜单版本";
            case NOEXIST_MENUDATA:
                return "不存在的菜单数据";
            case NOEXIST_USER:
                return "不存在的用户";
            case PARSE_XML_JSON_ERR:
                return "解析JSON/XML内容错误";
            case UNOAUTH_MODULE_API:
                return "api功能未授权";
            case UNOATH_API:
                return "用户未授权该api";
            default:
                return "未知异常";
        }
    }
}
