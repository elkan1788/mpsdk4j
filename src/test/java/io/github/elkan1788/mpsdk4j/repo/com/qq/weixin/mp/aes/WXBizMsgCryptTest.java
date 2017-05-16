package io.github.elkan1788.mpsdk4j.repo.com.qq.weixin.mp.aes;

import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import io.github.elkan1788.mpsdk4j.util.StreamTool;

/**
 * WXBizMsgCrypt 测试
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WXBizMsgCryptTest {

    private static final Log log = Logs.get();

    private String encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
    private String token = "pamtest";
    private String timeStamp = "1409304348";
    private String nonce = "xxxxxx";
    private String appId = "wxb11529c136998cb6";
    private String replyMsg = "<xml><ToUserName><![CDATA[oia2TjjewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[中文testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
    private String fromMsg = "<xml><Encrypt><![CDATA[PTMtzW/z7ASpQyvKcHT1H15AeVEYjhkQpieM4enpX0Cu8zgKys4D49GmNNOFb6OGcsTRfoo/UwXezaZvtgGQzgqPIMLnJFPkPAjtD0TviL8gkdtJw6kSGtU5ksSvhV0U4EFtnGPyfyGVnAUDMqrJV7uIDbyB2pXGKyEGJnAqYzHDga2HeG1YJes1OlZADbQDlrwHtJ0xfxgOX/sTRHuK525CFpJw0eACE2ZFu/qj1gZ5e3yewjWld9bwa1OAgMzCIHUdJf25gnWq1fGFhuVeGQiZmGt3uxCEO0WV9S5DXdt0P9YHu2ZPV+uik/pVTiIU+ZqGy5SeyuYCiGgE4xfQedoYJSB+lIx/QmXUi+K1Rbv3X/zBHEXQ/JKsrzIzgD71vwFNEIJ7sSGtuoo6h2tmxlmI/75lXbteOg8oefSEVosgimafpv504RR7jpgdGuVvb7Z7q6mF6LWHVY4tF3jNyLMsdF1WwjOWogmXGTYdkcn+HOejiUeaFgg15h92JSrpMBnIPR2Biw7ery+BoeSRAiFjqHNd8AcKxYCW2L5La3abMcwiMvwPeos52FXTF8kam2yNAjUZJyY0Ib0VBMfl1KLu7JV0An8iIde1NhXZWNXuJHBVndqJhrInX9WxXTgW]]></Encrypt><MsgSignature><![CDATA[9f8883ff0676a51299747fc2d4b2e6a4be9207cb]]></MsgSignature><TimeStamp>1409304348</TimeStamp><Nonce><![CDATA[xxxxxx]]></Nonce></xml>";
    private String msgSignature = "9f8883ff0676a51299747fc2d4b2e6a4be9207cb";

    private WXBizMsgCrypt pc;

    @BeforeClass
    public void init() {
        log.info("====== WXBizMsgCryptTest ======");
        try {
            pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        }
        catch (AesException e) {
            throw Lang.wrapThrow(e);
        }
    }

    @Test
    public void testEncryptMsg() {
        log.info("====== WXBizMsgCrypt#encryptMsg ======");
        try {
            String encryptmsg = pc.encryptMsg(replyMsg, timeStamp, nonce);
            assertNotNull(encryptmsg);
            log.info(encryptmsg.replaceAll("\\n", ""));
        }
        catch (AesException e) {
            throw Lang.wrapThrow(e);
        }
    }

    @Test
    public void testDecryptMsg() {
        log.info("====== WXBizMsgCrypt#decryptMsg ======");
        try {
            String decryptmsg = pc.decryptMsg(msgSignature,
                                              timeStamp,
                                              nonce,
                                              StreamTool.toStream(fromMsg));
            assertNotNull(decryptmsg);
            log.info(decryptmsg);
            log.info(pc.getFromAppId());
        }
        catch (AesException e) {
            throw Lang.wrapThrow(e);
        }
    }

}
