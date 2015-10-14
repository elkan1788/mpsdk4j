package io.github.elkan1788.mpsdk4j.core;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import io.github.elkan1788.mpsdk4j.api.APITestSupport;
import io.github.elkan1788.mpsdk4j.vo.api.Template;
import io.github.elkan1788.mpsdk4j.vo.message.Article;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.MusicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.NewsMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VideoMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VoiceMsg;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * JsonBuilder 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class JsonMsgBuilderTest extends APITestSupport {

    private static final Log log = Logs.get();

    @Before
    public void init() {
        log.info("====== JsonMsgBuilderTest ======");
        super.init();
    }

    @Test
    public void testText() {
        log.info("====== JsonMsgBuilder#text ======");
        TextMsg tm = new TextMsg();
        tm.setToUserName(openId);
        tm.setContent("Hello world! 世界,你好！");
        String textJson = JsonMsgBuilder.create().text(tm).build();
        assertNotNull(textJson);
        assertNotEquals(-1, textJson.indexOf("text"));
        assertNotNull(Json.fromJson(textJson));
        log.info(textJson);
    }

    @Test
    public void testImage() {
        log.info("====== JsonMsgBuilder#image ======");
        ImageMsg im = new ImageMsg();
        im.setToUserName(openId);
        im.setMediaId(mediaId);
        String imgJson = JsonMsgBuilder.create().image(im).build();
        assertNotNull(imgJson);
        assertNotEquals(-1, imgJson.indexOf("image"));
        assertNotNull(Json.fromJson(imgJson));
        log.info(imgJson);
    }

    @Test
    public void testVoice() {
        log.info("====== JsonMsgBuilder#voice ======");
        VoiceMsg vom = new VoiceMsg();
        vom.setToUserName(openId);
        vom.setMediaId(mediaId);
        String voiceJson = JsonMsgBuilder.create().voice(vom).build();
        assertNotNull(voiceJson);
        assertNotEquals(-1, voiceJson.indexOf("voice"));
        assertNotNull(Json.fromJson(voiceJson));
        log.info(voiceJson);
    }

    @Test
    public void testVideo() {
        log.info("====== JsonMsgBuilder#video ======");
        VideoMsg vim = new VideoMsg();
        vim.setToUserName(openId);
        vim.setMediaId(mediaId);
        vim.setThumbMediaId(mediaId);
        vim.setTitle("Video message");
        vim.setDescription("Video message.");
        String videoJson = JsonMsgBuilder.create().video(vim).build();
        assertNotNull(videoJson);
        assertNotEquals(-1, videoJson.indexOf("video"));
        assertNotNull(Json.fromJson(videoJson));
        log.info(videoJson);
    }

    @Test
    public void testMusic() {
        log.info("====== JsonMsgBuilder#music ======");
        MusicMsg mm = new MusicMsg();
        mm.setToUserName(openId);
        mm.setThumbMediaId(mediaId);
        mm.setTitle("致爱 Your Song");
        mm.setDescription("Music message.");
        mm.setMusicUrl("http://y.qq.com/#type=song&mid=002IVyIU4093Xr&play=0");
        mm.setHQMusicUrl("http://y.qq.com/#type=song&mid=002IVyIU4093Xr&play=0");
        String musicJson = JsonMsgBuilder.create().music(mm).build();
        assertNotNull(musicJson);
        assertNotEquals(-1, musicJson.indexOf("music"));
        assertNotNull(Json.fromJson(musicJson));
        log.info(musicJson);
    }

    @Test
    public void testNews() {
        log.info("====== JsonMsgBuilder#news ======");
        NewsMsg nm = new NewsMsg();
        nm.setToUserName(openId);
        Article art = new Article();
        art.setTitle("Wechat news message.");
        art.setDescription("Wechat news message Description.");
        art.setPicUrl("http://www.baidu.com");
        art.setUrl("http://www.baidu.com");
        nm.setArticles(Arrays.asList(art));
        String newsJson = JsonMsgBuilder.create().news(nm).build();
        assertNotNull(newsJson);
        assertNotEquals(-1, newsJson.indexOf("articles"));
        assertNotNull(Json.fromJson(newsJson));
        log.info(newsJson);
    }

    @Test
    public void testTemplate() {
        log.info("====== JsonMsgBuilder#template ======");
        Template tmp = new Template();
        tmp.setColor("#ff0000");
        tmp.setName("title");
        tmp.setValue("Wechat template message.");

        String tmpJson = JsonMsgBuilder.create()
                                       .template(openId,
                                                 tmplId,
                                                 "#ff0000",
                                                 "http://www.baidu.com",
                                                 tmp)
                                       .build();
        assertNotNull(tmpJson);
        assertNotEquals(-1, tmpJson.indexOf("data"));
        assertNotNull(Json.fromJson(tmpJson));
        log.info(tmpJson);
    }

}
