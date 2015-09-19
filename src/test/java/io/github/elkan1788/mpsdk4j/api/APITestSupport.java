package io.github.elkan1788.mpsdk4j.api;

import org.junit.Before;

import io.github.elkan1788.mpsdk4j.TestSupport;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;

public class APITestSupport extends TestSupport {

    protected MPAccount mpAct;

    @Before
    public void init() {
        mpAct = new MPAccount();
        mpAct.setMpId(_cr.get("mpId"));
        mpAct.setAppId(_cr.get("appId"));
        mpAct.setAppSecret(_cr.get("appSecret"));
    }

}
