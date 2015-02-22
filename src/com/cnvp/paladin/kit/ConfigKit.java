package com.cnvp.paladin.kit;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import com.jfinal.core.JFinal;


/**
 * 配置文件app.properties所有配置
 * 
 */
public class ConfigKit {
	public static Map<String, Object> config = new HashMap<String, Object>();
	public static Properties properties=new Properties();
	static {
		ResourceBundle rb = ResourceBundle.getBundle("app");
		Enumeration<String> cfgs = rb.getKeys();
		while (cfgs.hasMoreElements()) {
			String key = cfgs.nextElement();
			String val=rb.getString(key);
			config.put(key,val);
		}
		 properties.putAll(config);
	}
	public static boolean isDevMode(){
		return JFinal.me().getConstants().getDevMode();
	}
	public static String getDbPrefix(){
		return get("jdbc.dbPrefix");
	}
	public static String get(String key){
		return (String)config.get(key);
	}
	public static Object getObj(String key){
		return config.get(key);
	}
	public static Boolean getToBool(String key,Boolean def){
		try{
			return Boolean.valueOf((String)config.get(key));
		}catch(Exception e){
			return def;
		}
	}
	public static Integer getToInteger(String key,Integer def){
		try{
			return Integer.valueOf((String)config.get(key));
		}catch(Exception e){
			return def;
		}
	}
	public static Long getToLong(String key,Long def){
		try{
			return Long.valueOf((String)config.get(key));
		}catch(Exception e){
			return def;
		}
	}
}
