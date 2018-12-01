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
			JOptionPane.showMessageDialog(null, "δ�ҵ������ļ�", "����", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�����ļ���ȡ���������ļ�", "����", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	private PropUtils(){}
	
	/**
	 * ����key��ȡ�����ļ��ж�Ӧ��value
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		
		return prop.getProperty(key);
	}
}
