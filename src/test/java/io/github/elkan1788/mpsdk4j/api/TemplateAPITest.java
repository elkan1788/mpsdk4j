package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * TemplateAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
// TODO 此为新接口待测试
public class TemplateAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    private TemplateAPI ta;

    @Before
    public void init() {
        log.info("====== TemplateAPITest ======");
        super.init();
        ta = WechatAPIImpl.create(mpAct);
    }

    @Ignore
    public void testSetIndustry() {
        log.info("====== TemplateAPITest#setIndustry ======");
        boolean flag = ta.setIndustry(1, 2);
        assertTrue(flag);
        log.info(flag);
    }

    @Ignore
    public void testGetTemplateId() {
        log.info("====== TemplateAPITest#getTemplateId ======");
        String tmplid = ta.getTemplateId("5A90LqXLMHUVd0d1PFv-TezTxYWf2PBDV1APvAMeb1E");
        assertNotNull(tmplid);
        log.info(tmplid);
    }

}
