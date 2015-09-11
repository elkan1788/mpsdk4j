package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * CredentialApi 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CredentialApiTest extends APITestSupport {

    private static final Log log = Logs.get();

    private CredentialAPI ca;

    @Override
    @Before
    public void init() {
        super.init();
        ca = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testGetAccessToken() {
        assertNotNull(ca.getAccessToken());
    }

    @Test
    public void testGetServerIps() {
        log.infof("access_token: %s", ca.getAccessToken());
        List<String> ips = ca.getServerIps();
        assertNotNull(ips);
        int i = 0;
        for (String ip : ips) {
            i++;
            log.infof("Wechat server[%d] ip: %s", i, ip);
        }
    }

}
