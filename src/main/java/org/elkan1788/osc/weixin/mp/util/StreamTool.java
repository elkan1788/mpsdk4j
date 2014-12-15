package org.elkan1788.osc.weixin.mp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 输入流与字符串处理工具
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/8
 * @version 1.0.0
 */
public class StreamTool {

    private static final Logger log = LoggerFactory.getLogger(StreamTool.class);

    /**
     * 将字符串转换成输入流
     *
     * @param str 字符串
     * @return 输入流
     */
    public static InputStream toStream(String str) {
        InputStream stream = null;
        try {
            // UTF-8 解决网络传输中的字符集问题
            stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("转换输出Stream异常,不支持的字符集!!!");
            log.error(e.getLocalizedMessage(), e);
        }
        return stream;
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 输入流
     * @return 字符串
     * @throws IOException
     */
    public static String toString(InputStream is) {
        StringBuffer str = new StringBuffer();
        byte[] b = new byte[1024];

        try {
            for (int n; (n = is.read(b)) != -1; ) {
                str.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            log.error("读取输入流出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }
        return str.toString();
    }
}
