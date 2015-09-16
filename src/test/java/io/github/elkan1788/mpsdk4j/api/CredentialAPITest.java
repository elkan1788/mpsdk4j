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
public class CredentialAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    private CredentialAPI ca;

    @Override
    @Before
    public void init() {
        log.info("====== CredentialAPITest ======");
        super.init();
        ca = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testGetAccessToken() {
        log.info("====== CredentialAPI#getAccessToken ======");
        assertNotNull(ca.getAccessToken());
        log.info(ca.getAccessToken());
    }

    @Test
    public void testGetServerIps() {
        log.info("====== CredentialAPI#getServerIps ======");
        List<String> ips = ca.getServerIps();
        assertNotNull(ips);
        int i = 0;
        for (String ip : ips) {
            i++;
            log.infof("Wechat server[%d] ip: %s", i, ip);
        }
    }

    @Test
    public void testGetShorUrl() {
        log.info("====== CredentialAPI#getShortUrl ======");
        String longurl = "https://mp.weixin.qq.com/wiki/10/165c9b15eddcfbd8699ac12b0bd89ae6.html";
        String shorurl = ca.getShortUrl(longurl);
        assertNotNull(shorurl);
        log.info(shorurl);
    }

    @Test
    public void testGetJSTicket() {
        log.info("====== CredentialAPI#getJSTicket ======");
        String jsticket = ca.getJSTicket();
        assertNotNull(jsticket);
        log.info(jsticket);
    }
}
