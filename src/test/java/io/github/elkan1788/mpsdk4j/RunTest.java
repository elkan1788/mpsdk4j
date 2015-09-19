package io.github.elkan1788.mpsdk4j;

import io.github.elkan1788.mpsdk4j.api.APITest;
import io.github.elkan1788.mpsdk4j.core.CoreTest;
import io.github.elkan1788.mpsdk4j.repo.com.qq.weixin.mp.aes.AESTest;
import io.github.elkan1788.mpsdk4j.util.UtilTest;
import io.github.elkan1788.mpsdk4j.vo.VOTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test all class
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Mpsdk4jTest.class,
        UtilTest.class,
        VOTest.class,
        APITest.class,
        CoreTest.class,
        AESTest.class
})
public class RunTest {

}
