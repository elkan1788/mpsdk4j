package org.elkan1788.osc.weixin.mp.util;

import com.alibaba.fastjson.JSON;
import org.elkan1788.osc.testunit.TestSupport;
import org.elkan1788.osc.weixin.mp.commons.WxApiUrl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求测试
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/12
 */
public class SimpleHttpReqTest extends TestSupport {

    private String mediaType = "image";

    @Before
    public void init() throws Exception {
        super.init();
    }

    @Test
    public void testGet() throws Exception {
        String url = String.format(WxApiUrl.ACCESS_TOKEN_API, appId, appSecret);
        System.out.println(url);
        String content = SimpleHttpReq.get(url);
        System.out.println(content);
    }

    @Test
    public void testPost() throws Exception {
        Map<String,Object> msg =  new HashMap<>();
        msg.put("touser", openId);
        msg.put("msgtype", mediaType);
        Map<String,String> image = new HashMap<>();
        image.put("media_id", mediaId);
        msg.put("image", image);
        String url = String.format(WxApiUrl.CUSTOM_MESSAGE_API, accessToken);
        System.out.println(url);
        System.out.println(JSON.toJSONString(msg));
        String content = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, JSON.toJSONString(msg));
        System.out.println(content);
    }

    @Test
    public void testUpload() throws Exception {
        File f = new File("D:/upload.jpg");
        String url = String.format(WxApiUrl.MEDIA_UP_API, mediaType, accessToken);
        System.out.println(url);
        String content = SimpleHttpReq.upload(url, f);
        System.out.println(content);
    }

    @Test
    public void testDownload() throws Exception {
        File f = new File("D:/download.jpg");
        String url = String.format(WxApiUrl.MEDIA_DL_API, accessToken, mediaId);
        System.out.println(url);
        SimpleHttpReq.download(url, f);
    }
}