package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.vo.api.FollowList;
import io.github.elkan1788.mpsdk4j.vo.api.Follower;
import io.github.elkan1788.mpsdk4j.vo.api.Follower2;

import java.util.Collection;
import java.util.List;

/**
 * 微信用户信息接口
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public interface UserAPI {

    /**
     * 设置备注名地址
     */
    String userRemarkURL = "/user/info/updateremark?access_token=";

    /**
     * 用户列表地址
     */
    String userListURL = "/user/get?access_token=%s&next_openid=%s";

    /**
     * 用户基本信息地址
     */
    String userInfoURL = "/user/info?access_token=%s&openid=%s&lang=%s";

    /**
     * 批量用户基本信息地址
     */
    String batchUserInfoURL = "/user/info/batchget?access_token=";

    /**
     * 设置用户备注名
     * 
     * @param openId
     *            用户标识
     * @param remark
     *            新的备注名,长度必须小于30字符
     * @return true 或 false
     */
    boolean updateRemark(String openId, String remark);

    /**
     * 获取关注用户列表
     * 
     * @param nextOpenId
     *            第一个拉取的OPENID,不填默认从头开始拉取
     * @return 关注列表{@link FollowList}
     */
    FollowList getFollowerList(String nextOpenId);

    /**
     * 获取用户基本信息(包括UnionID机制)
     * 
     * @param openId
     *            用户的标识
     * @param lang
     *            国家地区语言版本,zh_CN 简体,zh_TW 繁体,en 英语
     * @return 关注用户 {@link io.github.elkan1788.mpsdk4j.vo.api.Follower}
     */
    Follower getFollower(String openId, String lang);

    /**
     * 批量获取用户基本信息[最多拉取100条]
     * 
     * @param users
     *            批量用户集合{@link io.github.elkan1788.mpsdk4j.vo.api.Follower2}
     * @return 关注用户 {@link io.github.elkan1788.mpsdk4j.vo.api.Follower}
     */
    List<Follower> getFollowers(Collection<Follower2> users);
}
