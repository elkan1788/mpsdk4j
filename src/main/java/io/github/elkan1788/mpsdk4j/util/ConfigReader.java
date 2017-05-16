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
 * 读取配置文件的工具类, 默认的文件路径为: mp_config.properties,
 * 支持读取多个配置文件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class ConfigReader {

    private static final Log log = Logs.get();

    private static final Map<String, String> CONF_PARAMETERS = new LinkedHashMap<String, String>();

    private static ConfigReader CR;

    private static final String[] DEFAULT_CONF_FILES = { "/mp_config.properties" };

    public static ConfigReader read() {
        return read(null);
    }

    public static ConfigReader read(String... confFilesPath) {
        if (CR == null) {
            synchronized (ConfigReader.class) {
                confFilesPath = Lang.isEmptyArray(confFilesPath) ? DEFAULT_CONF_FILES : confFilesPath;
                CR = new ConfigReader(confFilesPath);
            }
        }

        return CR;
    }

    public ConfigReader(String... confFilesPath) {
        this.load(confFilesPath);
    }

    protected void load(String... confFilesPath) {
        if (log.isDebugEnabled()) {
            log.debugf("Loading config files[%s].", confFilesPath);
        }
        InputStream is = null;
        BufferedReader br = null;
        Properties p = new Properties();
        try {
            for (String confFilePath : confFilesPath) {
                is = this.getClass().getResourceAsStream(confFilePath);
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                p.load(br);
            }
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
        CONF_PARAMETERS.putAll(p);
    }

    public synchronized void clear() {
        CONF_PARAMETERS.clear();
    }

    protected void valid(String key) {
        if (Strings.isBlank(key)) {
            throw new NullPointerException("Key can't not be NULL or empty value.");
        }
    }

    public void put(String key, String value) {
        CONF_PARAMETERS.put(key, value);
    }

    public List<String> keys() {
        return new ArrayList<String>(CONF_PARAMETERS.keySet());
    }

    public Collection<String> values() {
        return CONF_PARAMETERS.values();
    }

    public String get(String key) {
        valid(key);
        return CONF_PARAMETERS.get(key);
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

    public Map<String, String> getAll() {
        return CONF_PARAMETERS;
    }
}
