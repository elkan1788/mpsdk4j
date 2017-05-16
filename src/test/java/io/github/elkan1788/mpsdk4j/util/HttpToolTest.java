package io.github.elkan1788.mpsdk4j.util;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.runner.RunWith;
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
public class HttpToolTest {

    public void MockUpHttpGet(final String result){

        new MockUp<HttpTool>(){

            @Mock
            public String get(String url) {
                return result;
            }

        };
    }

    /*@Test
    public void testMocked() throws Exception {
        new MockUp<HttpTool>() {

            @Mock
            public String get(String url) {
                return "get";
            }

            @Mock
            public String post(String url, String body) {
                return "post";
            }
        };

        assertEquals("get", HttpTool.get("test"));
        assertEquals("post", HttpTool.post("test","teset"));

    }*/

    @Test
    public void testGet() {
        final String content = "{\"status\":0, \"msg\":\"ok\"}";
        new Expectations(HttpTool.class){
            {
//                HttpTool.get(anyString);
                Deencapsulation.invoke(HttpTool.class, "get", anyString);
                result = content;
            }
        };
        String tmp = HttpTool.get("http://www.google.com");
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
        String tmp = HttpTool.post("http://www.google.com", body);
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

        String tmp = HttpTool.upload("http://www.google.com", new File(""));
        assertNotNull(tmp);
        assertEquals(tmp, content);

    }

    @Test
    public void testDownload() {
        final File content = new File("D:/newpswd.txt");
        new Expectations(HttpTool.class){
            {
                HttpTool.download(anyString);
                result = content;
            }
        };
        Object tmp = HttpTool.download("http://www.google.com");
        assertNotNull(tmp);
        assertEquals(((File) tmp).getName(), content.getName());
    }

}
