package org.elkan1788.osc.weixin.mp.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;

/**
 * HTTP请求实现
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/7
 * @version 1.0.0
 */
public class SimpleHttpReq {

    public static String TEXT_PLAIN = "text/plain";

    public static String TEXT_HTML = "text/html";

    public static String TEXT_XML = "text/xml";

    public static String APPLICATION_XML = "application/xml";

    public static String APPLICATION_JSON = "application/json";

    private static StrResponseHandler respHandler = new StrResponseHandler();

    /**
     * GET请求
     *
     * @param url 请求地址
     * @return 响应内容
     * @throws java.io.IOException
     */
    public static String get(String url) throws IOException {
        String content = Request.Get(url).execute().handleResponse(respHandler);
        return content;
    }

    /**
     * POST请求
     *
     * @param url 请求地址
     * @param contentType 请求体类型[text, xml, json, html]
     * @param body 请求体
     * @return 响应内容
     * @throws java.io.IOException
     */
    public static String post(String url,
                              String contentType,
                              String body) throws IOException {
        String content = Request.Post(url)
                .bodyString(body, ContentType.create(contentType))
                .execute().handleResponse(respHandler);
        return content;
    }

    /**
     * 上传文件
     *
     * @param url 请求地址
     * @param file 上传文件
     * @return 响应内容
     * @throws java.io.IOException
     */
    public static String upload(String url, File file) throws IOException {
        HttpEntity media = MultipartEntityBuilder.create()
                .addPart("media", new FileBody(file)).build();
        String content = Request.Post(url).body(media)
                .execute().handleResponse(respHandler);
        return content;
    }

    /**
     * 下载文件
     *
     * @param url   请求地址
     * @param file 文件保存位置
     * @throws java.io.IOException
     */
    public static void download(String url, File file) throws IOException {
        Request.Get(url).execute().saveContent(file);
    }
}
