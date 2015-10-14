package io.github.elkan1788.mpsdk4j.core;

import org.junit.Before;
import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.TestSupport;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VoiceMsg;

/**
 * XmlMsgBuilder 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class XmlMsgBuilderTest extends TestSupport {

    private static final Log log = Logs.get();

    private String mpId;
    private String openId;
    private String mediaId;

    @Before
    public void init() {
        log.info("====== XmlMsgBuilderTest ======");
        mpId = _cr.get("mpId");
        openId = _cr.get("openId");
        mediaId = _cr.get("mediaId");
    }

    @Test
    public void testText() {
        log.info("====== XmlMsgBuilder#text ======");
        TextMsg tm = new TextMsg();
        tm.setFromUserName(mpId);
        tm.setToUserName(openId);
        tm.setContent("Hello world! 世界, 你好！");
        String txtXml = XmlMsgBuilder.create().text(tm).build();
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
        log.info(voiceXml);
    }

}
