package org.elkan1788.osc.weixin.mp.util;

import org.junit.Test;

import java.io.InputStream;

public class StreamUtilTest {

    @Test
    public void testStr2Stream() throws Exception {

    }

    @Test
    public void testStream2String() throws Exception {
        InputStream is = this.getClass().getResourceAsStream("/mysqlnginx.txt");
        long t1 = System.currentTimeMillis();
        String str = StreamTool.toString(is);
        long t2 = System.currentTimeMillis();
        System.out.println(str);
        System.out.println((t2-t1));
    }
}