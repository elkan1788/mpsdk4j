package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import io.github.elkan1788.mpsdk4j.vo.api.Groups;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * GroupsAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class GroupsAPITest extends RunTestSupport {

    private static final Log log = Logs.get();

    @BeforeClass
    public void init() {
        log.info("====== GroupsAPITest ======");
    }

    @Test
    public void testCreateGroup() {
        log.info("====== GroupsAPI#createGroup ======");
        MockUpHttpPost("{\"group\":{\"id\":"+groupId+",\"name\": \""+groupName+"\"}}");
        int temp = wechatAPI.createGroup(groupName);
        assertTrue(temp > 0);
        assertEquals(temp, groupId);
    }

    @Test
    public void testGetGroups() {
        log.info("====== GroupsAPI#getGroups  ======");
        MockUpHttpGet("{\"groups\":[{\"id\":1,\"name\": \"test1\", \"count\":100}," +
                "{\"id\":2,\"name\": \"test2\", \"count\":200}," +
                "{\"id\":3,\"name\": \"test3\", \"count\":300}]}\"");
        List<Groups> gs = wechatAPI.getGroups();
        assertNotNull(gs);
        assertEquals(gs.size(), 3);
        assertEquals(gs.get(0).getName(), "test1");
    }

    @Test
    public void testRenGroups() {
        log.info("====== GroupsAPI#renGroups ======");
        postMethodSuccess();
        boolean flag = wechatAPI.renGroups(groupId, "测试分组改名");
        assertTrue(flag);
    }

    @Test
    public void testMove2Group() throws Exception {
        log.info("====== GroupsAPI#move2Group ======");
        postMethodSuccess();
        boolean flag = wechatAPI.move2Group(openId, groupId);
        assertTrue(flag);
    }

    @Test
    public void testGetGroup() {
        log.info("====== GroupsAPI#getGroup ======");
        MockUpHttpPost("{\"groupid\":"+groupId+"}");
        int id = wechatAPI.getGroup(openId);
        assertEquals(groupId, id);
    }

    @Test
    public void testBatchMove2Group() {
        log.info("====== GroupsAPI#batchMove2Group ======");
        postMethodSuccess();
        List<String> openIds = new ArrayList<String>();
        openIds.add(openId);
        openIds.add(openId);
        boolean flag = wechatAPI.batchMove2Group(openIds, groupId);
        assertTrue(flag);
    }

    @Test
    public void testDelGroup() {
        log.info("====== GroupsAPI#delGroup ======");
        postMethodSuccess();
        boolean flag = wechatAPI.delGroup(groupId);
        assertTrue(flag);
    }

}
