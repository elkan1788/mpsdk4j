package org.elkan1788.osc.weixin.mp;

/**
 * 微信公众平台JAVA SDK版本号声明
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/6
 */
public class MPSDK4J {

    /**
     * 获取 mpsdk4j 的版本号，版本号的命名规范
     *
     * <pre>
     * [大版本号].[质量号].[发布流水号]
     * </pre>
     *
     * @return mpsdk4j 项目的版本号
     */
    public static String version() {
        return String.format("%d.%s.%d", majorVersion(), releaseLevel(), minorVersion());
    }

    public static int majorVersion() {
        return 1;
    }

    public static int minorVersion() {
        return 24;
    }

    public static String releaseLevel() {
        //a: 内部测试品质, b: 公测品质, r: 最终发布版
        return "a";
    }
}
