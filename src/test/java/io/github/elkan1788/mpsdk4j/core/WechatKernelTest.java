package io.github.elkan1788.mpsdk4j.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.nutz.lang.random.R;
import org.nutz.lang.random.StringGenerator;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.TestSupport;
import io.github.elkan1788.mpsdk4j.util.StreamTool;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WechatKernelTest extends TestSupport {

    private static final Log log = Logs.get();

    private MPAccount mpAct;
    private Map<String, String[]> data;

    @Before
    public void init() {
        log.info("====== WechatKernelTest ======");
        mpAct = new MPAccount();
        mpAct.setMpId(_cr.get("mpId"));
        mpAct.setAppId(_cr.get("appId"));
        mpAct.setAppSecret(_cr.get("appSecret"));
        mpAct.setToken(_cr.get("token"));
        data = new HashMap<String, String[]>();
        data.put("signature", new String[]{"ffbcb8aca5c4c7d5da4e41461908470cf07a518a"});
        data.put("timestamp", new String[]{"1442726144"});
        data.put("nonce", new String[]{"1439307736"});
        data.put("echostr", new String[]{"1439307541"});
    }

    @Test
    public void testNullParamsCheck() {
        log.info("====== WechatKernel#check-null ======");
        data.clear();
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals("error"));
    }

    @Test
    public void testLongParamsCheck() {
        log.info("====== WechatKernel#check-toolong ======");
        StringGenerator sg = R.sg(129, 200);
        data.put("signature", new String[]{sg.next()});
        data.put("timestamp", new String[]{sg.next()});
        data.put("nonce", new String[]{sg.next()});
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals("error"));
    }

    @Test
    public void testLong1ParamsCheck() {
        log.info("====== WechatKernel#check-toolong1 ======");
        StringGenerator sg = R.sg(129, 200);
        data.put("signature", new String[]{sg.next()});
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals("error"));
    }

    @Test
    public void testLong2ParamsCheck() {
        log.info("====== WechatKernel#check-toolong2 ======");
        StringGenerator sg = R.sg(129, 200);
        data.put("timestamp", new String[]{sg.next()});
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals("error"));
    }

    @Test
    public void testLong3ParamsCheck() {
        log.info("====== WechatKernel#check-toolong3 ======");
        StringGenerator sg = R.sg(129, 200);
        data.put("nonce", new String[]{sg.next()});
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals("error"));
    }

    @Test
    public void testNull1ParamsCheck() {
        log.info("====== WechatKernel#check-null1 ======");
        data.put("signature", null);
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals("error"));
    }

    @Test
    public void testNull2ParamsCheck() {
        log.info("====== WechatKernel#check-null2 ======");
        data.put("timestamp", null);
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals("error"));
    }

    @Test
    public void testNull3ParamsCheck() {
        log.info("====== WechatKernel#check-null3 ======");
        data.put("nonce", null);
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals("error"));
    }

    @Test
    public void testCheck() {
        log.info("====== WechatKernel#check ======");
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String echo = wk.check();
        assertNotNull(echo);
        assertTrue(echo.equals(data.get("echostr")[ 0]));
    }

    @Test
    public void testTextHandle() {
        log.info("====== WechatKernel#handle-text ======");
        String textxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[text]]></MsgType>\n"
                         + "<Content><![CDATA[你好!]]></Content>\n"
                         + "<MsgId>6091046778677430</MsgId>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(textxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testImageHandle() {
        log.info("====== WechatKernel#handle-image ======");
        String textxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + " <MsgType><![CDATA[image]]></MsgType>\n"
                         + "<PicUrl><![CDATA[this is a url]]></PicUrl>\n"
                         + "<MediaId><![CDATA[media_id]]></MediaId>\n"
                         + "<MsgId>6091046778677430</MsgId>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(textxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testVoiceHandle() {
        log.info("====== WechatKernel#handle-voice ======");
        String textxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[voice]]></MsgType>\n"
                         + "<MediaId><![CDATA[GOoTXFJ_Gll6-OAKS1DVMVYhhk1EVMNk]]></MediaId>\n"
                         + "<Format><![CDATA[arm]]></Format>\n"
                         + "<Recognition><![CDATA[腾讯微信团队]]></Recognition>\n"
                         + "<MsgId>1234567890123456</MsgId>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(textxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testShortVideoHandle() {
        log.info("====== WechatKernel#handle-shortvideo ======");
        String textxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[shortvideo]]></MsgType>\n"
                         + "<MediaId><![CDATA[GOoTXFJ_Gll6-OAKS1DVMVYhhk1EVMNk]]></MediaId>\n"
                         + "<ThumbMediaId><![CDATA[GOoTXFJ_Gll6-OAKS1DVMVYhhk1EVMNk]]></ThumbMediaId>\n"
                         + "<MsgId>1234567890123456</MsgId>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(textxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testSubEventHandle() {
        log.info("====== WechatKernel#handle-subevent ======");
        String textxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[event]]></MsgType>\n"
                         + "<Event><![CDATA[subscribe]]></Event>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(textxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

}
