package io.github.elkan1788.mpsdk4j.api;

import org.junit.Before;

import io.github.elkan1788.mpsdk4j.util.ConfigReader;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;

public class APITestSupport {

    protected MPAccount mpAct;

    static ConfigReader _cr = new ConfigReader("/mpconf.properties");

    @Before
    public void init() {
        mpAct = new MPAccount();
        mpAct.setMpId(_cr.get("mpId"));
        mpAct.setAppId(_cr.get("appId"));
        mpAct.setAppSecret(_cr.get("appSecret"));
    }

}
