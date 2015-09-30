package io.github.elkan1788.mpsdk4j.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * ConfigReader Testing
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class ConfigReaderTest {

    private static final int INTEGER = 1;
    private static final boolean BOOL = true;
    private static final long LONG = 100000000000000l;

    private ConfigReader cr;

    @Before
    public void init() {
        cr = new ConfigReader("/ErrorCode.properties");
        cr.put("int", String.valueOf(INTEGER));
        cr.put("bool", String.valueOf(BOOL));
        cr.put("long", String.valueOf(LONG));
    }

    @After
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

    @Test
    public void testGet() {
        assertNotNull(cr.get("0"));
    }

    @Test(expected = NullPointerException.class)
    public void testNullFile() {
        new ConfigReader("/test.properties");
    }

    @Test(expected = NullPointerException.class)
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

}
