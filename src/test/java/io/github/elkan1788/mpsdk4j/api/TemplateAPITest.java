package io.github.elkan1788.mpsdk4j.api;

import org.junit.Before;
import org.junit.Test;
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

    @SuppressWarnings("unused")
    private TemplateAPI ta;

    @Before
    public void init() {
        log.info("====== TemplateAPITest ======");
        super.init();
        ta = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testSetIndustry() {

    }

    @Test
    public void testGetTemplateId() {

    }

}
