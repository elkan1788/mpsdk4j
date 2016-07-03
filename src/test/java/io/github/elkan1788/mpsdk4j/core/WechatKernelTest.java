package io.github.elkan1788.mpsdk4j.core;

import java.util.HashMap;
import java.util.Map;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import org.nutz.lang.random.R;
import org.nutz.lang.random.StringGenerator;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.util.StreamTool;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;
import io.github.elkan1788.mpsdk4j.vo.message.BasicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.MusicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VideoMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VoiceMsg;
import io.github.elkan1788.mpsdk4j.vo.push.SentAllJobEvent;
import io.github.elkan1788.mpsdk4j.vo.push.SentTmlJobEvent;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WechatKernelTest extends RunTestSupport {

    private static final Log log = Logs.get();

    private MPAccount mpAct;
    private Map<String, String[]> data;

    @BeforeTest
    public void init() {
        log.info("====== WechatKernelTest ======");
        data = new HashMap<String, String[]>();
        data.put("signature", new String[]{"af06ae6995cb1979e465d3b8015509ad61bb7204"});
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
        assertTrue(echo.equals(data.get("echostr")[0]));
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
        assertTrue(respxml.equals("success"));
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
    public void testSentTemplateFinshJobEventHandle() {
        log.info("====== WechatKernel#handle-senttemplateevent ======");
        String tmlfjxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182341</CreateTime>\n"
                          + "<MsgType><![CDATA[event]]></MsgType>\n"
                          + "<Event><![CDATA[TEMPLATESENDJOBFINISH]]></Event>\n"
                          + "<MsgID>200163836</MsgID>\n"
                          + "<Status><![CDATA[sendsuccess]]></Status>\n"
                          + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(tmlfjxml));
        assertNotNull(respxml);
        assertTrue(respxml.equals("success"));
        log.info(respxml);
        wk = new WechatKernel(mpAct, new PushEventHandler(), data);
        respxml = wk.handle(StreamTool.toStream(tmlfjxml));
        assertNotNull(respxml);
        assertTrue(respxml.equals("success"));
        log.info(respxml);
    }

    @Test
    public void testSentAllFinshJobEventHandle() {
        log.info("====== WechatKernel#handle-sentallevent ======");
        String tmlfjxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182341</CreateTime>\n"
                          + "<MsgType><![CDATA[event]]></MsgType>\n"
                          + "<Event><![CDATA[MASSSENDJOBFINISH]]></Event>\n"
                          + "<MsgID>200163836</MsgID>\n"
                          + "<Status><![CDATA[sendsuccess]]></Status>\n"
                          + "<TotalCount>100</TotalCount>\n"
                          + "<FilterCount>80</FilterCount>\n"
                          + "<SentCount>75</SentCount>\n"
                          + "<ErrorCount>5</ErrorCount>\n"
                          + "</xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(tmlfjxml));
        assertNotNull(respxml);
        assertTrue(respxml.equals("success"));
        log.info(respxml);
        wk = new WechatKernel(mpAct, new PushEventHandler(), data);
        respxml = wk.handle(StreamTool.toStream(tmlfjxml));
        assertNotNull(respxml);
        assertTrue(respxml.equals("success"));
        log.info(respxml);
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

   // @Test(expected = RuntimeException.class)
    public void testErrorXmlHandle() {
        log.info("====== WechatKernel#handle-viewevent ======");
        String newxml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                        + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                        + "<CreateTime>1418182341</CreateTime>\n"
                        + "<MsgType><![CDATA[text]]></MsgType>\n"
                        + "<Content><![CDATA[location_receive]]></Content>\n"
                        + "/xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(newxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }

    //@Test(expected = RuntimeException.class)
    public void testDecryptMsgFailure() {
        log.info("====== WechatKernel#encryptmsg ======");
        data.clear();
        data.put("nonce", new String[]{"xxxxxx"});
        data.put("timestamp", new String[]{"1409304348"});
        data.put("msg_signature", new String[]{"9f8883ff0676a51299747fc2d4b2e6a4be9207cb"});
        data.put("encrypt_type", new String[]{"aes"});
        mpAct.setAESKey("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG");
        mpAct.setAppId("wxb11529c136998cb6");
        mpAct.setToken("pamtest");
        String encryptxml = "<xml><Encrypt><![CDATA[8tliIQIIrfnrQe3t/TMenAG7LBnLHiK584YWLHuYxw/AW+PlKe9yNl5/9Gj+M3wgoTzdz+YT9ZiouUS4/gY2sCthFagSugVNP59G8CHwGPk+WP13LvFr/Xr3mYACI/qB9SsLA0z08i1FwXga85Xh/kBZO7yUXLQIcpnZEiCjuQDngfS+hjtp9nCTz2W2CGqV0jk/6oIUD1TB6aAQLyh05/12WF82swc3ANQdM1SEhFr/9gQriTYh/4Pt0CUlPR9ZJgDC8ci4C33kpDQhqSC79u/UzHusW9zigjVsb4eDm9BrPNPAnXRYc/0MyBlGYtdtsnda+QdvAeR2UZwcec3Ro5QUvdE9BSNeAC+Rg9G+bV0/Yug3CT4ge9gkjZp1cqYDUnE3f3aSoZ0OOpNL0dhg21G1rGeOuDkT49PjRw2D9NUIIPZrHqQeCsQpQ3t2vrIUwfbUyqU3T0MpdqiceZWRwWTm7HH7DK9XbHI6svpU3cw49rwLoe3KUq/T2V/LjFENAqCX708ZgDS1f4fvlZg+Yc7BmMGZrpHr7WYTUNUa4u3uoUNj9fJur23BlIlmCW6/ve/wJK6qKYh+KI4sZluGc1pqERPmx/23OFS8ip0JSt4jrJb5QEd445BoAQvoY6Ii]]></Encrypt><MsgSignature><![CDATA[cb88d9fc51272bfbc6f0be9ff96874293a435728]]></MsgSignature><TimeStamp>1409304348</TimeStamp><Nonce><![CDATA[xxxxxx]]></Nonce></xml>";
        WechatKernel wk = new WechatKernel(mpAct, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(encryptxml));
        assertNotNull(respxml);
        assertFalse(respxml.equals("success"));
        log.info(respxml);
    }
    
    @Test
    public void testDecryptMsg() {
        log.info("====== WechatKernel#encryptmsg ======");
        data.clear();
        data.put("nonce", new String[]{"xxxxxx"});
        data.put("timestamp", new String[]{"1409304348"});
        data.put("msg_signature", new String[]{"92aa0aeee3943305c0127627dad968bd73e32428"});
        data.put("encrypt_type", new String[]{"aes"});
        MPAccount mpact = new MPAccount();
        mpact.setAESKey("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG");
        mpact.setAppId("wxb11529c136998cb6");
        mpact.setToken("pamtest");
        String encryptxml = "<xml><Encrypt><![CDATA[pGaTAHG6ATJgrx2E+qL6koE2BDyy8F7DM80suSNFWvGtOUYUhPPlX+8pkQ7K3bwy/5EQfsJFysPYpUz5Hv5IFkBQIgWclg3Y0I8VxSTl8mxriTf4a4hO6iqLRLcq+DoicuxtgiMSVs/k4zpVZnLjcooiWjf1xRj4nxl2Bnq8mL54P0nljMZkjhtofnFqJdfCDA9cZyIHLzF4zN1uxDHSXALaEkgEMPzUdCHAxU6UvnSUuZ0qBFn2b9b9N7NgZGsd+L6AxJb6f8ouzb4N07XryO9O9omboNO1ZvEWru9x1Em78YYBtXhepn3q00HsQWlSq++IteRh+0laLl0RO8aNXG4jvPdaCSXysJFVN0eqzfimQzzLaxdY4PMQ+4pt9mOxdUfbvZG3zrug3rDkqNQrU4mnc/BXrdU4+E/7wfUMYlVzPwcfxvDyFbBywCKofeOUxohNS2BSipkpfCJ07/gis/6iwrchJflgwGR8r3/Y17SIzeAAeUdau/tTYTgQIiPa6gY/A68O4KowHzpcsGLpJYhDu4XsEnK+sxNOFqTHV16UAkeWHumURHJ7Wnw2D7hz/vhqMLGqZ/VH2qbDKaQJ43fNR93rGEGRdwRW6xuw0l1Oy9WoVDNxJqJEiEo+AgYJEzDPuY4i3pz8ewUGEZH9FFiw7JH0td5qlHONVHid2yZqN2QHijzdch4Fhfg5yTvjO1zxKR+wy9oigsv0VEfllQ==]]></Encrypt><MsgSignature><![CDATA[92aa0aeee3943305c0127627dad968bd73e32428]]></MsgSignature><TimeStamp>1409304348</TimeStamp><Nonce><![CDATA[xxxxxx]]></Nonce></xml>";
        WechatKernel wk = new WechatKernel(mpact, new WechatDefHandler(), data);
        String respxml = wk.handle(StreamTool.toStream(encryptxml));
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

    class PushEventHandler extends WechatDefHandler {

        @Override
        public void eSentTmplJobFinish(SentTmlJobEvent stje) {
            log.info(stje);
        }

        @Override
        public void eSentAllJobFinish(SentAllJobEvent saje) {
            log.info(saje);
        }

    }

}
