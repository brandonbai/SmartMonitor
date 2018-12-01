package org.jifeihu.smartshed.util;

public class DataUtils {
	private DataUtils(){}
	
	/**
	 * 转字符串为byte数组
	 * @param s
	 * @return
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
