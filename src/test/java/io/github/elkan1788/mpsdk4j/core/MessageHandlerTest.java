package io.github.elkan1788.mpsdk4j.core;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.util.StreamTool;
import io.github.elkan1788.mpsdk4j.vo.event.SendLocationInfoEvent;
import io.github.elkan1788.mpsdk4j.vo.event.SendPhotosEvent;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.LinkMsg;
import io.github.elkan1788.mpsdk4j.vo.message.LocationMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VideoMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VoiceMsg;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

/**
 * 测试XML读取
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class MessageHandlerTest {

    private static final Log log = Logs.get();

    private SAXParserFactory factory = SAXParserFactory.newInstance();
    private SAXParser xmlParser;
    private MessageHandler msgHandler = new MessageHandler();

//    @Before
    public void init() {
        log.info("====== MessageHandlerTest ======");
        try {
            xmlParser = factory.newSAXParser();
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
    }

    @Test
    public void testTextMessage() throws Exception {
        log.info("====== MessageHandler#text ======");
        String textXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                         + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                         + "<CreateTime>1418182341</CreateTime>\n"
                         + "<MsgType><![CDATA[text]]></MsgType>\n"
                         + "<Content><![CDATA[你好!]]></Content>\n"
                         + "<MsgId>6091046778677430</MsgId>\n"
                         + "</xml>";

        xmlParser.parse(StreamTool.toStream(textXml), msgHandler);
        TextMsg tm = new TextMsg(msgHandler.getValues());
        assertNotNull(tm);
        log.info(tm);
    }

    @Test
    public void testImageMessage() throws Exception {
        log.info("====== MessageHandler#image ======");
        String imgXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                        + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                        + "<CreateTime>1418182361</CreateTime>\n"
                        + "<MsgType><![CDATA[image]]></MsgType>\n"
                        + "<PicUrl><![CDATA[http://www.baidu.com]]></PicUrl>\n"
                        + "<MediaId><![CDATA[Fjkodsfjli32ijdsljoxn]]></MediaId>\n"
                        + "<MsgId>6091046778677460</MsgId>\n"
                        + "</xml>";

        xmlParser.parse(StreamTool.toStream(imgXml), msgHandler);
        ImageMsg im = new ImageMsg(msgHandler.getValues());
        assertNotNull(im);
        log.info(im);
    }

    @Test
    public void testVoiceMessage() throws Exception {
        log.info("====== MessageHandler#voice ======");
        String voiceXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182361</CreateTime>\n"
                          + "<MsgType><![CDATA[voice]]></MsgType>\n"
                          + "<MediaId><![CDATA[Fjkodsfjli32ijdsljoxn]]></MediaId>\n"
                          + "<Format><![CDATA[arm]]></Format>\n"
                          + "<Recognition><![CDATA[世界你好!]]></Recognition>\n"
                          + "<MsgId>6091046778677460</MsgId>\n"
                          + "</xml>";
        xmlParser.parse(StreamTool.toStream(voiceXml), msgHandler);
        VoiceMsg vm = new VoiceMsg(msgHandler.getValues());
        assertNotNull(vm);
        log.info(vm);
    }

    @Test
    public void testVideoMessage() throws Exception {
        log.info("====== MessageHandler#video ======");
        String videoXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182361</CreateTime>\n"
                          + "<MsgType><![CDATA[video]]></MsgType>\n"
                          + "<MediaId><![CDATA[Fjkodsfjli32ijdsljoxn]]></MediaId>\n"
                          + "<ThumbMediaId><![CDATA[Fjkodsfjli32ijdsljoxn]]></ThumbMediaId>\n"
                          + "<MsgId>6091046778677460</MsgId>\n"
                          + "</xml>";
        xmlParser.parse(StreamTool.toStream(videoXml), msgHandler);
        VideoMsg vm = new VideoMsg(msgHandler.getValues());
        assertNotNull(vm);
        log.info(vm);
    }

    @Test
    public void testShortVideoMessage() throws Exception {
        log.info("====== MessageHandler#shortvideo ======");
        String videoXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182361</CreateTime>\n"
                          + "<MsgType><![CDATA[shortvideo]]></MsgType>\n"
                          + "<MediaId><![CDATA[Fjkodsfjli32ijdsljoxn]]></MediaId>\n"
                          + "<ThumbMediaId><![CDATA[Fjkodsfjli32ijdsljoxn]]></ThumbMediaId>\n"
                          + "<MsgId>6091046778677460</MsgId>\n"
                          + "</xml>";
        xmlParser.parse(StreamTool.toStream(videoXml), msgHandler);
        VideoMsg vm = new VideoMsg(msgHandler.getValues());
        assertNotNull(vm);
        log.info(vm);
    }

    @Test
    public void testLocationMessage() throws Exception {
        log.info("====== MessageHandler#location ======");
        String videoXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182361</CreateTime>\n"
                          + "<MsgType><![CDATA[shortvideo]]></MsgType>\n"
                          + "<Location_X>23.134521</Location_X>\n"
                          + "<Location_Y>113.358803</Location_Y>\n"
                          + "<Scale>20</Scale>\n"
                          + "<Label><![CDATA[中国上海]]></Label>\n"
                          + "<MsgId>6091046778677460</MsgId>\n"
                          + "</xml>";
        xmlParser.parse(StreamTool.toStream(videoXml), msgHandler);
        LocationMsg lm = new LocationMsg(msgHandler.getValues());
        assertNotNull(lm);
        log.info(lm);
    }

    @Test
    public void testLinkMessage() throws Exception {
        log.info("====== MessageHandler#link ======");
        String videoXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182361</CreateTime>\n"
                          + "<MsgType><![CDATA[shortvideo]]></MsgType>\n"
                          + "<Title><![CDATA[Google]]></Title>\n"
                          + "<Description><![CDATA[google website]]></Description>\n"
                          + "<Url><![CDATA[https://www.google.com]]></Url>\n"
                          + "<MsgId>6091046778677460</MsgId>\n"
                          + "</xml>";
        xmlParser.parse(StreamTool.toStream(videoXml), msgHandler);
        LinkMsg lm = new LinkMsg(msgHandler.getValues());
        assertNotNull(lm);
        log.info(lm);
    }

    @Test
    public void testSendLocationMessage() throws Exception {
        log.info("====== MessageHandler#sendlocation ======");
        String videoXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182361</CreateTime>\n"
                          + "<MsgType><![CDATA[shortvideo]]></MsgType>\n"
                          + "<Event><![CDATA[location_select]]></Event>\n"
                          + "<EventKey><![CDATA[6]]></EventKey>\n"
                          + "<SendLocationInfo><Location_X><![CDATA[23]]></Location_X>\n"
                          + "<Location_Y><![CDATA[113]]></Location_Y>\n"
                          + "<Scale><![CDATA[15]]></Scale>\n"
                          + "<Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>\n"
                          + "<Poiname><![CDATA[朋友圈]]></Poiname>\n"
                          + "</SendLocationInfo>\n"
                          + "</xml>";
        xmlParser.parse(StreamTool.toStream(videoXml), msgHandler);
        SendLocationInfoEvent slife = new SendLocationInfoEvent(msgHandler.getValues());
        assertNotNull(slife);
        log.info(slife);
    }

    @Test
    public void testSendPicsMessage() throws Exception {
        log.info("====== MessageHandler#sendpics ======");
        String videoXml = "<xml><ToUserName><![CDATA[gh_15d5865s2c]]></ToUserName>\n"
                          + "<FromUserName><![CDATA[oa_H3239023j32324243]]></FromUserName>\n"
                          + "<CreateTime>1418182361</CreateTime>\n"
                          + "<MsgType><![CDATA[shortvideo]]></MsgType>\n"
                          + "<Event><![CDATA[pic_photo_or_album]]></Event>\n"
                          + "<EventKey><![CDATA[6]]></EventKey>\n"
                          + "<SendPicsInfo><Count>2</Count>\n"
                          + "<PicList><item><PicMd5Sum><![CDATA[5a75aaca956d97be686719218f275c6b]]></PicMd5Sum>\n"
                          + "</item><item><PicMd5Sum><![CDATA[5a75aaca956d97be686719218f275c6b]]></PicMd5Sum>\n"
                          + "</item></PicList></SendPicsInfo>\n"
                          + "</xml>";
        xmlParser.parse(StreamTool.toStream(videoXml), msgHandler);
        SendPhotosEvent spe = new SendPhotosEvent(msgHandler.getValues());
        assertNotNull(spe);
        log.info(spe);
    }
}
