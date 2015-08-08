package com.menkj.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * propertiesUtil������
 * 
 * @author zen64
 * 
 */
public class PropertiesUtil {

	public static Properties getProperties(String fileName) throws FileNotFoundException {
		Properties prop = new Properties();// ���Լ��϶���
		FileInputStream fis;

		fis = new FileInputStream(fileName);
		try {
			prop.load(fis);
			fis.close();// �ر���
		} catch (IOException e) {
			return null;
		}
		return prop;
	}

	/**
	 * ����Properties�ļ�
	 * @param fileName
	 * @param map
	 * @return
	 */
	public static boolean createProperties( String fileName, Map map){
		Properties prop = new Properties();// ���Լ��϶���
		
		Iterator<String> it = map.keySet().iterator();
//		File file = new File(fileName);
//		if(!file.exists()) file.
//		System.out.println(file.getAbsolutePath());
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			
			while(it.hasNext()){
				String key = (String) it.next();
				prop.setProperty(key, (String) map.get(key));
			}
			prop.store(fos, "Copyright (c) lsc");
			fos.close();// �ر���
			return true;
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return false;
	}
}
