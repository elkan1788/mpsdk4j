package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.TestSupport;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;

import org.junit.Before;

public class APITestSupport extends TestSupport {

    protected MPAccount mpAct;
    protected String openId;
    protected String openId2;
    protected int groupId;
    protected String accessToken;
    protected String mediaId;
    protected String ticket;
    protected String tmplId;

    @Before
    public void init() {
        mpAct = new MPAccount();
        mpAct.setMpId(_cr.get("mpId"));
        mpAct.setAppId(_cr.get("appId"));
        mpAct.setAppSecret(_cr.get("appSecret"));

        this.openId = _cr.get("openId");
        this.openId2 = _cr.get("openId2");
        this.groupId = _cr.getInt("groupId");
        this.accessToken = _cr.get("accessToken");
        this.mediaId = _cr.get("mediaId");
        this.ticket = _cr.get("ticket");
        this.tmplId = _cr.get("tmplId");
    }

}
