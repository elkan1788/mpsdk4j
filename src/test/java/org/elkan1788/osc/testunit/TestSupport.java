package org.elkan1788.osc.testunit;

import org.elkan1788.osc.weixin.mp.vo.MPAct;
import org.junit.Before;

import java.util.Properties;

/**
 * 测试父类
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/12
 * @version 1.0.0
 */
public class TestSupport {
    
    protected String mpId;
    protected String appId;
    protected String appSecret;
    protected String token;
    protected String openId;
    protected String aesKey;
    
    protected String templateId;
    protected String mediaId;
    protected String accessToken;

    protected String msgSing;
    protected String timestamp;
    protected String echostr;
    protected String nonce;

    protected MPAct mpAct;
    
    @Before
    public void init() throws Exception {
        Properties p = new Properties();
        p.load(getClass().getResourceAsStream("/test-cfg.properties"));
        
        mpId = p.getProperty("mpId");
        appId = p.getProperty("appId");
        appSecret = p.getProperty("appSecret");
        token = p.getProperty("token");
        openId = p.getProperty("openId");
        aesKey = p.getProperty("aesKey");
        
        templateId = p.getProperty("templateId");
        mediaId = p.getProperty("mediaId");
        accessToken = p.getProperty("accessToken", "NOT");

        msgSing = p.getProperty("msgSing");
        timestamp = p.getProperty("timestamp");
        echostr = p.getProperty("echostr");
        nonce = p.getProperty("nonce");

        mpAct = new MPAct();
        mpAct.setMpId(mpId);
        mpAct.setAppId(appId);
        mpAct.setAppSecert(appSecret);
        mpAct.setToken(token);
        mpAct.setAESKey(aesKey);
        if (!accessToken.equals("NOT")||!accessToken.isEmpty()) {
            mpAct.setAccessToken(accessToken);
            mpAct.setExpiresIn(7000 * 1000 + System.currentTimeMillis());
        }
    }
}
