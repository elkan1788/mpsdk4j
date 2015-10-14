package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.common.EventType;
import io.github.elkan1788.mpsdk4j.vo.api.Menu;

/**
 * CustomMenuAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@FixMethodOrder(MethodSorters.JVM)
public class MenuAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    private MenuAPI cma;

    @Override
    @Before
    public void init() {
        log.info("====== MenuAPITest ======");
        super.init();
        cma = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testDelMenu() {
        log.info("====== MenuAPI#delMenu ======");
        List<Menu> menus = cma.getMenu();
        assertNotNull(menus);
        for (Menu m : menus) {
            log.info(m);
        }

        boolean flag = cma.delMenu();
        assertTrue(flag);

        menus = cma.getMenu();
        assertTrue(menus == null);
    }

    @Test
    public void testCreateMenu() {
        log.info("====== MenuAPI#createMenu ======");
        Menu csdn = new Menu("CSND");
        csdn.setType(EventType.VIEW.name());
        csdn.setUrl("http://www.csdn.net");

        Menu oschina = new Menu("OSCHINA");
        oschina.setType(EventType.VIEW.name());
        oschina.setUrl("http://www.oschina.net");

        Menu map = new Menu("Map");
        Menu baidu = new Menu("BaiDu", EventType.VIEW.name(), "http://map.baidu.com");
        Menu tencent = new Menu("Tencent", EventType.VIEW.name(), "http://map.qq.com");
        Menu ali = new Menu("Ali", EventType.VIEW.name(), "http://ditu.amap.com/");
        map.setSubButtons(Arrays.asList(baidu, tencent, ali));

        boolean flag = cma.createMenu(csdn, oschina, map);
        assertTrue(flag);
    }
}
