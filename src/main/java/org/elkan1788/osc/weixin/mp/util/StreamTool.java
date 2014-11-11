package org.elkan1788.osc.weixin.mp.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 输入流与字符串处理工具
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/8
 * @version 1.0.0
 */
public class StreamTool {

    /**
     * 将字符串转换成输入流
     *
     * @param str 字符串
     * @return 输入流
     */
    public static InputStream toStream(String str) {
        InputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 输入流
     * @return 字符串
     * @throws IOException
     */
    public static String toString(InputStream is) throws IOException {
        StringBuffer str = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = is.read(b)) != -1; ) {
            str.append(new String(b, 0, n));
        }
        return str.toString();
    }
}
