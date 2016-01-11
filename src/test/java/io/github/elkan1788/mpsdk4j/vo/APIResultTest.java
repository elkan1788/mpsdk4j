package io.github.elkan1788.mpsdk4j.vo;

import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * APIResult 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class APIResultTest {

    private String sucJson = "{'errcode':0,'errmsg':'Success.'}";
    private String menuSucJson = "{'errcode':46003,'errmsg':'Not menu data.'}";
    private String unKnowErrJson = "{'errcode':9999,'errmsg':'Unkonw error.'}";
    private String errJson = "{'errcode':-1,'errmsg':'System busy.'}";
    private String atErrJson1 = "{'errcode':40001,'errmsg':'invalid appsecret'}";
    private String atErrJson2 = "{'errcode':42001,'errmsg':'access_token over time'}";
    private String atErrJson3 = "{'errcode':42002,'errmsg':'refresh token over time'}";
    private String atErrJson4 = "{'errcode':40014,'errmsg':'invalid access_token'}";

    @Test
    public void testGetJson() {
        APIResult ar = APIResult.create(sucJson);
        assertEquals(sucJson, ar.getJson());
    }

    @Test
    public void testGetJson2() {
        APIResult ar = APIResult.create(menuSucJson);
        assertEquals(menuSucJson, ar.getJson());
    }

    @Test
    public void testGetErrCode() {
        APIResult ar = APIResult.create(errJson);
        assertEquals(-1, ar.getErrCode().intValue());
    }

    @Test
    public void testGetErrMsg() {
        APIResult ar = APIResult.create(errJson);
        assertEquals("System busy.", ar.getErrMsg());
    }

    @Test
    public void testGetErrCNMsg() {
        APIResult ar = APIResult.create(sucJson);
        assertEquals("请求成功.", ar.getErrCNMsg());
    }

    @Test
    public void testIsSuccess() {
        APIResult ar = APIResult.create(errJson);
        assertTrue(!ar.isSuccess());
    }

    @Test
    public void testIsAccessTokenInvalid() {
        APIResult ar = APIResult.create(atErrJson1);
        assertFalse(ar.isSuccess());
        ar = APIResult.create(atErrJson2);
        assertFalse(ar.isSuccess());
        ar = APIResult.create(atErrJson3);
        assertFalse(ar.isSuccess());
        ar = APIResult.create(atErrJson4);
        assertFalse(ar.isSuccess());
    }

    @Test
    public void testUnknowError() {
        APIResult ar = APIResult.create(unKnowErrJson);
        assertEquals(unKnowErrJson, ar.getJson());
        assertEquals("未知错误!", ar.getErrCNMsg());
    }

    @Test
    public void testSucWithoutErrCode() {
        String json = "{'access_token':'ACCESS_TOKEN','expires_in':7200}";
        APIResult ar = APIResult.create(json);
        assertEquals(json, ar.getJson());
        assertEquals("Unknow Error!", ar.getErrMsg());
        assertTrue(ar.isSuccess());
    }

}
