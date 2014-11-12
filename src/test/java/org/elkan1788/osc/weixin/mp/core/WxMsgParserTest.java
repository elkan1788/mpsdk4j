package org.elkan1788.osc.weixin.mp.core;

import org.elkan1788.osc.testunit.TestSupport;
import org.elkan1788.osc.weixin.mp.util.StreamTool;
import org.elkan1788.osc.weixin.mp.vo.Article;
import org.elkan1788.osc.weixin.mp.vo.OutPutMsg;
import org.elkan1788.osc.weixin.mp.vo.ReceiveMsg;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信消息解析测试
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/12
 */
public class WxMsgParserTest extends TestSupport {

    private String textXml = "<xml>" +
            " <ToUserName><![CDATA[toUser]]></ToUserName>" +
            " <FromUserName><![CDATA[fromUser]]></FromUserName> " +
            " <CreateTime>1348831860</CreateTime>" +
            " <MsgType><![CDATA[text]]></MsgType>" +
            " <Content><![CDATA[this is a test]]></Content>" +
            " <MsgId>1234567890123456</MsgId>" +
            " </xml>";

    private String locationXml = "<xml>\n" +
            "<ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "<FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
            "<CreateTime>1351776360</CreateTime>\n" +
            "<MsgType><![CDATA[location]]></MsgType>\n" +
            "<Location_X>23.134521</Location_X>\n" +
            "<Location_Y>113.358803</Location_Y>\n" +
            "<Scale>20</Scale>\n" +
            "<Label><![CDATA[位置信息]]></Label>\n" +
            "<MsgId>1234567890123456</MsgId>\n" +
            "</xml>";

    private String linkXml = "<xml>\n" +
            "<ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "<FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
            "<CreateTime>1351776360</CreateTime>\n" +
            "<MsgType><![CDATA[link]]></MsgType>\n" +
            "<Title><![CDATA[公众平台官网链接]]></Title>\n" +
            "<Description><![CDATA[公众平台官网链接]]></Description>\n" +
            "<Url><![CDATA[url]]></Url>\n" +
            "<MsgId>1234567890123456</MsgId>\n" +
            "</xml> ";

    private String eventLocation = "<xml>\n" +
            "<ToUserName><![CDATA[toUser]]></ToUserName>\n" +
            "<FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
            "<CreateTime>123456789</CreateTime>\n" +
            "<MsgType><![CDATA[event]]></MsgType>\n" +
            "<Event><![CDATA[LOCATION]]></Event>\n" +
            "<Latitude>23.137466</Latitude>\n" +
            "<Longitude>113.352425</Longitude>\n" +
            "<Precision>119.385040</Precision>\n" +
            "</xml>";

    private String tempXml = "<xml>\n" +
            "<ToUserName><![CDATA[gh_7f083739789a]]></ToUserName>\n" +
            "<FromUserName><![CDATA[oia2TjuEGTNoeX76QEjQNrcURxG8]]></FromUserName>\n" +
            "<CreateTime>1395658920</CreateTime>\n" +
            "<MsgType><![CDATA[event]]></MsgType>\n" +
            "<Event><![CDATA[TEMPLATESENDJOBFINISH]]></Event>\n" +
            "<MsgID>200163836</MsgID>\n" +
            "<Status><![CDATA[success]]></Status>\n" +
            "</xml>";

    String timestamp = "1415069229";
    String nonce = "1830582838";
    String msgSignature = "6aeedb19f51a1fb43c1472f56f2bf343f897c23c";

    private OutPutMsg outPutMsg;

    @Before
    public void init() throws Exception {
       super.init();

        List<Article> articles = new ArrayList<>();
        for (int i=1; i<13; i++){
            Article a = new Article("title-"+i, "description-"+i, "picurl-"+i, "url-i"+i);
            articles.add(a);
        }
        outPutMsg = new OutPutMsg(openId, mpId, "news");
        outPutMsg.setArticles(articles);
    }

    @Test
    public void testConvert2VO() throws Exception {
        long t1 = System.currentTimeMillis();
        WxMsgParser.enableDev();
        ReceiveMsg rm = WxMsgParser.convert2VO(StreamTool.toStream(textXml), null, null, null);
        long t2 = System.currentTimeMillis();
        System.out.println((t2-t1));
        System.out.println(rm);
        System.out.println("*************************************");

        WxMsgParser.enableAES();
        WxMsgParser.addMpAct(mpAct);

        String message = "<xml>    <ToUserName><![CDATA[gh_32a4c979bbeb]]></ToUserName>    <Encrypt><![CDATA[fvno1eOdRyWdFtxTN4s2xsStyHjmsruvj9vBQFj6Dl/8D1QnJ3MM7jFn7S5z2GRq1/Sck1EAQv3sU1x1yC9IuAh/lrH477pCDeXK0beH5GcP/Hy3XnHnzXn2R98ANVQ8zQ0vdzaPS9c+dsK0w0oAbkKK3VBBc6T4WGod6fovW9kv/CV5uERzRF4MXQGWoQnq5gcOhcFqO3e/KK6bIa3Y3H1kC9Y29ZSvnSprSq2nO6nKxEUPWYPv4YdkS5JZnzrnJf8fGxWTmpTteX3bV3feSmwwd+DqqguI020NC3A2EyQGwIyPkynbjPNHxEsBoXLrPr6G6QL/Bt4teXU1Lh7/jGh6rF0ScDrv0pmaaKh1K1H/9XrXmORUPfrobK3lKWkmvpNfqt/1gWVWhPQx+UnGuLkPAluCQ1xL7zeJtEy7DSrKmMh6nF2AGFf3HfnDiKrdG6fhc6vlCHsht9XNIuSa8w==]]></Encrypt></xml>";
        t1 = System.currentTimeMillis();
        rm = WxMsgParser.convert2VO(StreamTool.toStream(message),msgSignature, timestamp, nonce);
        t2 = System.currentTimeMillis();
        System.out.println(rm);
        System.out.println((t2-t1));
    }

    @Test
    public void testReply() throws Exception {
        WxMsgParser.enableDev();
        WxMsgParser.enableAES();
        WxMsgParser.addMpAct(mpAct);
        long t1 = System.currentTimeMillis();
        String xml = WxMsgParser.reply(outPutMsg, msgSignature, timestamp, nonce);
        long t2 = System.currentTimeMillis();
        System.out.println("1:" + (t2 - t1));
        System.out.println(xml);
        System.out.println("************************");
        WxMsgParser.disableDev();
        WxMsgParser.disableAES();
        long t = 0l;
        for (int i=0; i<101; i++) {
            t1 = System.currentTimeMillis();
            xml = WxMsgParser.reply(outPutMsg, msgSignature, timestamp, nonce);
            t2 = System.currentTimeMillis();
            t += (t2-t1);
            System.out.println(i+":\t"+(t2-t1));
            Thread.sleep(10);
        }
        System.out.println(xml);
        System.out.println("avg:"+((double) t / 101.0));
    }
}