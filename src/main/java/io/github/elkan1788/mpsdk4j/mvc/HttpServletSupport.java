package io.github.elkan1788.mpsdk4j.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.elkan1788.mpsdk4j.core.WechatKernel;

/**
 * Servlet环境接入
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public abstract class HttpServletSupport extends HttpServlet {

    private static final long serialVersionUID = 8041879868339754244L;

    protected static WechatKernel _wk = new WechatKernel();

    /**
     * !!!实际生产中需要重写此方法内的数据!!!
     * <ol>
     * <li>开发者的微信公众号信息</li>
     * <li>微信消息处理器</li>
     * </ol>
     */
    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        _wk.setParams(req.getParameterMap());
        String echo = _wk.check();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().print(echo);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        _wk.setParams(req.getParameterMap());
        String respmsg = _wk.handle(req.getInputStream());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().print(respmsg);
    }

}
