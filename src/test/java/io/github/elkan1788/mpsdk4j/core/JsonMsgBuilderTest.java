package io.github.elkan1788.mpsdk4j.core;

import org.junit.Before;
import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.vo.normal.ImageMessage;
import io.github.elkan1788.mpsdk4j.vo.normal.TextMessage;

/**
 * JsonBuilder 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class JsonMsgBuilderTest {

    private static final Log log = Logs.get();

    private String openId = "oa_fdsjkjio2324234";

    @Before
    public void init() {
        log.info("====== JsonMsgBuilderTest ======");
    }

    @Test
    public void testText() {
        log.info("====== JsonMsgBuilder#text ======");
        TextMessage tm = new TextMessage();
        tm.setToUserName(openId);
        tm.setContent("Hello world! 世界,你好！");
        String txtJson = JsonMsgBuilder.create().text(tm).build();
        log.info(txtJson);
    }

    @Test
    public void testImage() {
        log.info("====== JsonMsgBuilder#image ======");
        ImageMessage im = new ImageMessage();
        im.setToUserName(openId);
        im.setMediaId("Mjdfjio3ioxjflkjeadsiosdjfs-32jk");
        String imgJson = JsonMsgBuilder.create().image(im).build();
        log.info(imgJson);
    }

}
