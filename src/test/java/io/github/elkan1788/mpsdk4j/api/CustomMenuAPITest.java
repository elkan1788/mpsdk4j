package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertNotNull;
import io.github.elkan1788.mpsdk4j.vo.menu.Menu;

import java.util.List;

import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * CustomMenuAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class CustomMenuAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    @Test
    public void testGetMenu() {
        CustomMenuAPI cma = WechatAPIImpl.create(mpAct);
        List<Menu> menus = cma.getMenu();
        assertNotNull(menus);
        for (Menu m : menus) {
            log.info(m);
        }
    }

    @Test
    public void testCreateMenu() {

    }

    @Test
    public void testDelMenu() {

    }

}
