package io.github.elkan1788.mpsdk4j.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import io.github.elkan1788.mpsdk4j.vo.APIResult;

/**
 * HttpTool 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public class HttpToolTest {

    private String openId;
    private String appId;
    private String appSecret;
    private String accessToken;
    private String mediaId;

    @Before
    public void init() {
        /*this.openId = _cr.get("openId");
        this.appId = _cr.get("appId");
        this.appSecret = _cr.get("appSecret");
        this.accessToken = _cr.get("accessToken");
        this.mediaId = _cr.get("mediaId");*/
    }

    // 注意access_token接口调用次数,建议跑一次就关闭
    @Ignore
    // @Before
    public void testGet() {
        if (Strings.isBlank(accessToken)) {
            String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                                       appId,
                                       appSecret);
            String content = HttpTool.get(url);
            assertNotNull(content);
            Map<String, String> data = (Map<String, String>) Json.fromJson(content);
            accessToken = data.get("access_token");
            assertNotNull(data.get("expires_in"));
        }
        assertNotNull(accessToken);
    }

    @Test
    public void testPost() {
        // 测试前请发先消息给公众号
        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s",
                                   accessToken);
        String body = "{\"touser\":\""
                      + openId
                      + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"Hello World!\r\n世界，你好！\"}}";
        String content = HttpTool.post(url, body);
        assertNotNull(content);
        assertEquals("{\"errcode\":0,\"errmsg\":\"ok\"}", content);

    }

    @Test
    public void testUpload() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s",
                                   accessToken,
                                   "image");
        File file = new File(this.getClass().getResource("/mpsdk4j-logo.png").getFile());
        String content = HttpTool.upload(url, file);
        assertNotNull(content);
        Map<String, String> nm = (Map<String, String>) Json.fromJson(content);
        assertNotNull(nm.get("created_at"));
        mediaId = nm.get("media_id");
        System.out.println(mediaId);
    }

    // @Ignore
    @Test
    public void testDownload() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s",
                                   accessToken,
                                   mediaId);
        Object tmp = HttpTool.download(url);
        try {
            if (tmp instanceof File) {
                Files.copyFile((File) tmp, new File("D:/tmp/mpsdk4j-logo.png"));
            }
            else {
                APIResult ar = APIResult.create((String) tmp);
                System.out.println(ar);
            }
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
    }

}
