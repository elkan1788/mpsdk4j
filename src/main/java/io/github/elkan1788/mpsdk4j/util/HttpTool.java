package io.github.elkan1788.mpsdk4j.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.nutz.http.Http;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.http.sender.FilePostSender;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * Http工具包，用于访问API，上传或下载微信素材
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
// TODO Http.disableJvmHttpsCheck();
public class HttpTool {

    private static final Log log = Logs.get();

    private static final int CONNECT_TIME_OUT = 5 * 1000;
    private static final String FILE_NAME_FLAG = "filename=";

    public static String get(String url) {
        if (log.isDebugEnabled()) {
            log.debugf("Request url: %s, default timeout: %d", url, CONNECT_TIME_OUT);
        }

        try {
            Response resp = Http.get(url, CONNECT_TIME_OUT);
            if (resp.isOK()) {
                String content = resp.getContent("UTF-8");
                if (log.isInfoEnabled()) {
                    log.infof("GET Request success. Response content: %s", content);
                }
                return content;
            }

            throw Lang.wrapThrow(new RuntimeException(String.format("Get request [%s] failed. status: %d",
                                                                    url,
                                                                    resp.getStatus())));
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
    }

    public static String post(String url, String body) {
        if (log.isDebugEnabled()) {
            log.debugf("Request url: %s, post data: %s, default timeout: %d",
                       url,
                       body,
                       CONNECT_TIME_OUT);
        }

        try {
            Request req = Request.create(url, METHOD.POST);
            req.setEnc("UTF-8");
            req.setData(body);
            Response resp = Sender.create(req, CONNECT_TIME_OUT).send();
            if (resp.isOK()) {
                String content = resp.getContent();
                if (log.isInfoEnabled()) {
                    log.infof("POST Request success. Response content: %s", content);
                }
                return content;
            }

            throw Lang.wrapThrow(new RuntimeException(String.format("Post request [%s] failed. status: %d",
                                                                    url,
                                                                    resp.getStatus())));
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
    }

    public static String upload(String url, File file) {
        if (log.isDebugEnabled()) {
            log.debugf("Upload url: %s, file name: %s, default timeout: %d",
                       url,
                       file.getName(),
                       CONNECT_TIME_OUT);
        }

        try {
            Request req = Request.create(url, METHOD.POST);
            req.getParams().put("media", file);
            Response resp = new FilePostSender(req).send();
            if (resp.isOK()) {
                String content = resp.getContent();
                return content;
            }

            throw Lang.wrapThrow(new RuntimeException(String.format("Upload file [%s] failed. status: %d",
                                                                    url,
                                                                    resp.getStatus())));
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
    }

    public static Object download(String url) {
        if (log.isDebugEnabled()) {
            log.debugf("Download url: %s, default timeout: %d", url, CONNECT_TIME_OUT);
        }

        try {
            Response resp = Http.get(url);
            if (resp.isOK()) {
                String cd = resp.getHeader().get("Content-disposition");
                if (log.isInfoEnabled()) {
                    log.infof("Get download file info: %s", cd);
                }

                if (Lang.isEmpty(cd)) {
                    return resp.getContent();
                }

                cd = cd.substring(cd.indexOf(FILE_NAME_FLAG) + FILE_NAME_FLAG.length());
                String tmp = cd.startsWith("\"") ? cd.substring(1) : cd;
                tmp = tmp.endsWith("\"") ? cd.replace("\"", "") : cd;
                String filename = tmp.substring(0, tmp.lastIndexOf("."));
                String fileext = tmp.substring(tmp.lastIndexOf("."));
                if (log.isInfoEnabled()) {
                    log.infof("Download file name: %s", filename);
                    log.infof("Download file ext: %s", fileext);
                }

                File tmpfile = File.createTempFile(filename, fileext);
                InputStream is = resp.getStream();
                OutputStream os = new FileOutputStream(tmpfile);
                Streams.writeAndClose(os, is);
                return tmpfile;
            }

            throw Lang.wrapThrow(new RuntimeException(String.format("Download file [%s] failed. status: %d, content: %s",
                                                                    url,
                                                                    resp.getStatus(),
                                                                    resp.getContent())));
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
    }
}
