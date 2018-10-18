package com.github.brandonbai.smartmonitor.utils;

/**
 * 
 * TextUtils 
 * 文本工具
 * @author brandonbai
 * @since 2016年10月19日
 *
 */
public class TextUtils {
	private TextUtils(){}
	
	/**
	 * 将16进制字符串转换成byte数组
	 * 0A01050D ——> [10, 1, 5, 13]
	 */
	public static byte[] hexString2ByteArray(String s) {
		if(s == null ) {
			return null;
		}
		if(!s.matches("[\\dA-F]+")) {
			return null;
		}
		byte[] data = new byte[s.length()/2];
		for(int i = 0; i < s.length()-1; i += 2) {
			String sub = s.substring(i, i + 2);
			data[i/2] = (byte)Integer.parseInt(sub, 16);
		}
		return data;
	}
}
