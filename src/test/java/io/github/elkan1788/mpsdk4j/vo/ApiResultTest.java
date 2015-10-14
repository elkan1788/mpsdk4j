package io.github.elkan1788.mpsdk4j.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * ApiResult 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class ApiResultTest {

    private String sucJson = "{'errcode':0,'errmsg':'Success.'}";
    private String unKnowErrJson = "{'errcode':9999,'errmsg':'Unkonw error.'}";
    private String errJson = "{'errcode':-1,'errmsg':'System busy.'}";
    private String atErrJson1 = "{'errcode':40001,'errmsg':'invalid appsecret'}";
    private String atErrJson2 = "{'errcode':42001,'errmsg':'access_token over time'}";
    private String atErrJson3 = "{'errcode':42002,'errmsg':'refresh token over time'}";
    private String atErrJson4 = "{'errcode':40014,'errmsg':'invalid access_token'}";

    @Test
    public void testGetJson() {
        ApiResult ar = ApiResult.create(sucJson);
        assertEquals(sucJson, ar.getJson());
    }

    @Test
    public void testGetErrCode() {
        ApiResult ar = ApiResult.create(errJson);
        assertEquals(-1, ar.getErrCode().intValue());
    }

    @Test
    public void testGetErrMsg() {
        ApiResult ar = ApiResult.create(errJson);
        assertEquals("System busy.", ar.getErrMsg());
    }

    @Test
    public void testGetErrCNMsg() {
        ApiResult ar = ApiResult.create(sucJson);
        assertEquals("请求成功.", ar.getErrCNMsg());
    }

    @Test
    public void testIsSuccess() {
        ApiResult ar = ApiResult.create(errJson);
        assertTrue(!ar.isSuccess());
    }

    @Test
    public void testIsAccessTokenInvalid() {
        ApiResult ar = ApiResult.create(atErrJson1);
        assertFalse(ar.isSuccess());
        ar = ApiResult.create(atErrJson2);
        assertFalse(ar.isSuccess());
        ar = ApiResult.create(atErrJson3);
        assertFalse(ar.isSuccess());
        ar = ApiResult.create(atErrJson4);
        assertFalse(ar.isSuccess());
    }

    @Test
    public void testUnknowError() {
        ApiResult ar = ApiResult.create(unKnowErrJson);
        assertEquals(unKnowErrJson, ar.getJson());
        assertEquals("未知错误!", ar.getErrCNMsg());
    }

    @Test
    public void testSucWithoutErrCode() {
        String json = "{'access_token':'ACCESS_TOKEN','expires_in':7200}";
        ApiResult ar = ApiResult.create(json);
        assertEquals(json, ar.getJson());
        assertEquals("Unknow Error!", ar.getErrMsg());
        assertTrue(ar.isSuccess());
    }

}
