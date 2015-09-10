package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.common.EventType;
import io.github.elkan1788.mpsdk4j.vo.menu.Menu;

/**
 * CustomMenuAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomMenuAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    private CustomMenuAPI cma;

    @Override
    @Before
    public void init() {
        super.init();
        cma = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testCreateMenu() {
        Menu menu1 = new Menu("潮童FUN");
        Menu news = new Menu("最新活动公告",
                             EventType.VIEW.name(),
                             "http://mp.weixin.qq.com/s?__biz=MjM5NDg1OTY4NA==&mid=205158851&idx=1&sn=ade4eafe8bcdc3511f0592dc9bf8ab01#rd");
        Menu ranks = new Menu("游戏排行榜", EventType.VIEW.name(), "https://www.baidu.com");
        Menu puzzle = new Menu("潮童拼图游戏", EventType.CLICK.name(), "puzzle");
        menu1.setSubButtons(Arrays.asList(news, ranks, puzzle));

        Menu menu2 = new Menu("美式育婴");
        Menu share = new Menu("美式育婴分享", EventType.CLICK.name(), "baby_expr_share");
        Menu special = new Menu("努比微Show场",
                                EventType.VIEW.name(),
                                "http://m.weibo.cn/d/usnuby?jumpfrom=weibocom");
        menu2.setSubButtons(Arrays.asList(share, special));

        Menu menu3 = new Menu("努比微店");
        Menu web = new Menu("努比微店", EventType.VIEW.name(), "http://kdt.im/nz9NfGQ5r");
        Menu trail = new Menu("免费试用", EventType.VIEW.name(), "http://www.csdn.net");
        Menu store = new Menu("品牌介绍", EventType.VIEW.name(), "http://www.nuby.com");
        menu3.setSubButtons(Arrays.asList(web, trail, store));

        boolean flag = cma.createMenu(menu1, menu2, menu3);
        assertTrue(flag);

        List<Menu> menus = cma.getMenu();
        assertNotNull(menus);
        for (Menu m : menus) {
            log.info(m);
        }
    }

    @Test
    public void testDelMenu() {
        boolean flag = cma.delMenu();
        assertTrue(flag);

        List<Menu> menus = cma.getMenu();
        assertTrue(menus == null);
    }

}
