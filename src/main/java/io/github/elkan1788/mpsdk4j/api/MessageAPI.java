/**
 * @author senhui.li
 */
package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.vo.api.Template;

/**
 * 微信高级消息接口
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public interface MessageAPI {
    
    /**
     * 发模板消息地址
     */
    String send_template = "/message/template/send?access_token=";

    /**
     * 发送模板消息
     * 
     * @param openId
     *            接收用户Id
     * @param tmlId
     *            模板Id
     * @param topColor
     *            顶部颜色
     * @param url
     *            跳转链接
     * @param tmls
     *            模板数据
     * @return 消息Id
     */
    long sendTemplateMsg(String openId, String tmlId, String topColor, String url, Template... tmls);
}
