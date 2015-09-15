package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.vo.MPAccount;

import org.junit.Before;

public class APITestSupport {

    protected MPAccount mpAct;

    @Before
    public void init() {
        mpAct = new MPAccount();
        mpAct.setMpId("gh_dcc1e9f05cf1");
        mpAct.setAppId("wxf602a2bc7f5d189c");
        mpAct.setAppSecret("68c51bd8d22a28a0d0759dfbd631f13f");
    }

}
