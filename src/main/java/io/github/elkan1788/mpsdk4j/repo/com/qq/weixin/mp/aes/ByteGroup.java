package io.github.elkan1788.mpsdk4j.repo.com.qq.weixin.mp.aes;

import java.util.ArrayList;
import java.util.List;

/**
 * 字节处理工具
 *
 * @author Tencent
 * @since 2014/11/4
 */
public class ByteGroup {

	// 定义一个字节容器
	private List<Byte> byteContainer = new ArrayList<Byte>();

	/**
	 * 将容器转化成字节数组
	 *
	 * @return 字节数组
	 */
	public byte[] toBytes() {
		byte[] bytes = new byte[byteContainer.size()];
		for (int i = 0; i < byteContainer.size(); i++) {
			bytes[i] = byteContainer.get(i);
		}
		return bytes;
	}

	/**
	 * 添加字节
	 *
	 * @param bytes
	 *            字节数组
	 * @return com.qq.weixin.mp.aes.ByteGroup
	 */
	public ByteGroup addBytes(byte[] bytes) {
		for (byte b : bytes) {
			byteContainer.add(b);
		}
		return this;
	}

	/**
	 * 获取字节容器长度
	 *
	 * @return 容器长度
	 */
	public int size() {
		return byteContainer.size();
	}
}
