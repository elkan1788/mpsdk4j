package io.github.elkan1788.mpsdk4j.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import io.github.elkan1788.mpsdk4j.vo.api.FollowList;
import io.github.elkan1788.mpsdk4j.vo.api.Follower;
import io.github.elkan1788.mpsdk4j.vo.api.Follower2;

/**
 * UserAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class UserAPITest extends RunTestSupport {

    private static final Log log = Logs.get();

    private Follower follower = new Follower();

    @BeforeClass
    public void init() {
        log.info("====== UserAPITest ====== ");

        follower.setOpenid(openId);
        follower.setSubscribe(1);
        follower.setCountry("China");
        follower.setProvince("ShangHai");
        follower.setCity("ShangHai");
        follower.setSex(1);
        follower.setSubscribeTime(new Date().getTime());
        follower.setGroupid(101);
        follower.setNickname("MPSDK4J");
        follower.setHeadimgurl(url);
        follower.setRemark("MPSDK4J");
    }

    @Test
    public void testGetFollowerList() {
        log.info("====== UserAPI#getFollowerList ====== ");
        MockUpHttpGet("{\"total\":23000,\"count\":10000,\"data\":{\"openid\":[\"OPENID1\",\"OPENID2\",\"OPENID10000\"]},\"next_openid\":\"OPENID10000\"}");
        FollowList fl = wechatAPI.getFollowerList(openId);
        assertNotNull(fl);
        assertEquals(fl.getCount(), 10000);
    }

    @Test
    public void testGetFollower() {
        log.info("====== UserAPI#getFollower ====== ");
        MockUpHttpGet(Json.toJson(follower));
        Follower f = wechatAPI.getFollower(openId, null);
        assertNotNull(f);
        assertEquals(f.getSubscribe(), 1);
    }

    @Test
    public void testUpdateRemark() {
        log.info("====== UserAPI#updateRemark ====== ");
        postMethodSuccess();
        boolean flag = wechatAPI.updateRemark(openId, "Youself");
        assertTrue(flag);
    }

    @Test
    public void testGetFollowers() {
        log.info("====== UserAPI#getFollowers ====== ");
        List<Follower2> getfs = new ArrayList<Follower2>();
        getfs.add(new Follower2(openId));
        getfs.add(new Follower2(openId));

        MockUpHttpPost("{\"user_info_list\":[{\"subscribe\":1,\"openid\":\"otvxTs4dckWG7imySrJd6jSi0CWE\",\"nickname\":\"iWithery\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"Jieyang\",\"province\":\"Guangdong\",\"country\":\"China\",\"headimgurl\":\"http://wx.qlogo.cn/mmopen/xbIQx1GRqdvyqkMMhEaGOX802l1CyqMJNgUzKP8MeAeHFicRDSnZH7FY4XB7p8XHXIf6uJA2SCunTPicGKezDC4saKISzRj3nz/0\",\"subscribe_time\":1434093047,\"unionid\":\"oR5GjjgEhCMJFyzaVZdrxZ2zRRF4\",\"remark\":\"\",\"groupid\":0},{\"subscribe\":0,\"openid\":\"otvxTs_JZ6SEiP0imdhpi50fuSZg\",\"unionid\":\"oR5GjjjrbqBZbrnPwwmSxFukE41U\"}]}");
        List<Follower> fs = wechatAPI.getFollowers(getfs);
        assertNotNull(fs);
        assertEquals(fs.size(), 2);
    }

}
