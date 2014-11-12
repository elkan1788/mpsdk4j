package com.qq.weixin.mp.aes;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * SHA1算法计算公众平台的消息签名接口
 *
 * @author Tencent
 * @since 2014/11/4
 */
public class SHA1 {

	/**
     * 用SHA1算法生成安全签名
     * @param params [token, timestamp, nonce, encrypt]
     * @return 安全签名
     * @throws com.qq.weixin.mp.aes.AesException
     */
        public static String getSHA1(String... params) throws AesException {
		try {
            String[] array = params;
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < 4; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
}
