package io.github.elkan1788.mpsdk4j.api;

import org.junit.Before;

import io.github.elkan1788.mpsdk4j.vo.MPAccount;

public class APITestSupport {

    protected MPAccount mpAct;

    @Before
    public void init() {
        mpAct = new MPAccount();
        mpAct.setMpId("");
        mpAct.setAppId("");
        mpAct.setAppSecret("");
    }

}
