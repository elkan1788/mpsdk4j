package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.github.elkan1788.mpsdk4j.vo.api.Groups;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nutz.log.Log;
import org.nutz.log.Logs;

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

    @Override
    @Before
    public void init() {
        log.info("====== GroupsAPITest ======");
        super.init();
        ga = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testCreateGroup() {
        log.info("====== GroupsAPI#createGroup ======");
        groupId = ga.createGroup("测试分组");
        assertTrue(groupId > 0);
        log.info(groupId);
    }

    @Test
    public void testGetGroups() {
        log.info("====== GroupsAPI#getGroups  ======");
        log.infof("groupId: %d", groupId);
        List<Groups> gs = ga.getGroups();
        assertNotNull(gs);
        for (Groups g : gs) {
            log.info(g);
        }
    }

    @Test
    public void testRenGroups() {
        log.info("====== GroupsAPI#renGroups ======");
        boolean flag = ga.renGroups(groupId, "测试分组改名");
        assertTrue(flag);
        log.info(flag);
    }

    @Test
    public void testMove2Group() {
        log.info("====== GroupsAPI#move2Group ======");
        boolean flag = ga.move2Group(openId, groupId);
        assertTrue(flag);
        log.info(flag);
    }

    @Test
    public void testGetGroup() {
        log.info("====== GroupsAPI#getGroup ======");
        int id = ga.getGroup(openId);
        assertEquals(id, groupId);
        log.info(id);
    }

    @Test
    public void testBatchMove2Group() {
        log.info("====== GroupsAPI#batchMove2Group ======");
        List<String> openIds = new ArrayList<String>();
        openIds.add(openId);
        openIds.add(openId2);
        boolean flag = ga.batchMove2Group(openIds, groupId);
        assertTrue(flag);
        log.info(flag);
    }

    @Test
    public void testDelGroup() {
        log.info("====== GroupsAPI#delGroup ======");
        boolean flag = ga.delGroup(groupId);
        assertTrue(flag);
        log.info(flag);
    }

}
