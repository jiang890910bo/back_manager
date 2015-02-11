package com.cnvp.paladin.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jfinal.kit.PathKit;

/**
 * 1、必须是.properties文件 2、必须在WEB-INF/classes目录下
 */
public class PropertyKit {
	// 属性文件的路径
	// static String profilepath = "config.properties";
	static String defaultPropFileName = "config";
	private static Map<String, Properties> props = new HashMap<String, Properties>();
	static {
		load(defaultPropFileName);
	}
	public static boolean getToBoolean(String key){
		String val = get(key);
		return Boolean.parseBoolean(val);
	}
	public static String get(String key) {
		return get(key,defaultPropFileName);
	}
	public static String get(String key,String proFileName) {
		if(!props.containsKey(proFileName))
			load(proFileName);
		return props.get(proFileName).getProperty(key);
	}

	/**
	 * 更新（或插入）一对properties信息(主键及其键值) 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 */
	public static boolean set(String keyname, String keyvalue) {
		return set(keyname,keyvalue,defaultPropFileName);
	}
	public static boolean set(String keyname, String keyvalue ,String proFileName) {
//		if (!props.containsKey(proFileName))
			load(proFileName);
		try {
			String profilepath = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + proFileName + ".properties";
			Properties prop = props.get(proFileName);
			OutputStream fos = new FileOutputStream(profilepath);
			prop.setProperty(keyname, keyvalue);
			prop.store(fos, "Update '" + keyname + "' value");
			props.put(proFileName, prop);
			return true;
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
			return false;
		}
	}

	private static void load(String proFileName) {
		String profilepath = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + proFileName + ".properties";
		File file = new File(profilepath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("PropertyKit=>文件不存在，且创建失败");
				e.printStackTrace();
			}
		}
		try {
			Properties prop = new Properties();			
			prop.load(new FileInputStream(profilepath));
			props.put(proFileName, prop);
		} catch (FileNotFoundException e) {
			System.err.println("PropertyKit=>读取失败："+profilepath);
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}
}