package org.jifeihu.smartshed.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class PropUtils {
	private static Properties prop = new Properties();
	static {
		try {
			String path = PropUtils.class.getClassLoader().getResource("config.properties").getPath();
			prop.load(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "未找到配置文件", "错误", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "配置文件读取错误，请检查文件", "错误", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	private PropUtils(){}
	
	/**
	 * 根据key获取配置文件中对应的value
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		
		return prop.getProperty(key);
	}
}
