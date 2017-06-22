package io.github.elkan1788.mpsdk4j;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 测试Mpsdk4j
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class Mpsdk4jTest extends RunTestSupport {

    private static final Log log = Logs.get();

    /**
     * Test method for {@link io.github.elkan1788.mpsdk4j.Mpsdk4j#version()}.
     */
    @Test
    public void testVersion() {
        new Mpsdk4j(); // Just cover testing
        log.debug(Mpsdk4j.version());
        String cur_ver = "2.b.3";
        Assert.assertEquals(cur_ver, Mpsdk4j.version());
    }

}
