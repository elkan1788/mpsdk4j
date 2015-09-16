package io.github.elkan1788.mpsdk4j.core;

import org.junit.Before;
import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.vo.normal.ImageMessage;
import io.github.elkan1788.mpsdk4j.vo.normal.TextMessage;

/**
 * XmlMsgBuilder 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class XmlMsgBuilderTest {

    private static final Log log = Logs.get();

    private String openId = "oa_fdjsfiosfjslkeow";
    private String mpId = "gh_ewjisfjlkdjsfjf";

    @Before
    public void init() {
        log.info("====== XmlMsgBuilderTest ======");
    }

    @Test
    public void testText() {
        log.info("====== XmlMsgBuilder#text ======");
        TextMessage tm = new TextMessage();
        tm.setFromUserName(mpId);
        tm.setToUserName(openId);
        tm.setContent("Hello world! 世界, 你好！");
        String txtXml = XmlMsgBuilder.create().text(tm).build();
        log.info(txtXml);
    }

    @Test
    public void testImage() {
        log.info("====== XmlMsgBuilder#image ======");
        ImageMessage im = new ImageMessage();
        im.setFromUserName(mpId);
        im.setToUserName(openId);
        im.setMediaId("yfdy-fdOdkfdsFfdsfs323");
        String imgXml = XmlMsgBuilder.create().image(im).build();
        log.info(imgXml);
    }

}
