package io.github.elkan1788.mpsdk4j.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.elkan1788.mpsdk4j.core.WechatKernel;

/**
 * 各种WEB容器环境接入
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public abstract class WechatWebSupport {

    /**
     * 微信消息处理核心
     */
    protected static WechatKernel _wk = new WechatKernel();

    /**
     * !!!实际生产中需要重写此方法内的数据!!!
     * <ol>
     * <li>开发者的微信公众号信息</li>
     * <li>微信消息处理器</li>
     * </ol>
     */
    public void init() {}

    /**
     * 与微信服务器互动
     * 
     * <p>
     * !!!实际生产中写个入口方法调用即可!!!
     * </p>
     * 
     * @param req
     *            微信服务器请求
     * @param resp
     *            响应微信服务器
     * @throws IOException 抛出IO异常
     */
    protected void interact(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        init();
        _wk.setParams(req.getParameterMap());
        String respmsg = "success";
        if ("GET".equals(req.getMethod())) {
            respmsg = _wk.check();
        }
        else {
            respmsg = _wk.handle(req.getInputStream());
        }
        // 输出回复消息
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().print(respmsg);
    }

}
