package io.github.elkan1788.mpsdk4j.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 读取配置文件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class ConfigReader {

    private static final Log log = Logs.get();

    private Map<String, String> confs = new LinkedHashMap<String, String>();

    public ConfigReader(String path) {
        this.load(path);
    }

    protected synchronized void load(String path) {
        if (log.isDebugEnabled()) {
            log.debugf("Loading config file[%s].", path);
        }
        InputStream is = null;
        BufferedReader br = null;
        Properties p = new Properties();
        try {
            is = this.getClass().getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(is));
            p.load(br);
        }
        catch (Exception e) {
            throw Lang.wrapThrow(e);
        }
        finally {
            Streams.safeClose(is);
            Streams.safeClose(br);
        }
        putAll(p);
    }

    @SuppressWarnings({
                       "unchecked", "rawtypes"
    })
    protected void putAll(Map p) {
        confs.putAll(p);
    }

    public synchronized void clear() {
        confs.clear();
    }

    protected void valid(String key) {
        if (Strings.isBlank(key)) {
            throw new NullPointerException("Key can't not be NULL or empty value.");
        }
    }

    public void put(String key, String value) {
        confs.put(key, value);
    }

    public List<String> keys() {
        return new ArrayList<String>(confs.keySet());
    }

    public Collection<String> values() {
        return confs.values();
    }

    public String get(String key) {
        valid(key);
        return confs.get(key);
    }

    public int getInt(String key) {
        valid(key);
        return Integer.parseInt(get(key));
    }

    public long getLong(String key) {
        valid(key);
        return Long.valueOf(get(key)).longValue();
    }

    public boolean getBoolean(String key) {
        valid(key);
        return Boolean.valueOf(get(key));
    }
}
