package io.github.elkan1788.mpsdk4j.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * API 包测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
                     CredentialApiTest.class,
                     MenuAPITest.class,
                     MediaAPITest.class,
                     GroupsAPITest.class
})
public class APITest {

}
