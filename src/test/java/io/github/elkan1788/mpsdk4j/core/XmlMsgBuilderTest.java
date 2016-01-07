package io.github.elkan1788.mpsdk4j.core;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import io.github.elkan1788.mpsdk4j.vo.message.*;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * XmlMsgBuilder 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class XmlMsgBuilderTest extends RunTestSupport {

    private static final Log log = Logs.get();

    @BeforeClass
    public void init() {
        log.info("====== XmlMsgBuilderTest ======");
    }

    @Test
    public void testText() {
        log.info("====== XmlMsgBuilder#text ======");
        TextMsg tm = new TextMsg();
        tm.setFromUserName(mpId);
        tm.setToUserName(openId);
        tm.setContent("Hello world! 世界, 你好！");
        String txtXml = XmlMsgBuilder.create().text(tm).build();
        assertNotNull(txtXml);
        log.info(txtXml);
    }

    @Test
    public void testImage() {
        log.info("====== XmlMsgBuilder#image ======");
        ImageMsg im = new ImageMsg();
        im.setFromUserName(mpId);
        im.setToUserName(openId);
        im.setMediaId(mediaId);
        String imgXml = XmlMsgBuilder.create().image(im).build();
        assertNotNull(imgXml);
        log.info(imgXml);
    }

    @Test
    public void testVoice() {
        log.info("====== XmlMsgBuilder#voice ======");
        VoiceMsg vm = new VoiceMsg();
        vm.setFromUserName(mpId);
        vm.setToUserName(openId);
        vm.setMediaId(mediaId);
        String voiceXml = XmlMsgBuilder.create().voice(vm).build();
        assertNotNull(voiceXml);
        log.info(voiceXml);
    }

    @Test
    public void testVideo() {
        log.info("====== XmlMsgBuilder#video ======");
        VideoMsg vm = new VideoMsg();
        vm.setFromUserName(mpId);
        vm.setToUserName(openId);
        vm.setMediaId(mediaId);
        vm.setThumbMediaId(mediaId);
        vm.setTitle("视频消息");
        vm.setDescription("视频消息");
        String videoXml = XmlMsgBuilder.create().video(vm).build();
        assertNotNull(videoXml);
        log.info(videoXml);
    }


    @Test
    public void testNews() {
        log.info("====== XmlMsgBuilder#news ======");
        NewsMsg nm = new NewsMsg();
        nm.setFromUserName(mpId);
        nm.setToUserName(openId);
        nm.setArticles(articles);
        String newsXml = XmlMsgBuilder.create().news(nm).build();
        assertNotNull(newsXml);
        assertEquals(nm.getCount(), 2);
        log.info(newsXml);
    }

    @Test
    public void testMusic() {
        log.info("====== XmlMsgBuilder#music ======");
        String musicXml = XmlMsgBuilder.create().music(musicMsg).build();
        assertNotNull(musicXml);
        log.info(musicXml);
    }
}
