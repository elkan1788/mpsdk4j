package io.github.elkan1788.mpsdk4j.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;

import io.github.elkan1788.mpsdk4j.vo.ApiResult;

/**
 * HttpTool 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@SuppressWarnings("unchecked")
public class HttpToolTest {

    private String openId = "";
    private String appId = "";
    private String appSecret = "";
    private String accessToken = "";
    private String mediaId = "";

    // 注意access_token接口调用次数,建议跑一次就关闭
    @Ignore
    // @Before
    public void testGet() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                                   appId,
                                   appSecret);
        String content = HttpTool.get(url);
        Assert.assertNotNull(content);
        Map<String, String> data = (Map<String, String>) Json.fromJson(content);
        accessToken = data.get("access_token");
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(data.get("expires_in"));
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
        Assert.assertNotNull(content);
        Assert.assertEquals("{\"errcode\":0,\"errmsg\":\"ok\"}", content);

    }

    @Test
    public void testUpload() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s",
                                   accessToken,
                                   "image");
        File file = new File(this.getClass().getResource("/mpsdk4j-logo.png").getFile());
        String content = HttpTool.upload(url, file);
        Assert.assertNotNull(content);
        Map<String, String> nm = (Map<String, String>) Json.fromJson(content);
        Assert.assertNotNull(nm.get("created_at"));
        mediaId = nm.get("media_id");
        System.out.println(mediaId);
    }

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
                ApiResult ar = ApiResult.create((String) tmp);
                System.out.println(ar);
            }
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
    }

}
