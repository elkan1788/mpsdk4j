package org.elkan1788.osc.weixin.mp.web;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.qq.weixin.mp.aes.AesException;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.elkan1788.osc.weixin.mp.core.WxBase;
import org.elkan1788.osc.weixin.mp.core.WxHandler;
import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Struts2环境接入
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/1/13
 */
public abstract class WxStruts2Support extends ActionSupport implements WxWebSupport, ServletRequestAware, ServletResponseAware {

    private static final Logger log = LoggerFactory.getLogger(WxStruts2Support.class);
    // 微信基础功能
    private WxBase wxBase = new WxBase();
    // Http请求
    private HttpServletRequest req;
    // Http响应
    private HttpServletResponse resp;

    protected void init() {
        // 重写此方法
        // 1.设置微信公众号的信息
        // 2.setMpAct();
        // 3.setWxHandler();
    }

    /**
     * 与微信信息交互<pre/>
     * 实现时只需写个Struts2入口调用此方法即可
     *
     * @return  回复消息
     * @throws java.io.IOException
     */
    protected void wxInteract() throws IOException {
        // 准备
        this.init();
        this.wxBase.init(req);
        // 响应设置
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String reply = "";
        // 区分POST与GET来源
        String method = req.getMethod();
        // 微信接入验证
        if ("GET".equals(method)) {
            try {
                reply = this.wxBase.check();
                if (reply.isEmpty()) {
                    reply = "error";
                    log.error("微信接入验证URL时失败!!!");
                    log.error("微信服务器echoStr值: {}", this.wxBase.getEchostr());
                    log.error("SHA1签名echoStr值: {}", reply);
                } else {
                    out.print(reply);
                }
            } catch (AesException e) {
                log.error("微信接入验证URL时出现异常!!!");
                log.error(e.getLocalizedMessage(), e);
                out.print("error");
            }
        }

        // 信息互动
        try {
            reply = this.wxBase.handler();
            out.print(reply);
        } catch (Exception e) {
            log.error("解析微信消息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
            out.print("error");
        }

    }

    @Override
    public void setMpAct(MPAct mpAct) {
        this.wxBase.setMpAct(mpAct);
    }

    @Override
    public void setWxHandler(WxHandler wxHandler) {
        this.wxBase.setWxHandler(wxHandler);
    }

    @Override
    public WxBase getWxBase() {
        return this.wxBase;
    }

    @Override
    public void setServletRequest(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void setServletResponse(HttpServletResponse resp) {
        this.resp = resp;
    }
}
