package io.github.elkan1788.mpsdk4j;

/**
 * 项目版本号声明
 * 
 * <pre/>
 * (参考:http://nutzam.com/core/committer/version_naming.html)
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public final class Mpsdk4j {

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

    private static int majorVersion() {
        return 2;
    }

    private static int minorVersion() {
        return 1;
    }

    private static String releaseLevel() {
        // a: 内部测试品质, b: 公测品质, r: 最终发布版
        return "b";
    }
}
