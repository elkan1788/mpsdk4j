package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import io.github.elkan1788.mpsdk4j.vo.api.Template;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * MessageAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class MessageAPITest extends RunTestSupport {

    private static final Log log = Logs.get();

    @BeforeClass
    public void init() {
        log.info("====== MessageAPITest ======");
    }

    @Test
    public void testSendTemplateMsg() {
        log.info("====== MessageAPI#sendTemplateMsg ======");
        Template tml = new Template("title", "测试");
        MockUpHttpPost("{\"errcode\":0,\"errmsg\":\"OK\",\"msgid\":200251}");
        long msgid = wechatAPI.sendTemplateMsg(openId, tmplId, "#119EF3", url, tml);
        assertTrue(msgid > 0);
    }

}
