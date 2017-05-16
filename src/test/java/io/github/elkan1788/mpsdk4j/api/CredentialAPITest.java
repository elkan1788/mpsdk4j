package io.github.elkan1788.mpsdk4j.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import io.github.elkan1788.mpsdk4j.vo.api.JSTicket;

/**
 * CredentialApi 测试
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class CredentialAPITest extends RunTestSupport {

    private static final Log log = Logs.get();

    @BeforeClass
    public void init() {
        log.info("====== CredentialAPITest ======");
    }

    @Test
    public void testGetServerIps() {
        log.info("====== CredentialAPI#getServerIps ======");

        MockUpHttpGet("{\"ip_list\":[\"127.0.0.1\",\"127.0.0.2\",\"127.0.0.3\"]}");
        List<String> ips = wechatAPI.getServerIps();
        assertNotNull(ips);
        assertEquals(ips.size(), 3);
    }

    @Test
    public void testGetShorUrl() {
        log.info("====== CredentialAPI#getShortUrl ======");

        String shortUrl = "http://w.url.cn/s/AvCo6Ih";
        MockUpHttpPost("{\"errcode\":0,\"errmsg\":\"ok\",\"shortURL\":\""+shortUrl+"\"}");

        String tmp = wechatAPI.getShortUrl(url);
        assertNotNull(tmp);
        assertEquals(tmp, shortUrl);
    }

    @Test
    public void testGetJSTicket() {
        log.info("====== CredentialAPI#getJSTicket ======");

        MockUpHttpGet("{\"errcode\":0,\"errmsg\":\"ok\",\"ticket\":\""+uuid+"\"," +
                "\"expires_in\":7200}");

        String tmp = wechatAPI.getJSTicket();
        assertNotNull(tmp);
        assertEquals(tmp, uuid);
    }

    @Test
    public void testJSTicketExpired() {
        log.info("====== CredentialAPI#JSTicketExpired ======");

        JSTicket jst = WechatAPIImpl.getJsTicketCache().get(mpId);
        jst.setExpiresIn(-100);
        WechatAPIImpl.getJsTicketCache().set(mpId, jst);

        MockUpHttpGet("{\"errcode\":0,\"errmsg\":\"ok\",\"ticket\":\""+uuid+"\"," +
                "\"expires_in\":7200}");

        String tmp = wechatAPI.getJSTicket();
        assertNotNull(tmp);
        assertEquals(tmp, uuid);
    }

}
