package io.github.elkan1788.mpsdk4j.repo.com.qq.weixin.mp.aes;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Formatter;

/**
 * SHA1算法计算公众平台的消息签名接口
 *
 * @author Tencent
 * @since 2014/11/4
 */
public class SHA1 {

	/**
	 * 用SHA1算法生成安全签名
	 *
	 * @param params
	 *            [token, timestamp, nonce, encrypt]
	 * @return 安全签名
	 * @throws com.qq.weixin.mp.aes.AesException
	 */
	public static String calculate(String... params) throws AesException {
		try {
			String[] array = params;
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			int len = params.length;
			for (int i = 0; i < len; i++) {
				sb.append(array[i]);
			}

			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(new String(sb).getBytes("UTF-8"));

			// HEX输出
			byte[] hash = md.digest();
			Formatter formatter = new Formatter();
			for (byte b : hash) {
				formatter.format("%02x", b);
			}
			String hex = formatter.toString();
			formatter.close();
			return hex;
		} catch (Exception e) {
			throw new AesException(AesException.ComputeSignatureError);
		}
	}
}
