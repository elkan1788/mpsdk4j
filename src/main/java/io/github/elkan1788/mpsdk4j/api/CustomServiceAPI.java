package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.vo.api.KfSession;

import java.util.List;

/**
 * 客服消息接口
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.b.2
 */
public interface CustomServiceAPI {

    /**
     * 客服消息的会话状态接口地址
      */
    String kfSessionURL = "/customservice/kfsession/%s?access_token=";

    /**
     * 创建会话接口地址
     */
    String kfSessionCreateURL = "/customservice/kfsession/create?access_token=";

    /**
     * 关闭会话接口地址
     */
    String kfSessionClose = "/customservice/kfsession/close?access_token=";

    /**
     * 获取用户会话状态接口地址
     */
    String kfSessionGetURL = "/customservice/kfsession/getsession?access_token=%1$s&openid=";

    /**
     * 获取客服会话列表接口地址
     */
    String kfSessionGetListURL = "/customservice/kfsession/getsessionlist?access_token=%1$s&kf_account=";

    /**
     * 获取未接入会话列表接口地址
     */
    String kfSessionGetWaitURL = "/customservice/kfsession/getwaitcase?access_token=";

    /**
     * 创建客服会话
     * @param kfAccount 客服账号
     * @param openId    微信用户
     * @param message   客服接口消息
     * @return  创建成功返回"true"相反返回"false"
     */
    boolean createKfSession(String kfAccount, String openId, String message);

    /**
     * 关闭客服会话
     * @param kfAccount 客服账号
     * @param openId    微信用户
     * @param message   客服接口消息
     * @return  关闭成功返回"true"相反返回"false"
     */
    boolean closeKfSession(String kfAccount, String openId, String message);

    /**
     * 获取用户会话状态
     * @param openId    微信用户
     * @return  客服账号与创建时间
     */
    KfSession getSession(String openId);

    /**
     * 获取客服的接待列表
     * @param kfAccount 客服账号
     * @return  接待用户与创建时间列表
     */
    List<KfSession> getSessionList(String kfAccount);

    /**
     * 获取正在等待会话的列表
     * @return  等待用户,接待客服及创建时间
     */
    List<KfSession> getSessionWaitList();
}
