package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import io.github.elkan1788.mpsdk4j.common.MediaType;
import io.github.elkan1788.mpsdk4j.vo.api.AccessToken;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * 模拟测试所有的API异常
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.b.2
 */
public class AllAPIRetryTest extends RunTestSupport {

    private static final Log log = Logs.get();

    @BeforeClass
    public void init() {
        log.info("====== AllAPIRetryTest ======");
        Object[] test2 = new Object[]{ 1, "2", 3};
        Object[] test3 = new Object[4];
        test3[0] = "0";
        System.arraycopy(test2, 0, test3, 1, test2.length);
        System.out.println(Json.toJson(test2));
        System.out.println(Json.toJson(test3));

    }

    @BeforeMethod
    public void mockUpGetException() {
        getMethodLostATException();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testRetryCredentialAPI() throws RuntimeException {
        try {
            log.info("====== CredentialAPI#RetryGetJSTicket ======");
            wechatAPI.getJSTicket();
        } catch (Exception e) {
            // 不做任何处理,继续下一步测试
        }

        try {
            log.info("====== CredentialAPI#RetryGetServerIps ======");
            wechatAPI.getServerIps();
        } catch (Exception e) {
        }

        try {
            log.info("====== CredentialAPI#RetryGetAccessToken ======");
            AccessToken at = WechatAPIImpl.getAccessTokenCache().get(mpId);
            at.setExpiresIn(-100);
            WechatAPIImpl.getAccessTokenCache().set(mpId, at);

            invalidAppIdException();
            wechatAPI.getAccessToken();
        } catch (Exception e) {
        }

        log.info("====== CredentialAPI#RetryGetShortUrl ======");
        postMethodLostATException();
        wechatAPI.getShortUrl(url);
    }

    @Test(expectedExceptions = RuntimeException.class, enabled = true)
    public void testRetryGroupAPI() {

        try {
            log.info("====== GroupsAPI#RetryGetGroups  ======");
            wechatAPI.getGroups();
        } catch (Exception e) {
        }

        postMethodLostATException();

        try {
            log.info("====== GroupsAPI#retryCreateGroup ======");
            wechatAPI.createGroup(groupName);
        } catch (Exception e) {
        }

        try {
            log.info("====== GroupsAPI#retryBatchMove2Group ======");
            List<String> openIds = new ArrayList<String>();
            openIds.add(openId);
            openIds.add(openId);
            wechatAPI.batchMove2Group(openIds, groupId);
        } catch (Exception e) {
        }

        try {
            log.info("====== GroupsAPI#retryGetGroup ======");
            wechatAPI.getGroup(openId);
        } catch (Exception e) {
        }

        try {
            log.info("====== GroupsAPI#retryMove2Group ======");
            wechatAPI.move2Group(openId, groupId);
        } catch (Exception e) {
        }

        try {
            log.info("====== GroupsAPI#retryRenGroups ======");
            wechatAPI.renGroups(groupId, groupName+"Ren");
        } catch (Exception e) {
        }

        log.info("====== GroupsAPI#retryDelGroup ======");
        wechatAPI.delGroup(groupId);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testRetryMediaAPI() {
        try {
            log.info("====== MediaAPI#retryUpMedia ======");
            wechatAPI.upMedia(MediaType.image.name(), this.imgMedia);
        } catch (Exception e) {
        }

        log.info("====== MediaAPI#retryDlMedia ======");
        wechatAPI.dlMedia(mediaId);
    }
}
