package io.github.elkan1788.mpsdk4j.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.elkan1788.mpsdk4j.core.WechatDefHandler;
import io.github.elkan1788.mpsdk4j.util.ConfigReader;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;

/**
 * WEB 容器
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WebSupportContainer extends WechatWebSupport {

    private static ConfigReader _cr;

    static {
        _cr = new ConfigReader("/mpconf.properties");
    }

    @Override
    public void init() {
        MPAccount mpAct = new MPAccount();
        mpAct.setMpId(_cr.get("mpId"));
        mpAct.setAppId(_cr.get("appId"));
        mpAct.setAppSecret(_cr.get("appSecret"));
        mpAct.setToken(_cr.get("token"));
        _wk.setMpAct(mpAct);
        _wk.setWechatHandler(new WechatDefHandler());
    }

    public void wechat(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.interact(req, resp);
    }
}
