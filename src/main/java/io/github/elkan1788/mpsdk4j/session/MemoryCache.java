package io.github.elkan1788.mpsdk4j.session;

/**
 * 本地缓存接口设计
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public interface MemoryCache<T> {

    T get(String key);

    void set(String key, T object);

    void remove(String key);

}
