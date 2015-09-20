package io.github.elkan1788.mpsdk4j.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.elkan1788.mpsdk4j.core.WechatDefHandler;
import io.github.elkan1788.mpsdk4j.core.WechatHandler;
import io.github.elkan1788.mpsdk4j.core.WechatKernel;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;

/**
 * Servlet环境接入
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WechatServletSupport extends HttpServlet {

    private static final long serialVersionUID = 8041879868339754244L;

    protected static WechatKernel _wk = new WechatKernel();
    protected static WechatHandler _wh = new WechatDefHandler();

    @Override
    public void init() throws ServletException {
        // TODO 实际生产中写入微信公众号信息
        MPAccount mpAct = new MPAccount();
        _wk.setMpAct(mpAct);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        _wk.setParams(req.getParameterMap());
        String echo = _wk.check();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().print(echo);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String respmsg = _wk.handle(req.getInputStream(), _wh);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().print(respmsg);
    }

}
