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
import io.github.elkan1788.mpsdk4j.vo.event.TmplFinshJobEvent;
import io.github.elkan1788.mpsdk4j.vo.message.BasicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.MusicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VideoMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VoiceMsg;

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
        String imgxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + " <MsgType><![CDATA[image]]></MsgType>\n"
                         + "<PicUrl><![CDATA[this is a url]]></PicUrl>\n"
                         + "<MediaId><![CDATA[media_id]]></MediaId>\n"
                         + "<MsgId>6091046778677430</MsgId>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(imgxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testVoiceHandle() {
        log.info("====== WechatKernel#handle-voice ======");
        String voicexml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[voice]]></MsgType>\n"
                         + "<MediaId><![CDATA[GOoTXFJ_Gll6-OAKS1DVMVYhhk1EVMNk]]></MediaId>\n"
                         + "<Format><![CDATA[arm]]></Format>\n"
                         + "<Recognition><![CDATA[腾讯微信团队]]></Recognition>\n"
                         + "<MsgId>1234567890123456</MsgId>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(voicexml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
        log.info("====== WechatKernel#handle-voice-voice ======");
        wk = new WechatKernel(mpAct, new VoiceHandler(), data);
        respxml = wk.handle(StreamTool.toStream(voicexml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
        log.info("====== WechatKernel#handle-voice-music ======");
        wk = new WechatKernel(mpAct, new MediaHandler(), data);
        respxml = wk.handle(StreamTool.toStream(voicexml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testVideoHandle() {
        log.info("====== WechatKernel#handle-video ======");
        String videoxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[video]]></MsgType>\n"
                         + "<MediaId><![CDATA[GOoTXFJ_Gll6-OAKS1DVMVYhhk1EVMNk]]></MediaId>\n"
                         + "<ThumbMediaId><![CDATA[GOoTXFJ_Gll6-OAKS1DVMVYhhk1EVMNk]]></ThumbMediaId>\n"
                         + "<MsgId>1234567890123456</MsgId>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(videoxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
        log.info("====== WechatKernel#handle-video-video ======");
        wk = new WechatKernel(mpAct, new MediaHandler(), data);
        respxml = wk.handle(StreamTool.toStream(videoxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testShortVideoHandle() {
        log.info("====== WechatKernel#handle-shortvideo ======");
        String shortvxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[shortvideo]]></MsgType>\n"
                         + "<MediaId><![CDATA[GOoTXFJ_Gll6-OAKS1DVMVYhhk1EVMNk]]></MediaId>\n"
                         + "<ThumbMediaId><![CDATA[GOoTXFJ_Gll6-OAKS1DVMVYhhk1EVMNk]]></ThumbMediaId>\n"
                         + "<MsgId>1234567890123456</MsgId>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(shortvxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testLocationHandle() {
        log.info("====== WechatKernel#handle-location ======");
        String locationxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                             + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                             + "<CreateTime>1418182341</CreateTime>\n"
                             + "<MsgType><![CDATA[location]]></MsgType>\n"
                             + "<Location_X>23.134521</Location_X>\n"
                             + "<Location_Y>113.358803</Location_Y>\n"
                             + "<Scale>20</Scale>\n"
                             + "<Label><![CDATA[位置信息]]></Label>\n"
                             + "<MsgId>1234567890123456</MsgId>\n"
                             + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(locationxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testLinkHandle() {
        log.info("====== WechatKernel#handle-location ======");
        String linkxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                      + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                      + "<CreateTime>1418182341</CreateTime>\n"
                      + "<MsgType><![CDATA[link]]></MsgType>\n"
                      + "<Title><![CDATA[公众平台官网链接]]></Title>\n"
                      + "<Description><![CDATA[公众平台官网链接]]></Description>\n"
                      + "<Url><![CDATA[url]]></Url>\n"
                      + "<MsgId>1234567890123456</MsgId>\n"
                      + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(linkxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testUnknowHandle() {
        log.info("====== WechatKernel#handle-unknow ======");
        String unknowxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                      + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                      + "<CreateTime>1418182341</CreateTime>\n"
                      + "<MsgType><![CDATA[default]]></MsgType>\n"
                      + "<Url><![CDATA[url]]></Url>\n"
                      + "<MsgId>1234567890123456</MsgId>\n"
                      + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(unknowxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testSubEventHandle() {
        log.info("====== WechatKernel#handle-subevent ======");
        String subxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[event]]></MsgType>\n"
                         + "<Event><![CDATA[subscribe]]></Event>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(subxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testUnSubEventHandle() {
        log.info("====== WechatKernel#handle-subevent ======");
        String unsubxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182341</CreateTime>\n"
                          + "<MsgType><![CDATA[event]]></MsgType>\n"
                          + "<Event><![CDATA[unsubscribe]]></Event>\n"
                          + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(unsubxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testScanEventHandle() {
        log.info("====== WechatKernel#handle-scanevent ======");
        String scanxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182341</CreateTime>\n"
                          + "<MsgType><![CDATA[event]]></MsgType>\n"
                          + "<Event><![CDATA[SCAN]]></Event>\n"
                          + "<EventKey><![CDATA[SCENE_VALUE]]></EventKey>\n"
                          + "<Ticket><![CDATA[TICKET]]></Ticket>\n"
                          + "</xml>";
        WechatKernel wk = new WechatKernel();
        wk.setMpAct(mpAct);
        wk.setWechatHandler(new WechatDefHandler());
        wk.setParams(data);
        String respxml = wk.handle(StreamTool.toStream(scanxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testLocationEventHandle() {
        log.info("====== WechatKernel#handle-locationevent ======");
        String locationxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182341</CreateTime>\n"
                          + "<MsgType><![CDATA[event]]></MsgType>\n"
                          + "<Event><![CDATA[LOCATION]]></Event>\n"
                          + "<Latitude>23.137466</Latitude>\n"
                          + "<Longitude>113.352425</Longitude>\n"
                          + "<Precision>119.385040</Precision>\n"
                          + "</xml>";
        WechatKernel wk = new WechatKernel();
        wk.setMpAct(mpAct);
        wk.setWechatHandler(new WechatDefHandler());
        wk.setParams(data);
        String respxml = wk.handle(StreamTool.toStream(locationxml));
        assertNotNull(respxml);
        assertTrue(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testClickEventHandle() {
        log.info("====== WechatKernel#handle-clickevent ======");
        String clickxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                             + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                             + "<CreateTime>1418182341</CreateTime>\n"
                             + "<MsgType><![CDATA[event]]></MsgType>\n"
                             + "<Event><![CDATA[CLICK]]></Event>\n"
                             + "<EventKey><![CDATA[EVENTKEY]]></EventKey>\n"
                             + "</xml>";
        WechatKernel wk = new WechatKernel();
        wk.setMpAct(mpAct);
        wk.setWechatHandler(new WechatDefHandler());
        wk.setParams(data);
        String respxml = wk.handle(StreamTool.toStream(clickxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testViewEventHandle() {
        log.info("====== WechatKernel#handle-viewevent ======");
        String viewxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[event]]></MsgType>\n"
                         + "<Event><![CDATA[VIEW]]></Event>\n"
                         + "<EventKey><![CDATA[VIEWURL]]></EventKey>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel();
        wk.setMpAct(mpAct);
        wk.setWechatHandler(new WechatDefHandler());
        wk.setParams(data);
        String respxml = wk.handle(StreamTool.toStream(viewxml));
        assertNotNull(respxml);
        assertTrue(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testScanPushEventHandle() {
        log.info("====== WechatKernel#handle-scanpushevent ======");
        String spxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[event]]></MsgType>\n"
                         + "<Event><![CDATA[scancode_push]]></Event>\n"
                         + "<EventKey><![CDATA[6]]></EventKey>\n"
                         + "<ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType>\n"
                         + "<ScanResult><![CDATA[1]]></ScanResult>\n"
                         + "</ScanCodeInfo>\n"
                         + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(spxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testScanWaitEventHandle() {
        log.info("====== WechatKernel#handle-scanwaitmsgevent ======");
        String swxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                       + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                       + "<CreateTime>1418182341</CreateTime>\n"
                       + "<MsgType><![CDATA[event]]></MsgType>\n"
                       + "<Event><![CDATA[scancode_waitmsg]]></Event>\n"
                       + "<EventKey><![CDATA[6]]></EventKey>\n"
                       + "<ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType>\n"
                       + "<ScanResult><![CDATA[1]]></ScanResult>\n"
                       + "</ScanCodeInfo>\n"
                       + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(swxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testPicSysEventHandle() {
        log.info("====== WechatKernel#handle-picsysevent ======");
        String psxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                       + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                       + "<CreateTime>1418182341</CreateTime>\n"
                       + "<MsgType><![CDATA[event]]></MsgType>\n"
                       + "<Event><![CDATA[pic_sysphoto]]></Event>\n"
                       + "<EventKey><![CDATA[6]]></EventKey>\n"
                       + "<SendPicsInfo><Count>1</Count>\n"
                       + "<PicList><item><PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>\n"
                       + "</item></PicList></SendPicsInfo>\n"
                       + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(psxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testPicPhotoAlbumEventHandle() {
        log.info("====== WechatKernel#handle-picphotoalbumevent ======");
        String ppaxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                        + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                        + "<CreateTime>1418182341</CreateTime>\n"
                        + "<MsgType><![CDATA[event]]></MsgType>\n"
                        + "<Event><![CDATA[pic_photo_or_album]]></Event>\n"
                        + "<EventKey><![CDATA[6]]></EventKey>\n"
                        + "<SendPicsInfo><Count>1</Count>\n"
                        + "<PicList><item><PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>\n"
                        + "</item></PicList></SendPicsInfo>\n"
                        + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(ppaxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testPicWeixinEventHandle() {
        log.info("====== WechatKernel#handle-picweixinevent ======");
        String pwxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                       + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                       + "<CreateTime>1418182341</CreateTime>\n"
                       + "<MsgType><![CDATA[event]]></MsgType>\n"
                       + "<Event><![CDATA[pic_weixin]]></Event>\n"
                       + "<EventKey><![CDATA[6]]></EventKey>\n"
                       + "<SendPicsInfo><Count>1</Count>\n"
                       + "<PicList><item><PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>\n"
                       + "</item></PicList></SendPicsInfo>\n"
                       + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(pwxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testSelectLocationEventHandle() {
        log.info("====== WechatKernel#handle-selectlocationevent ======");
        String slxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                       + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                       + "<CreateTime>1418182341</CreateTime>\n"
                       + "<MsgType><![CDATA[event]]></MsgType>\n"
                       + "<Event><![CDATA[location_select]]></Event>\n"
                       + "<EventKey><![CDATA[6]]></EventKey>\n"
                       + "<SendLocationInfo><Location_X><![CDATA[23]]></Location_X>\n"
                       + "<Location_Y><![CDATA[113]]></Location_Y>\n"
                       + "<Scale><![CDATA[15]]></Scale>\n"
                       + "<Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>\n"
                       + "<Poiname><![CDATA[]]></Poiname>\n"
                       + "</SendLocationInfo>\n"
                       + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(slxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testTemplateFinshJobEventHandle() {
        log.info("====== WechatKernel#handle-selectlocationevent ======");
        String tmlfjxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182341</CreateTime>\n"
                          + "<MsgType><![CDATA[event]]></MsgType>\n"
                          + "<Event><![CDATA[TEMPLATESENDJOBFINISH]]></Event>\n"
                          + "<MsgID>200163836</MsgID>\n"
                          + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(tmlfjxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
        TmplFinshJobEvent tfje = new TmplFinshJobEvent();
        TmplFinshJobEvent.Status s = TmplFinshJobEvent.Status.success;
    }

    @Test
    public void testNewEventHandle() {
        log.info("====== WechatKernel#handle-viewevent ======");
        String newxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                        + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                        + "<CreateTime>1418182341</CreateTime>\n"
                        + "<MsgType><![CDATA[event]]></MsgType>\n"
                        + "<Event><![CDATA[location_receive]]></Event>\n"
                        + "<EventKey><![CDATA[6]]></EventKey>\n"
                        + "<SendLocationInfo><Location_X><![CDATA[23]]></Location_X>\n"
                        + "<Location_Y><![CDATA[113]]></Location_Y>\n"
                        + "<Scale><![CDATA[15]]></Scale>\n"
                        + "<Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>\n"
                        + "<Poiname><![CDATA[]]></Poiname>\n"
                        + "</SendLocationInfo>\n"
                        + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(newxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }


    class VoiceHandler extends WechatDefHandler {

        @Override
        public BasicMsg voice(VoiceMsg vm) {
            VoiceMsg vim = new VoiceMsg(vm);
            vim.setMediaId(vm.getMediaId());
            return vim;
        }

    }

    class MediaHandler extends WechatDefHandler {

        @Override
        public BasicMsg voice(VoiceMsg vm) {
            MusicMsg mm = new MusicMsg(vm);
            mm.setTitle("致爱 Your Song");
            mm.setThumbMediaId(vm.getMediaId());
            mm.setMusicUrl("http://y.qq.com/#type=song&mid=002IVyIU4093Xr&play=0");
            mm.setHQMusicUrl("http://y.qq.com/#type=song&mid=002IVyIU4093Xr&play=0");
            return mm;
        }

        @Override
        public BasicMsg video(VideoMsg vm) {
            VideoMsg vdm = new VideoMsg(vm);
            vdm.setTitle("习主席联合国峰会演讲");
            vdm.setDescription("习主席联合国峰会演讲");
            vdm.setMediaId("Hfdjlioieijl#22iojkljlkjlutfd");
            return super.video(vm);
        }

    }

}
