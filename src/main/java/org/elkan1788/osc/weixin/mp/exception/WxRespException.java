package org.elkan1788.osc.weixin.mp.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elkan1788.osc.weixin.mp.commons.WxErrCode;

/**
 * 微信响应错误异常
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/6
 * @version 1.0.0
 */
public class WxRespException extends Exception {

    /**
     * 错误代码
     */
    private final int errCode;
    /**
     * 错误中文描述
     */
    private final String errMesg;
    // 临时JSON对象
    protected static JSONObject error;

    public WxRespException(String message) {
        super(convertMesg(message));
        this.errCode = error.getInteger("errcode");
        this.errMesg = error.getString("errmsg");
    }

    /**
     * 将消息转成JSON对象
     * @param message   消息
     * @return  error对象
     */
    protected static String convertMesg(String message) {
        if (null == message || message.isEmpty()) {
            throw new RuntimeException("网络通讯异常,请检查!!!");
        }
        String err_desc = "";
        error = JSON.parseObject(message);
        err_desc = WxErrCode.getErrDesc(error.getInteger("errcode"));
        return err_desc;
    }

    /**
     * 获取错误代码
     *
     * @return errCode
     */
    public int getErrCode() {
        return errCode;
    }

    /**
     * 获取错误描述
     *
     * @return  errmsg
     */
    public String getErrMesg() {
        return errMesg;
    }
}
