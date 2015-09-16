package io.github.elkan1788.mpsdk4j.core;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.util.StreamTool;
import io.github.elkan1788.mpsdk4j.vo.normal.ImageMessage;
import io.github.elkan1788.mpsdk4j.vo.normal.TextMessage;

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

    @Before
    public void init() {
        log.info("====== MessageHandlerTest ======");
        try {
            xmlParser = factory.newSAXParser();
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
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
        TextMessage tm = new TextMessage(msgHandler.getValues());
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
        ImageMessage im = new ImageMessage(msgHandler.getValues());
        log.info(im);
    }

}
