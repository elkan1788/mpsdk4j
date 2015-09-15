package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.github.elkan1788.mpsdk4j.vo.api.FollowList;
import io.github.elkan1788.mpsdk4j.vo.api.Follower;
import io.github.elkan1788.mpsdk4j.vo.api.Follower2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * UserAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@FixMethodOrder(MethodSorters.JVM)
public class UserAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    private UserAPI ua;

    private String openId = "";

    @Before
    public void init() {
        super.init();
        ua = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testGetFollowerList() {
        FollowList fl = ua.getFollowerList(null);
        assertNotNull(fl);
        log.info("====== UserAPI#getFollowerList ====== ");
        log.info(fl);
    }

    @Test
    public void testGetFollower() {
        Follower f = ua.getFollower(openId, null);
        assertNotNull(f);
        log.info("====== UserAPI#getFollower ====== ");
        log.info(f);
    }

    @Test
    public void testUpdateRemark() {
        boolean flag = ua.updateRemark(openId, "Youself");
        assertTrue(flag);
    }

    @Test
    public void testGetFollowers() {
        List<Follower2> getfs = new ArrayList<Follower2>();
        getfs.add(new Follower2(openId));
        getfs.add(new Follower2(""));

        List<Follower> fs = ua.getFollowers(getfs);
        assertNotNull(fs);
        assertEquals(2, fs.size());
        log.info("====== UserAPI#getFollowers ====== ");
        log.info(fs);
    }
}
