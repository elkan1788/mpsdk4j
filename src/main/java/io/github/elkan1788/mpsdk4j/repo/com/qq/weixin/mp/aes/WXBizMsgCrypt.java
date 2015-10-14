package io.github.elkan1788.mpsdk4j.repo.com.qq.weixin.mp.aes;

import io.github.elkan1788.mpsdk4j.core.XmlMsgBuilder;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.nutz.repo.Base64;

/**
 * 提供接收和推送给公众平台消息的加解密接口(UTF8编码的字符串).
 * <ol>
 * <li>第三方回复加密消息给公众平台</li>
 * <li>第三方收到公众平台发送的消息，验证消息的安全性，并对消息进行解密。</li>
 * </ol>
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 * <ol>
 * <li>在本项目中的jce-patch目录中找到对应JDK版本的文件</li>
 * <li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 * <li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 * <li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 */
public class WXBizMsgCrypt {

    private static Charset CHARSET = Charset.forName("utf-8");
    private byte[] aesKey;
    private String token;
    private String appId;
    private String fromAppId;

    /**
     * 构造函数
     * 
     * @param token
     *            公众平台上，开发者设置的token
     * @param encodingAesKey
     *            公众平台上，开发者设置的EncodingAESKey
     * @param appId
     *            公众平台appid
     * @throws AesException
     *             执行失败，请查看该异常的错误码和具体的错误信息
     */
    public WXBizMsgCrypt(String token, String encodingAesKey, String appId) throws AesException {
        if (encodingAesKey.length() != 43) {
            throw new AesException(AesException.IllegalAesKey);
        }

        this.token = token;
        this.appId = appId;
        aesKey = Base64.decode(encodingAesKey + "=");
    }

    // 生成4个字节的网络字节序
    private byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }

    // 还原4个字节的网络字节序
    private int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }

    // 随机生成16位字符串
    private String getRandomStr() {
        StringBuffer sb = new StringBuffer();
        Random ran = new Random();
        for (int i = 0; i < 16; i++) {
            boolean flag = ran.nextInt(2) % 2 == 0;
            if (flag) {
                char c = (char) (int) (Math.random() * 26 + 97);
                sb.append(c);
            }
            else {
                sb.append(ran.nextInt(10));
            }
        }
        return sb.toString();
    }

    /**
     * 对明文进行加密.
     *
     * @param text
     *            需要加密的明文
     * @return 加密后base64编码的字符串
     * @throws AesException
     *             aes加密失败
     */
    private String encrypt(String randomStr, String text) throws AesException {
        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] textBytes = text.getBytes(CHARSET);
        byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
        byte[] appidBytes = appId.getBytes(CHARSET);

        // randomStr + networkBytesOrder + text + appid
        byteCollector.addBytes(randomStrBytes);
        byteCollector.addBytes(networkBytesOrder);
        byteCollector.addBytes(textBytes);
        byteCollector.addBytes(appidBytes);

        // ... + pad: 使用自定义的填充方式对明文进行补位填充
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        // 获得最终的字节流, 未加密
        byte[] unencrypted = byteCollector.toBytes();

        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            // 加密
            byte[] encrypted = cipher.doFinal(unencrypted);

            // 使用BASE64对加密后的字符串进行编码
            String base64Encrypted = Base64.encodeToString(encrypted, false);

            return base64Encrypted;
        }
        catch (Exception e) {
            throw new AesException(AesException.EncryptAESError);
        }
    }

    /**
     * 对密文进行解密.
     *
     * @param text
     *            需要解密的密文
     * @return 解密得到的明文
     * @throws AesException
     *             aes解密失败
     */
    private String decrypt(String text) throws AesException {
        byte[] original;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decode(text);

            // 解密
            original = cipher.doFinal(encrypted);
        }
        catch (Exception e) {
            throw new AesException(AesException.DecryptAESError);
        }

        String xmlContent;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);

            // 分离16位随机字符串,网络字节序和AppId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

            int xmlLength = recoverNetworkBytesOrder(networkOrder);

            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            fromAppId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
        }
        catch (Exception e) {
            throw new AesException(AesException.IllegalBuffer);
        }

        // appid不相同的情况
        if (!fromAppId.equals(appId)) {
            throw new AesException(AesException.ValidateAppidError);
        }
        return xmlContent;

    }

    /**
     * 将公众平台回复用户的消息加密打包.
     * <ol>
     * <li>对要发送的消息进行AES-CBC加密</li>
     * <li>生成安全签名</li>
     * <li>将消息密文和安全签名打包成xml格式</li>
     * </ol>
     *
     * @param replyMsg
     *            公众平台待回复用户的消息，xml格式的字符串
     * @param timeStamp
     *            时间戳，可以自己生成，也可以用URL参数的timestamp
     * @param nonce
     *            随机串，可以自己生成，也可以用URL参数的nonce
     * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce,
     *         encrypt的xml格式的字符串
     * @throws AesException
     *             执行失败，请查看该异常的错误码和具体的错误信息
     */
    public String encryptMsg(String replyMsg, String timeStamp, String nonce) throws AesException {
        // 加密
        String encrypt = encrypt(getRandomStr(), replyMsg);

        // 生成安全签名
        if ("".equals(timeStamp)) {
            timeStamp = Long.toString(System.currentTimeMillis());
        }

        String signature = SHA1.calculate(token, timeStamp, nonce, encrypt);

        // 生成发送的xml
        String result = XmlMsgBuilder.create().encrypt(encrypt, signature, timeStamp, nonce);
        return result;
    }

    /**
     * 检验消息的真实性，并且获取解密后的明文.
     * <ol>
     * <li>利用收到的密文生成安全签名，进行签名验证</li>
     * <li>若验证通过，则提取xml中的加密消息</li>
     * <li>对消息进行解密</li>
     * </ol>
     *
     * @param msgSignature
     *            签名串，对应URL参数的msg_signature
     * @param timeStamp
     *            时间戳，对应URL参数的timestamp
     * @param nonce
     *            随机串，对应URL参数的nonce
     * @param postData
     *            密文，对应POST请求的数据
     * @return 解密后的原文
     * @throws AesException
     *             执行失败，请查看该异常的错误码和具体的错误信息
     */
    public String decryptMsg(String msgSignature,
                             String timeStamp,
                             String nonce,
                             InputStream postData) throws AesException {

        // 密钥，公众账号的app secret
        // 提取密文
        Object[] encrypt = XMLParse.extract(postData);

        // 验证安全签名
        String signature = SHA1.calculate(token, timeStamp, nonce, String.valueOf(encrypt[1]));

        // 和URL中的签名比较是否相等
        if (!signature.equals(msgSignature)) {
            throw new AesException(AesException.ValidateSignatureError);
        }

        // 解密
        String result = decrypt(String.valueOf(encrypt[1]));
        return result;
    }

    /**
     * 获取加密消息中的APPID[为订阅号提供]
     *
     * @return APPID
     */
    public String getFromAppid() {
        return this.fromAppId;
    }
}