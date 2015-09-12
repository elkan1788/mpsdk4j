package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.vo.api.Groups;

/**
 * GroupsAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@FixMethodOrder(MethodSorters.JVM)
public class GroupsAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    private GroupsAPI ga;
    private String openId = "";
    private List<String> openIds;
    private int groupId = 100;

    @Override
    @Before
    public void init() {
        super.init();
        ga = WechatAPIImpl.create(mpAct);
        openIds = new ArrayList<String>();
        openIds.add("");
        openIds.add("");
    }

    @Test
    public void testCreateGroup() {
        groupId = ga.createGroup("测试分组");
        assertTrue(groupId > 0);
    }

    @Test
    public void testGetGroups() {
        log.infof("groupId: %d", groupId);
        List<Groups> gs = ga.getGroups();
        assertNotNull(gs);
        log.infof(" ========== mp[%s] groups ==========", mpAct.getMpId());
        for (Groups g : gs) {
            log.info(g);
        }
    }

    @Test
    public void testRenGroups() {
        boolean flag = ga.renGroups(groupId, "测试分组改名");
        assertTrue(flag);
    }

    @Test
    public void testMove2Group() {
        boolean flag = ga.move2Group(openId, groupId);
        assertTrue(flag);
    }

    @Test
    public void testGetGroup() {
        int id = ga.getGroup(openId);
        assertEquals(id, groupId);
    }

    @Test
    public void testBatchMove2Group() {
        boolean flag = ga.batchMove2Group(openIds, groupId);
        assertTrue(flag);
    }

    @Test
    public void testDelGroup() {
        boolean flag = ga.delGroup(groupId);
        assertTrue(flag);
    }

}
