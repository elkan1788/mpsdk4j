package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * CredentialApi 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class CredentialApiTest extends APITestSupport {

    private static final Log log = Logs.get();

    @Test
    public void testGetAccessToken() {
        CredentialAPI ca = WechatAPIImpl.create(mpAct);
        assertNotNull(ca.getAccessToken());
    }

    @Test
    public void testGetServerIps() {
        CredentialAPI ca = WechatAPIImpl.create(mpAct);
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
