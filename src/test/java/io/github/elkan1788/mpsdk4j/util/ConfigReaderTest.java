package io.github.elkan1788.mpsdk4j.util;

import static org.testng.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * ConfigReader Testing
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class ConfigReaderTest {

    private static final Log logger = Logs.get();

    private static final int INTEGER = 1;
    private static final boolean BOOL = true;
    private static final long LONG = 100000000000000l;

    private ConfigReader cr;

    @BeforeClass
    public void init() {
        cr = ConfigReader.read("/ErrorCode.properties");
        cr.put("int", String.valueOf(INTEGER));
        cr.put("bool", String.valueOf(BOOL));
        cr.put("long", String.valueOf(LONG));
    }

    @AfterClass
    public void testClear() {
        cr.clear();
        assertNull(cr.get("0"));
    }

    @Test
    public void testAll() {
        List<String> keys = cr.keys();
        Collection<String> values = cr.values();
        assertEquals(keys.size(), values.size());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullFile() {
        new ConfigReader("/test.properties");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullKey() {
        cr.get(null);
    }

    @Test
    public void testGetInt() {
        assertEquals(cr.getInt("int"), INTEGER);
    }

    @Test
    public void testGetLong() {
        assertEquals(cr.getLong("long"), LONG);
    }

    @Test
    public void testGetBoolean() {
        assertTrue(cr.getBoolean("bool"));
    }

    @Test
    public void testValues() throws Exception {
        Map<String, String> tmp = cr.getAll();
        for (String key :  tmp.keySet()) {
            assertNotNull(key);
            logger.infof("%s: %s", key, tmp.get(key));
        }
    }
}
