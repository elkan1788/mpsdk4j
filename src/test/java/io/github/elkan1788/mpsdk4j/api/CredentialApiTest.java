package io.github.elkan1788.mpsdk4j.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.vo.MPAccount;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class CredentialApiTest {

    private static final Log log = Logs.get();

    private MPAccount mpAct;

    @Before
    public void init() {
        mpAct = new MPAccount();
        mpAct.setMpId("");
        mpAct.setAppId("");
        mpAct.setAppSecret("");
    }

    @Test
    public void testGetAccessToken() {
        CredentialApi aa = CredentialApi.create(mpAct);
        Assert.assertNotNull(aa.getAccessToken());
    }

    @Test
    public void testGetServerIps() {
        CredentialApi aa = CredentialApi.create(mpAct);
        log.infof("access_token: %s", aa.getAccessToken());
        List<String> ips = aa.getServerIps();
        Assert.assertNotNull(ips);
        int i = 0;
        for (String ip : ips) {
            i++;
            log.infof("%d: %s", i, ip);

        }
    }

}
