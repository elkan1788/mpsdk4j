package io.github.elkan1788.mpsdk4j;

import io.github.elkan1788.mpsdk4j.util.ConfigReader;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class TestSupport {

    protected static ConfigReader _cr;

    static {
        _cr = new ConfigReader("/mpconf.properties");
    }
}
