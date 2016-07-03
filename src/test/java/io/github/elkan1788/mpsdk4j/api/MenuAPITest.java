package io.github.elkan1788.mpsdk4j.api;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.common.EventType;
import io.github.elkan1788.mpsdk4j.vo.api.Menu;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * CustomMenuAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class MenuAPITest extends RunTestSupport {

    private static final Log log = Logs.get();

    private Menu[] customerMenus;

    @BeforeClass
    public void init() {
        log.info("====== MenuAPITest ======");
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

        customerMenus = new Menu[]{ csdn, oschina, map };
    }

    @Test
    public void testDelMenu() {
        log.info("====== MenuAPI#delMenu ======");

        getMethodSuccess();

        boolean flag = wechatAPI.delMenu();
        assertTrue(flag);
    }

    @Test
    public void testCreateMenu() {
        log.info("====== MenuAPI#createMenu ======");

        postMethodSuccess();

        boolean flag = wechatAPI.createMenu(customerMenus);
        assertTrue(flag);

    }

    @Test
    public void testGetMenu() {
        log.info("====== MenuAPI#getMenu ======");

        String mockup_menus = "{\"menu\":{\"button\":"+Json.toJson(customerMenus,JsonFormat.compact())+"}}";
        MockUpHttpGet(mockup_menus);

        List<Menu> menus = wechatAPI.getMenu();
        assertNotNull(menus);
        assertEquals(menus.size(), 3);
    }
}
