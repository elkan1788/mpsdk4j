package org.elkan1788.osc.weixin.mp.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 微信开放平台授权公众号信息
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/1/8
 */
public class AuthInfo {
    /**
     * 授权方昵称
     */
    @JSONField(name = "nick_name")
    private String nickName;
    /**
     * 授权方appid
     */
    @JSONField(name = "authorizer_appid")
    private String appId;
    /**
     * 授权方令牌（在授权的公众号具备API权限时<p/>才有此返回值）
     */
    @JSONField(name = "authorizer_access_token")
    private String accessToken;
    /**
     * 有效期（在授权的公众号具备API权限时<p/>才有此返回值）
     */
    @JSONField(name = "expires_in")
    private long expiresIn;
    /**
     * 授权方头像
     */
    @JSONField(name = "head_img")
    private String headImg;
    /**
     * 授权方公众号类型<p/>
     * 0代表订阅号<p/>
     * 1代表由历史老帐号升级后的订阅号<p/>
     * 2代表服务号
     */
    @JSONField(name = "service_type_info")
    private String serTypeInfo;
    /**
     * 授权方认证类型<p/>
     * -1代表未认证<p/>
     * 0代表微信认证<p/>
     * 1代表新浪微博认证<p/>
     * 2代表腾讯微博认证<p/>
     * 3代表已资质认证通过但还未通过名称认证<p/>
     * 4代表已资质认证通过、还未通过名称认证<p/>
     * 但通过了新浪微博认证<p/>
     * 5代表已资质认证通过、还未通过名称认证<p/>但通过了腾讯微博认证
     */
    @JSONField(name = "verify_type_info")
    private String verTypeInfo;
    /**
     * 授权方公众号的原始ID
     */
    @JSONField(name = "user_name")
    private String mpId;
    /**
     * 授权方公众号所设置的微信号<p/>可能为空
     */
    @JSONField(name = "alias")
    private String alias;
    /**
     * 刷新令牌（在授权的公众号具备API权限时<p/>才有此返回值）<p/><p/>
     * 刷新令牌主要用于公众号服务获取和刷新已授权用户的access_token<p/><p/>
     * 只会在授权时刻提供<p/>请妥善保存。 一旦丢失<p/>只能让用户重新授权<p/><p/>
     * 才能再次拿到新的刷新令牌
     */
    @JSONField(name = "authorizer_refresh_token")
    private String refreshToken;

    /**
     * 公众号授权给开发者的权限集列表
     */
    @JSONField(name = "func_info")
    private List<FunctionInfo> funs;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSerTypeInfo() {
        return serTypeInfo;
    }

    public void setSerTypeInfo(String serTypeInfo) {
        this.serTypeInfo = serTypeInfo;
    }

    public String getVerTypeInfo() {
        return verTypeInfo;
    }

    public void setVerTypeInfo(String verTypeInfo) {
        this.verTypeInfo = verTypeInfo;
    }

    public String getMpId() {
        return mpId;
    }

    public void setMpId(String mpId) {
        this.mpId = mpId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<FunctionInfo> getFuns() {
        return funs;
    }

    public void setFuns(List<FunctionInfo> funs) {
        this.funs = funs;
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "nickName='" + nickName + '\'' +
                ", appId='" + appId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", headImg='" + headImg + '\'' +
                ", serTypeInfo='" + serTypeInfo + '\'' +
                ", verTypeInfo='" + verTypeInfo + '\'' +
                ", mpId='" + mpId + '\'' +
                ", alias='" + alias + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", funs=" + String.valueOf(funs) +
                '}';
    }

    /**
     * 公众号授权给开发者的权限集
     */
    public class FunctionInfo {

        /**
         * 公众号授权给开发者的权限集列表[1-9]分别代表<p/>
         * 1.消息与菜单权限集<p/>
         * 2.用户管理权限集<p/>
         * 3.帐号管理权限集<p/>
         * 4.网页授权权限集<p/>
         * 5.微信小店权限集<p/>
         * 6.多客服权限集<p/>
         * 7.业务通知权限集<p/>
         * 8.微信卡券权限集<p/>
         * 9.微信扫一扫权限集<p/>
         */
        @JSONField(name = "funcscope_category")
        private String category;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "FunctionInfo{" +
                    "category='" + category + '\'' +
                    '}';
        }
    }
}
