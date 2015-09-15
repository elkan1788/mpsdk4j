package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import io.github.elkan1788.mpsdk4j.vo.api.QRTicket;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * QRCodeAPI 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
// TODO 此API貌似有问题,以后再好好检测
public class QRCodeAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    private QRCodeAPI qa;
    private int expireSeconds = 604800;
    private String ticket = "";

    @Override
    @Before
    public void init() {
        log.info("====== QRCodeAPITest ======");
        super.init();
        qa = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testCreateQR() {
        log.info("====== QRCodeAPI#createQR ======");
        QRTicket qrt = qa.createQR(10, expireSeconds);
        assertNotNull(qrt);
        assertEquals(qrt.getExpireSeconds(), expireSeconds);
        log.infof("QR ticket: %s", qrt.getTicket());
        log.infof("QR expire time: %s", qrt.getExpireSeconds());
        log.infof("QR url: %s", qrt.getUrl());
    }

    @Test(expected = RuntimeException.class)
    public void testGetQR() {
        log.info("====== QRCodeAPI#getQR ======");
        File qrImg = qa.getQR(ticket);
        assertNotNull(qrImg);
        log.infof("temp path: %s", qrImg.getAbsolutePath());
    }

}
