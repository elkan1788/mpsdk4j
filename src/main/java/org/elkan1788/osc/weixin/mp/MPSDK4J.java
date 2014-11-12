package org.elkan1788.osc.weixin.mp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信公众平台JAVA SDK
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/6
 * @version 1.0.0
 */
public class MPSDK4J {

    private static final Logger log = LoggerFactory.getLogger(MPSDK4J.class);

    // SDK版本号
    public static final String VERSION = "1.0.0";

    /**
     * 获取当前SDK版本号
     *
     * @return  SDK版本号
     */
    public static String version() {
        return VERSION;
    }
}
