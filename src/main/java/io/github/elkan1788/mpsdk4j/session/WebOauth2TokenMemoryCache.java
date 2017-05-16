package io.github.elkan1788.mpsdk4j.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.elkan1788.mpsdk4j.vo.api.WebOauth2Result;

/**
 * 网页授权缓存
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.b.2
 */
public class WebOauth2TokenMemoryCache implements MemoryCache<WebOauth2Result> {

    private static final Map<String, WebOauth2Result> waas = new ConcurrentHashMap<String, WebOauth2Result>();

    @Override
    public WebOauth2Result get(String key) {
        return waas.get(key);
    }

    @Override
    public void set(String key, WebOauth2Result webOauth2Result) {
        waas.put(key, webOauth2Result);
    }

    @Override
    public void remove(String key) {
        waas.remove(key);
    }
}
