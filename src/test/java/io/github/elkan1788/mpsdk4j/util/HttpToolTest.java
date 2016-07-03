package io.github.elkan1788.mpsdk4j.util;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import mockit.Expectations;
import mockit.NonStrictExpectations;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * HttpTool 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class HttpToolTest extends RunTestSupport {

    @Test
    public void testGet() {
        final String content = Json.toJson(accessToken, JsonFormat.compact());
        new NonStrictExpectations(HttpTool.class){
            {
                HttpTool.get(anyString);
                result = content;
            }
        };
        String tmp = HttpTool.get(url);
        assertNotNull(tmp);
        assertEquals(tmp, content);
    }

    @Test
    public void testPost() {
        String body = "{\"touser\":\"1234567\",\"msgtype\":\"text\",\"text\":{\"content\":\"Hello World!\r\n世界，你好！\"}}";
        final String content = "{\"errcode\":0,\"errmsg\":\"OK\"}";
        new Expectations(HttpTool.class){
            {
                HttpTool.post(anyString, anyString);
                result = content;
            }
        };
        String tmp = HttpTool.post(url, body);
        assertNotNull(tmp);
        assertEquals(tmp, content);
    }

    @Test
    public void testUpload() {
        final String content = "{\"errcode\":0,\"errmsg\":\"OK\",\"mediaid\":\"mediaId\"}";
        new Expectations(HttpTool.class){
            {
                HttpTool.upload(anyString, (File) any);
                result = content;
            }
        };

        String tmp = HttpTool.upload(url, imgMedia);
        assertNotNull(tmp);
        assertEquals(tmp, content);

    }

    @Test
    public void testDownload() {
        final File content = imgMedia;
        new Expectations(HttpTool.class){
            {
                HttpTool.download(anyString);
                result = content;
            }
        };
        Object tmp = HttpTool.download(url);
        assertNotNull(tmp);
        assertEquals(((File) tmp).getName(), content.getName());
    }

}
