package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * TemplateAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
// TODO 此为新接口待测试
public class TemplateAPITest extends RunTestSupport {

    private static final Log log = Logs.get();

    @BeforeClass
    public void init() {
        log.info("====== TemplateAPITest ======");
    }

    @Test
    public void testSetIndustry() {
        log.info("====== TemplateAPITest#setIndustry ======");
        postMethodSuccess();
        boolean flag = wechatAPI.setIndustry(1, 2);
        assertTrue(flag);
    }

    @Test
    public void testGetTemplateId() {
        log.info("====== TemplateAPITest#getTemplateId ======");
        MockUpHttpPost("{\"errcode\":0,\"errmsg\":\"ok\",\"template_id\":\"Doclyl5uP7Aciu-qZ7mJNPtWkbkYnWBWVja26EGbNyk\"}");
        String tmplid = wechatAPI.getTemplateId("5A90LqXLMHUVd0d1PFv-TezTxYWf2PBDV1APvAMeb1E");
        assertNotNull(tmplid);
    }

}
