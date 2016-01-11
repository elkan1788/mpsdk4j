package io.github.elkan1788.mpsdk4j.api;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import io.github.elkan1788.mpsdk4j.vo.api.QRTicket;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;


/**
 * QRCodeAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
// TODO 此API貌似有问题,以后再好好检测
public class QRCodeAPITest extends RunTestSupport {

    private static final Log log = Logs.get();


    @BeforeClass
    public void init() {
        log.info("====== QRCodeAPITest ======");
        MockUpHttpPost("{\"ticket\":\"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==\",\"expire_seconds\":60,\"url\":\"http:\\/\\/weixin.qq.com\\/q\\/kZgfwMTm72WWPkovabbI\"}");
    }

    @Test
    public void testCreateSceneQR() {
        log.info("====== QRCodeAPI#createSceneQR ======");
        QRTicket qrt = wechatAPI.createQR(10, 604800);
        assertNotNull(qrt);
        assertNotNull(qrt.getTicket());
    }

    @Test
    public void testCreateLimitSceneQR() {
        log.info("====== QRCodeAPI#createLimitSceneQR ======");
        QRTicket qrt = wechatAPI.createQR(new Integer(10), 0);
        assertNotNull(qrt);
        assertNotNull(qrt.getTicket());
    }

    @Test
    public void testCreateLimitStrSceneQR() {
        log.info("====== QRCodeAPI#createLimitStrSceneQR ======");
        QRTicket qrt = wechatAPI.createQR(url, 0);
        assertNotNull(qrt);
        assertNotNull(qrt.getTicket());
    }

    @Test
    public void testGetQR() {
        log.info("====== QRCodeAPI#getQR ======");
        MockUpHttpDownload(imgMedia);
        File qrImg = wechatAPI.getQR("gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm3sUw==");
        assertNotNull(qrImg);
    }
}
