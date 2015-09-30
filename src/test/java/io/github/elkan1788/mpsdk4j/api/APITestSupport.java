package io.github.elkan1788.mpsdk4j.api;

import org.junit.Before;

import io.github.elkan1788.mpsdk4j.TestSupport;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;

public class APITestSupport extends TestSupport {

    protected MPAccount mpAct;
    protected static String openId;
    protected static String openId2;
    protected static int groupId;
    protected static String accessToken;
    protected static String mediaId;
    protected static String ticket;
    protected static String tmplId;

    @Before
    public void init() {
        mpAct = new MPAccount();
        mpAct.setMpId(_cr.get("mpId"));
        mpAct.setAppId(_cr.get("appId"));
        mpAct.setAppSecret(_cr.get("appSecret"));

        openId = _cr.get("openId");
        openId2 = _cr.get("openId2");
        groupId = _cr.getInt("groupId");
        accessToken = _cr.get("accessToken");
        mediaId = _cr.get("mediaId");
        ticket = _cr.get("ticket");
        tmplId = _cr.get("tmplId");
    }

}
