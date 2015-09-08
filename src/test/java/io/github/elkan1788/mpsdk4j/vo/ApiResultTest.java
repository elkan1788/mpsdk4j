package io.github.elkan1788.mpsdk4j.vo;

import org.junit.Assert;
import org.junit.Test;

/**
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
        Assert.assertEquals(sucJson, ar.getJson());
    }

    @Test
    public void testGetErrCode() {
        ApiResult ar = ApiResult.create(errJson);
        Assert.assertEquals(-1, ar.getErrCode().intValue());
    }

    @Test
    public void testGetErrMsg() {
        ApiResult ar = ApiResult.create(errJson);
        Assert.assertEquals("System busy.", ar.getErrMsg());
    }

    @Test
    public void testGetErrCNMsg() {
        ApiResult ar = ApiResult.create(sucJson);
        Assert.assertEquals("请求成功.", ar.getErrCNMsg());
    }

    @Test
    public void testIsSuccess() {
        ApiResult ar = ApiResult.create(errJson);
        Assert.assertTrue(!ar.isSuccess());
    }

    @Test
    public void testIsAccessTokenInvalid() {
        ApiResult ar = ApiResult.create(atErrJson1);
        Assert.assertTrue(ar.isAccessTokenInvalid());
        ar = ApiResult.create(atErrJson2);
        Assert.assertTrue(ar.isAccessTokenInvalid());
        ar = ApiResult.create(atErrJson3);
        Assert.assertTrue(ar.isAccessTokenInvalid());
        ar = ApiResult.create(atErrJson4);
        Assert.assertTrue(ar.isAccessTokenInvalid());
    }

    @Test
    public void testUnknowError() {
        ApiResult ar = ApiResult.create(unKnowErrJson);
        Assert.assertEquals(unKnowErrJson, ar.getJson());
        Assert.assertEquals("未知错误!", ar.getErrCNMsg());
    }

    @Test
    public void testSucWithoutErrCode() {
        String json = "{'access_token':'ACCESS_TOKEN','expires_in':7200}";
        ApiResult ar = ApiResult.create(json);
        Assert.assertEquals(json, ar.getJson());
        Assert.assertEquals("Unknow Error!", ar.getErrMsg());
        Assert.assertTrue(ar.isSuccess());
        Assert.assertFalse(ar.isAccessTokenInvalid());
    }

}
